package com.cmput301f20t13.treatyourshelf.ui.camera;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.ui.AddEditBook.AddBookViewModel;
import com.cmput301f20t13.treatyourshelf.ui.BookDetails.BookDetailsFragmentArgs;
import com.cmput301f20t13.treatyourshelf.ui.RequestDetails.RequestDetailsViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

/**
 * Fragment that allows user to scan ISBN numbers of books.
 */
public class CameraXFragment extends Fragment {

    private int serviceCode;
    private ImageCapture imageCapture;
    private File outputDirectory;
    private CameraControl cameraControl;
    private ExecutorService cameraExecutor;
    private String[] permissions;
    private RectF rectF;
    private boolean cameraFlashToggled = false;

    /**
     * Constructor for BarcodeScanning Fragment, sets permissions string array to Camera.
     */
    public CameraXFragment() {
        permissions = new String[]{Manifest.permission.CAMERA};
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        Utils.hideKeyboardFrom(requireContext(), view);

        serviceCode = CameraXFragmentArgs.fromBundle(getArguments()).getServiceCode();


        ImageButton closeCameraBt = view.findViewById(R.id.close_camera_bt);
        ImageButton toggleCameraFlashBt = view.findViewById(R.id.camera_flash_bt);
        closeCameraBt.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
        toggleCameraFlashBt.setOnClickListener(view1 -> {
            System.out.println("Clicking");
            if (cameraFlashToggled) {
                // Camera Flash is On, want to turn off
                cameraFlashToggled = false;
                toggleCameraFlashBt.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_flash_off_24));
                cameraControl.enableTorch(cameraFlashToggled);
            } else {
                cameraFlashToggled = true;
                toggleCameraFlashBt.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_flash_on_24));
                cameraControl.enableTorch(cameraFlashToggled);
            }
        });


        return view;
    }

    /**
     * Check if permissions to use camera were accepted
     *
     * @return boolean indicating whether permissions were accepted or not
     */
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
            cameraExecutor = Executors.newSingleThreadExecutor();
        } else {
            requestPermissions(permissions, 11);
        }

    }

    /**
     * Starts Camera Preview
     */
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
//                            System.out.println(barcode.getRawValue())

                            RequestDetailsViewModel requestDetailsViewModel =
                                    new ViewModelProvider(requireActivity()).get(RequestDetailsViewModel.class);


                            if (serviceCode == 0) {


                                cameraProvider.unbindAll();
                                //Toast.makeText(requireContext(), barcode.getRawValue(), Toast.LENGTH_SHORT).show();
                                //TODO: Start barcode loading animation
                                //TODO: Check firebase if result exists, if it dosen't, bind camera again, else open bottom sheet and display result
                                BottomSheetScannedISBNResults bottomSheetScannedISBNResults = new BottomSheetScannedISBNResults(barcode.getRawValue());
                                bottomSheetScannedISBNResults.show(getChildFragmentManager(), null);
                                getChildFragmentManager().executePendingTransactions();
                                bottomSheetScannedISBNResults.setDissmissListener(() -> {
                                    //Dialog got dismissed
                                    cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageAnalysis, preview);
                                    cameraControl.enableTorch(cameraFlashToggled);

                                });
                            } else if (serviceCode == 1) {
                                // Add set isbn value to shared viewModel
                                AddBookViewModel addBookViewModel = new ViewModelProvider(requireActivity()).get(AddBookViewModel.class);
                                addBookViewModel.setScannedIsbn(barcode.getRawValue());
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                            } else if (serviceCode == 2) {
                                requestDetailsViewModel.setOwnBorrowedScannedIsbn(barcode.getRawValue());
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                            } else if (serviceCode == 3) {
                                requestDetailsViewModel.setBorBorrowedScannedIsbn(barcode.getRawValue());
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                            } else if (serviceCode == 4) {
                                requestDetailsViewModel.setBorReturnedScannedIsbn(barcode.getRawValue());
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                            } else if (serviceCode == 5) {
                                requestDetailsViewModel.setOwnReturnedScannedIsbn(barcode.getRawValue());
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                            }
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

    /**
     * Stops Camera Preview
     */
    private void stopCameraPreview() {

        cameraExecutor.shutdown();
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
                cameraExecutor = Executors.newSingleThreadExecutor();
            } else {
                Toast.makeText(getContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            }
        }
    }

    /**
     * Draws the overlay over the surfaceholder to give a visual indicator for the field of scanning for the barcode
     *
     * @param surfaceHolder     the surfaceholder to draw the overlay on.
     * @param heightCropPercent The height percentage to crop the fullscreen in order to draw the overlay
     * @param widthCropPercent  The width percentage to crop the fullscreen in order to draw the overlay
     * @return the rectangle used for the barcode scanning voerlay.
     */
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
