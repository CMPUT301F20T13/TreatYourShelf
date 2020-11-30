package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.content.Context;
import android.graphics.RectF;
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
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

/**
 * Class which analyzes each frame sent by the camera and determines if a barcode is there
 */
public class BarcodeAnalyzer implements ImageAnalysis.Analyzer {

    private final Context context;
    private Lifecycle lifecycle;
    private final OnBarcodeFound barcodeFound;
    private RectF rectF;
    private final int heightCropPercent;
    private final int widthCropPercent;

    interface OnBarcodeFound {
        void barcodeFound(Barcode barcode);
    }

    BarcodeAnalyzer(OnBarcodeFound barcodeFound, int heightCropPercent, int widthCropPercent, Context context) {
        this.barcodeFound = barcodeFound;
        this.context = context;
        this.heightCropPercent = heightCropPercent;
        this.widthCropPercent = widthCropPercent;
    }

    /**
     * function which is called to analyze each picture
     * @param imageProxy the image being analyzed
     */
    @androidx.camera.core.ExperimentalGetImage
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            float rectTop = mediaImage.getHeight() * heightCropPercent / 2 / 100f;
            float rectLeft = mediaImage.getWidth() * widthCropPercent / 2 / 100f;
            float rectRight = mediaImage.getWidth() * (1 - widthCropPercent / 2 / 100f);
            float rectBottom = mediaImage.getHeight() * (1 - heightCropPercent / 2 / 100f);

            rectF = new RectF(rectLeft, rectTop, rectRight, rectBottom);

            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_EAN_13)
                            .build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            Task<List<Barcode>> result = scanner.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            // Task completed successfully
                            if (!barcodes.isEmpty()) {
                                Barcode barcode = barcodes.get(0);
                                RectF barcodeBoundingBox = new RectF(barcode.getBoundingBox());
                                if (rectF.contains(barcodeBoundingBox.left, barcodeBoundingBox.top, barcodeBoundingBox.right, barcodeBoundingBox.bottom)) {
                                    barcodeFound.barcodeFound(barcode);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
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
