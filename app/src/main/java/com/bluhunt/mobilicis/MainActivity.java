package com.bluhunt.mobilicis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DeviceInfo deviceInfo = new DeviceInfo(MainActivity.this);
    private DatabaseReference databaseReference;

    @Override
    protected void onResume() {
        super.onResume();

        ImageView batteryImageCheck = findViewById(R.id.batterytestcheck);
        ImageView modelImageCheck  = findViewById(R.id.modeltestcheck);
        ImageView cameraImageCheck = findViewById(R.id.cameratestcheck);
        ImageView storageImageCheck = findViewById(R.id.storagetestcheck);
        ImageView cpugpuImageCheck = findViewById(R.id.cpugputestcheck);
        ImageView sensorImageCheck = findViewById(R.id.sensortestcheck);

        if (Flag.getBatteryImageCheck()){
            batteryImageCheck.setImageResource(R.drawable.test_completed);
            Log.d("Flag Changed", "Flag Changed");
        }else {
            batteryImageCheck.setImageResource(R.drawable.test_pending);
            Log.d("No Flag", "No Flag ");
        }

        if (Flag.getModelImageCheck()  ){
            modelImageCheck.setImageResource(R.drawable.test_completed);
        }
        else
        {
            modelImageCheck.setImageResource(R.drawable.test_pending);
        }

        if (Flag.getCameraImageCheck() ){
            cameraImageCheck.setImageResource(R.drawable.test_completed);
        }
        else
        {
            cameraImageCheck.setImageResource(R.drawable.test_pending);
        }


        if (Flag.getStorageImageCheck() ){
            storageImageCheck.setImageResource(R.drawable.test_completed);
        }
        else
        {
            storageImageCheck.setImageResource(R.drawable.test_pending);
        }

        if (Flag.getCpugpuImageCheck() ){
            cpugpuImageCheck.setImageResource(R.drawable.test_completed);
        }
        else
        {
            cpugpuImageCheck.setImageResource(R.drawable.test_pending);
        }

        if (Flag.getSensorImageCheck() ){
            sensorImageCheck.setImageResource(R.drawable.test_completed);
        }
        else
        {
            sensorImageCheck.setImageResource(R.drawable.test_pending);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button GenPdf = findViewById(R.id.gen_pdf);

        findViewById(R.id.model).setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), ModelActivity.class)));

        findViewById(R.id.battery).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), BatteryActivity.class)));

        findViewById(R.id.camera).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CameraActivity.class)));

        findViewById(R.id.storage).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StorageActivity.class)));

        findViewById(R.id.gpu).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CPUGPUActivity.class)));

        findViewById(R.id.sensor).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SensorActivity.class)));

        findViewById(R.id.gen_pdf).setOnClickListener(view -> GeneratePdf());
    }

    private void GeneratePdf() {

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(deviceInfo.getModel());

        // Retrieve data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String jsonData = convertDataToJSON(dataSnapshot);
                    if (jsonData != null) {
                        saveJSONToFile(MainActivity.this, "data.json", jsonData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        copyFileToPublicDirectory(this, "data.json");
        //String jsonFilePath = "/sdcard/Android/data/com.spacester.mobilicis/files/public_directory/data.json"; // Replace with the actual file path
        convertJsonFileToPdf(this, "data.json", "output.pdf");
        copyFileToPublicDirectory(this, "output.pdf");


    }

    private String convertDataToJSON(DataSnapshot dataSnapshot) {
        try {
            JSONObject json = new JSONObject();

            if (dataSnapshot.exists()) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    Object value = child.getValue();
                    json.put(key, value);
                }
            }

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Save JSON string to a file
    private void saveJSONToFile(Context context, String fileName, String json) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
            Log.d("File", "JSON data saved to file: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("File", "Error saving JSON data to file: " + fileName);
        }
    }

    private void copyFileToPublicDirectory(Context context, String fileName) {
        File privateFile = new File(context.getFilesDir(), fileName);
        File publicDirectory = new File(context.getExternalFilesDir(null), "public_directory");

        if (!publicDirectory.exists()) {
            publicDirectory.mkdirs();
        }

        File publicFile = new File(publicDirectory, fileName);

        try (FileInputStream fis = new FileInputStream(privateFile);
             FileOutputStream fos = new FileOutputStream(publicFile)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertJsonFileToPdf(Context context, String jsonFileName, String pdfFileName) {
        // Check if external storage is available and not read-only
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File publicDirectory = new File(Environment.getExternalStorageDirectory(), "public_directory");
            File jsonFile = new File(publicDirectory, jsonFileName);
            if (jsonFile.exists()) {
                try {
                    FileReader fileReader = new FileReader(jsonFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder jsonContent = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        jsonContent.append(line).append("\n");
                    }

                    // Create a PDF document
                    PdfWriter writer = new PdfWriter(context.openFileOutput(pdfFileName, Context.MODE_PRIVATE));
                    PdfDocument pdfDocument = new PdfDocument(writer);
                    Document document = new Document(pdfDocument);

                    // Create a paragraph with the JSON content
                    Paragraph paragraph = new Paragraph(jsonContent.toString());

                    // Add the paragraph to the document
                    document.add(paragraph);

                    // Close the document
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // The JSON file does not exist in the public directory
            }
        } else {
            // External storage is either read-only or unavailable
        }
    }

}