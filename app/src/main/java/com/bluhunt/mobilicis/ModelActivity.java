package com.bluhunt.mobilicis;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.rootbeer.RootBeer;

public class ModelActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DeviceInfo deviceInfo = new DeviceInfo(ModelActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        DeviceInfo deviceInfo = new DeviceInfo(ModelActivity.this);
        String manufacturer = deviceInfo.getManufacturer();
        String model = deviceInfo.getModel();
        String ram = deviceInfo.getRam();
        String osVersion = Build.VERSION.RELEASE;

        TextView mManufacturer = findViewById(R.id.manufacturer);
        TextView mModel = findViewById(R.id.model);
        TextView mRam = findViewById(R.id.ram);
        TextView verison = findViewById(R.id.verison);
        TextView isRoot = findViewById(R.id.isroot);
        TextView Bluetooth = findViewById(R.id.bluetooth);


        mManufacturer.setText("Manufacturer : "+ manufacturer);
        mDatabase.child(deviceInfo.getModel()).child("Model")
                .child("Manufacturer").setValue(manufacturer);

        mModel.setText("Model : "+ model);
        mDatabase.child(deviceInfo.getModel()).child("Model")
                .child("Model").setValue(model);

        mRam.setText("Ram : "+ ram);
        mDatabase.child(deviceInfo.getModel()).child("Model")
                .child("Ram").setValue(ram);

        verison.setText("OS version : "+ osVersion);
        mDatabase.child(deviceInfo.getModel()).child("Model")
                .child("OS version").setValue(osVersion);


        RootBeer rootBeer = new RootBeer(this.getBaseContext());

        if (rootBeer.isRooted()) {
            isRoot.setText("Root Status: Rooted");
            mDatabase.child(deviceInfo.getModel()).child("Model")
                    .child("Root Status").setValue("Rooted");
        } else {
            isRoot.setText("Root Status: Non-Rooted");
            mDatabase.child(deviceInfo.getModel()).child("Model")
                    .child("Root Status").setValue("Non-Rooted");
        }


        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            verison.setText("Bluetooth : Supported");
            mDatabase.child(deviceInfo.getModel()).child("Model")
                    .child("Bluetooths").setValue("Supported");
        } else {
            verison.setText("Bluetooth : Not Supported ");
            mDatabase.child(deviceInfo.getModel()).child("Model")
                    .child("Bluetooth").setValue("Not Supported");
        }

        Flag.setModelImageCheck(Boolean.TRUE);

    }
}