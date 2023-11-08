package com.bluhunt.mobilicis;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.bluhunt.mobilicis.R;
public class CameraActivity extends AppCompatActivity {
    CameraCharacteristics characteristics;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DeviceInfo deviceInfo = new DeviceInfo(CameraActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);



        String[] cameraIds = new String[0];
        try {
            cameraIds = manager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (cameraIds.length > 0) {
            String cameraId = cameraIds[0];
            try {
                characteristics = manager.getCameraCharacteristics(cameraId);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizes = map.getOutputSizes(ImageFormat.JPEG);
        double megapixels = (double) (sizes[0].getWidth() * sizes[0].getHeight()) / 1000000;
        double aperture = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES)[0];


        TextView mega = findViewById(R.id.mega);
        mega.setText("Camera megapixels : "+ megapixels);
        mDatabase.child(deviceInfo.getModel()).child("Camera")
                .child("Camera megapixels").setValue(megapixels);

        TextView apertures = findViewById(R.id.aperture);
        apertures.setText("Camera aperture : "+ aperture);
        mDatabase.child(deviceInfo.getModel()).child("Camera")
                .child("Camera aperture").setValue(aperture);

        Flag.setCameraImageCheck(Boolean.TRUE);
    }

}