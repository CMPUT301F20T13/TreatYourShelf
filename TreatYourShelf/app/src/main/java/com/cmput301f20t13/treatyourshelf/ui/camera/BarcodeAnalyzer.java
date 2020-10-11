package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.lifecycle.Lifecycle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class BarcodeAnalyzer implements ImageAnalysis.Analyzer {

    private Context context;
    private Lifecycle lifecycle;
    private OnBarcodeFound barcodeFound;
    private int imageCropWidthPercentage, imageCropHeightPercentage;

    interface OnBarcodeFound {
        public void barcodeFound(Barcode barcode);
    }

    BarcodeAnalyzer(OnBarcodeFound barcodeFound, int imageCropWidthPercentage, int imageCropHeightPercentage) {
        this.barcodeFound = barcodeFound;
        this.imageCropWidthPercentage = imageCropWidthPercentage;
        this.imageCropHeightPercentage = imageCropHeightPercentage;
    }

    @androidx.camera.core.ExperimentalGetImage
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
            // CROP IMAGE FIRST
            System.out.println("Height: " + mediaImage.getHeight());
            System.out.println("Width: " + mediaImage.getWidth());
            int imageHeight = mediaImage.getHeight();
            int imageWidth = mediaImage.getWidth();
            System.out.println(mediaImage.getFormat());
            int actualAspectRatio = imageWidth / imageHeight;

            Bitmap convertImageToBitmap = ImageUtilities.YUV_420_888_toRGB(image, imageWidth, imageHeight, context);
            Rect cropRect = new Rect(0, 0, imageWidth, imageHeight);

            // If the image has a way wider aspect ratio than expected, crop less of the height so we
            // don't end up cropping too much of the image. If the image has a way taller aspect ratio
            // than expected, we don't have to make any changes to our cropping so we don't handle it
            // here.

            if (actualAspectRatio > 3) {
                imageCropHeightPercentage = imageCropHeightPercentage / 2;

            }

            // If the image is rotated by 90 (or 270) degrees, swap height and width when calculating
            // the crop.
            float widthCrop, heightCrop;

            switch (rotationDegrees) {
                case 90:
                case 270: {
                    widthCrop = imageCropHeightPercentage / 100f;
                    heightCrop = imageCropWidthPercentage / 100f;
                    break;
                }
                default: {
                    widthCrop = imageCropWidthPercentage / 100f;
                    heightCrop = imageCropHeightPercentage / 100f;
                }
            }

            cropRect.inset(
                    Math.round(imageWidth * widthCrop / 2),
                    Math.round(imageHeight * heightCrop / 2)
            );
            Bitmap croppedBitmap =
                    ImageUtilities.rotateAndCrop(convertImageToBitmap, rotationDegrees, cropRect);


            // Pass image to an ML Kit Vision API
            // ...
            //        Bitmap myBitmap = BitmapFactory.decodeResource(
            //                requireContext().getResources(),
            //                R.drawable.puppy);
            //        BarcodeDetector detector =
            //                new BarcodeDetector.Builder(getContext())
            //                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
            //                        .build();
//                    Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
//                    SparseArray<Barcode> barcodes = detector.detect(frame);
//                    Barcode thisCode = barcodes.valueAt(0);
//                    System.out.println(thisCode.rawValue);

            BarcodeScanner scanner = BarcodeScanning.getClient();
            Task<List<Barcode>> result = scanner.process(InputImage.fromBitmap(croppedBitmap,0 ))
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            // Task completed successfully
                            // ...
                            if (!barcodes.isEmpty()) {
                                Barcode barcode = barcodes.get(0);
                                barcodeFound.barcodeFound(barcode);

                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...

                        }
                    });
            result.addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    imageProxy.close();
                }
            });
        }
    }
}
