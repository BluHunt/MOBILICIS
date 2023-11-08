package com.bluhunt.mobilicis;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class StorageActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DeviceInfo deviceInfo = new DeviceInfo(StorageActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        File externalStorage = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(externalStorage.getPath());
        long blockSize = statFs.getBlockSizeLong();
        long totalBlocks = statFs.getBlockCountLong();
        long availableBlocks = statFs.getAvailableBlocksLong();

        double totalSize = (double) totalBlocks * blockSize / (1024 * 1024 * 1024);
        double availableSize = (double) availableBlocks * blockSize / (1024 * 1024 * 1024);
        double usedSize = totalSize - availableSize;

        TextView available = findViewById(R.id.available);
        available.setText("Available Size: " + availableSize + " GB");
        mDatabase.child(deviceInfo.getModel()).child("Storage")
                .child("Available Size").setValue(availableSize + " GB");

        TextView total = findViewById(R.id.total);
        total.setText("Total Size: " + totalSize + " GB");
        mDatabase.child(deviceInfo.getModel()).child("Storage")
                .child("Total Size").setValue( totalSize + " GB");

        TextView used = findViewById(R.id.used);
        used.setText("Used Size: " + usedSize + " GB");
        mDatabase.child(deviceInfo.getModel()).child("Storage")
                .child("Used Size").setValue(usedSize + " GB");

        Flag.setStorageImageCheck(Boolean.TRUE);
    }
}