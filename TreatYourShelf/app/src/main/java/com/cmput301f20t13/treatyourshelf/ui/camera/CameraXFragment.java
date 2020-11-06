package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

public class CameraXFragment extends Fragment {

    private ImageCapture imageCapture;
    private File outputDirectory;
    private CameraControl cameraControl;
    private ExecutorService cameraExecutor;
    private String[] permissions;
    private RectF rectF;
    private boolean cameraFlashToggled = false;

    public CameraXFragment() {
        permissions = new String[]{Manifest.permission.CAMERA};
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment, container, false);

        ImageButton closeCameraBt = view.findViewById(R.id.close_camera_bt);
        ImageButton toggleCameraFlashBt = view.findViewById(R.id.camera_flash_bt);
        closeCameraBt.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
        toggleCameraFlashBt.setOnClickListener(view1 -> {

            if (!cameraFlashToggled) {
                // Camera Flash is On, want to turn off
                cameraFlashToggled = true;
                toggleCameraFlashBt.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_flash_off_24));
                cameraControl.enableTorch(false);
            } else {
                cameraFlashToggled = false;
                toggleCameraFlashBt.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_flash_on_24));
                cameraControl.enableTorch(true);
            }
        });


        return view;
    }

    private Boolean allPermissionsGranted() {


        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SurfaceView overlay = view.findViewById(R.id.overlay);

        overlay.setZOrderMediaOverlay(true);
        overlay.getHolder().setFormat(PixelFormat.TRANSPARENT);
        overlay.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                rectF = drawOverlay(overlay.getHolder(), 74, 8);

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        if (allPermissionsGranted()) {
            startCameraPreview();
        } else {
            requireActivity().requestPermissions(permissions, 11);
        }
        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    private void startCameraPreview() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    PreviewView viewFinder = requireView().findViewById(R.id.camera_preview);
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    Preview preview = new Preview.Builder().build();
                    viewFinder.getSurfaceProvider();
                    preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                    CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build();

                    imageAnalysis.setAnalyzer(cameraExecutor, new BarcodeAnalyzer(new BarcodeAnalyzer.OnBarcodeFound() {
                        @Override
                        public void barcodeFound(com.google.mlkit.vision.barcode.Barcode barcode) {
                            barcode.getRawValue();
                            barcode.getBoundingBox();
//                            System.out.println(barcode.getBoundingBox());
//                            System.out.println(barcode.getRawValue());
                            

                            cameraProvider.unbindAll();
                            Toast.makeText(requireContext(), barcode.getRawValue(), Toast.LENGTH_SHORT).show();
                            //TODO: Start barcode loading animation
                            //TODO: Check firebase if result exists, if it dosen't, bind camera again, else open bottom sheet and display result
                            BottomSheetScannedISBNResults bottomSheetScannedISBNResults = new BottomSheetScannedISBNResults();
                            bottomSheetScannedISBNResults.show(getChildFragmentManager(), null);
                            getChildFragmentManager().executePendingTransactions();
                            Objects.requireNonNull(bottomSheetScannedISBNResults.getDialog()).setOnDismissListener(dialogInterface -> {
                                System.out.println("WE should have been dismissed");
                                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageAnalysis, preview);
                            });
                        }

                    }, 74, 8, requireContext()));


                    try {
                        cameraProvider.unbindAll();
                        Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageAnalysis, preview);
                        cameraControl = camera.getCameraControl();


                    } catch (Exception e) {
                        Log.e(TAG, "Use case binding failed", e);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void stopCameraPreview() {

        cameraExecutor.shutdown();
    }

    private void takePhoto() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCameraPreview();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (allPermissionsGranted()) {
                startCameraPreview();
            } else {
                Toast.makeText(getContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                //Pop backstack
            }
        }
    }

    private RectF drawOverlay(SurfaceHolder surfaceHolder, int heightCropPercent,
                              int widthCropPercent) {
        // Set paint colors and styles for rectangle outline, background and fill
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint backgroundPaint = new Paint();
        backgroundPaint.setAlpha(140);
        canvas.drawPaint(backgroundPaint);
        Paint rectPaint = new Paint();
        rectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.WHITE);
        Paint outlinePaint = new Paint();
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStrokeWidth(4f);
        int surfaceWidth = surfaceHolder.getSurfaceFrame().width();
        int surfaceHeight = surfaceHolder.getSurfaceFrame().height();
        float cornerRadius = 25f;
        // Set rectangle centered in frame
        float rectTop = surfaceHeight * heightCropPercent / 2 / 100f;
        float rectLeft = surfaceWidth * widthCropPercent / 2 / 100f;
        float rectRight = surfaceWidth * (1 - widthCropPercent / 2 / 100f);
        float rectBottom = surfaceHeight * (1 - heightCropPercent / 2 / 100f);
        // Create the rectangle object
        RectF rect = new RectF(rectLeft, rectTop, rectRight, rectBottom);

        // draw to canvas
        canvas.drawRoundRect(
                rect, cornerRadius, cornerRadius, rectPaint
        );
        canvas.drawRoundRect(
                rect, cornerRadius, cornerRadius, outlinePaint
        );

        surfaceHolder.unlockCanvasAndPost(canvas);
        return rect;
    }

    private RectF drawLoadingOverlay(SurfaceHolder surfaceHolder, int heightCropPercent,
                                     int widthCropPercent) {
        // Set paint colors and styles for rectangle outline, background and fill
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint backgroundPaint = new Paint();
        backgroundPaint.setAlpha(140);
        canvas.drawPaint(backgroundPaint);
        Paint rectPaint = new Paint();
        rectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.WHITE);
        Paint outlinePaint = new Paint();
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStrokeWidth(4f);
        int surfaceWidth = surfaceHolder.getSurfaceFrame().width();
        int surfaceHeight = surfaceHolder.getSurfaceFrame().height();
        float cornerRadius = 25f;
        // Set rectangle centered in frame
        float rectTop = surfaceHeight * heightCropPercent / 2 / 100f;
        float rectLeft = surfaceWidth * widthCropPercent / 2 / 100f;
        float rectRight = surfaceWidth * (1 - widthCropPercent / 2 / 100f);
        float rectBottom = surfaceHeight * (1 - heightCropPercent / 2 / 100f);
        // Create the rectangle object
        RectF rect = new RectF(rectLeft, rectTop, rectRight, rectBottom);

        // draw to canvas
        canvas.drawRoundRect(
                rect, cornerRadius, cornerRadius, rectPaint
        );
        canvas.drawRoundRect(
                rect, cornerRadius, cornerRadius, outlinePaint
        );

        surfaceHolder.unlockCanvasAndPost(canvas);
        return rect;
    }

}
