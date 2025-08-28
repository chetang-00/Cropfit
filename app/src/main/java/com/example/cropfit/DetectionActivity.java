package com.example.cropfit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropfit.detection.ImageClassifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetectionActivity extends AppCompatActivity {

    private String mModelPath = "plant_disease_tfmodel.tflite";
    private String mLabelPath = "plant_labels.txt";
    private String mSamplePath = "soybean.jpg";
    private static final int INPUT_SIZE = 256;

    private ImageClassifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult,remedyText;
    private Button btnDetectObject, mCameraButton,mGalleryButton;
    private ImageView imageViewResult;
    private Bitmap _selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        imageViewResult = findViewById(R.id.mPhotoImageView);
        textViewResult = findViewById(R.id.resultText);
        remedyText = findViewById(R.id.remedyText);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());

        mCameraButton = findViewById(R.id.mCameraButton);
        mGalleryButton = findViewById(R.id.mGalleryButton);
        btnDetectObject = findViewById(R.id.mDetectButton);
        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_selectedBitmap==null){
                    return;
                }
                _selectedBitmap = Bitmap.createScaledBitmap(_selectedBitmap, INPUT_SIZE, INPUT_SIZE, false);

                final List<ImageClassifier.Recognition> results = classifier.recognizeImage(_selectedBitmap);

                if(results!=null && !results.isEmpty()){


                ImageClassifier.Recognition recognition = results.get(0);
                System.out.println("recognition----"+recognition.toString());
                    DetectionData detectionData = new DetectionData();


                if(recognition.getTitle().equals("Blight") || recognition.getTitle().equals("BlightGray_Leaf_Spot"))
                {
                    detectionData.originalTitle=recognition.getTitle();
                    detectionData.name="Blight";
                    detectionData.preventions="Spraying of recommended product @ 2.5 g/l as soon as pustules appear and repeat at 10 days interval till flowering. Seed Treatment are not recommended.";
                    detectionData.symptoms="The symptoms appear as small, oval, water-soaked spots on the lower leaves first. As the disease progresses, they start to appear on the upper part of the plant. Older spots slowly grow into tan, long cigar-shaped necrotic lesions with distinct dark specks and pale green, water-soaked borders. These lesions later coalesce and engulf a large part of the leaf blade and stalk, sometimes leading to death and lodging. If the infection spreads to the upper parts of the plant during the development of the cob. Severe yield losses can occur (up to 70%).";
                    detectionData.product1= "Cyproconazole";
                    detectionData.product2= "Trifloxystrobin";

                }
            else if(recognition.getTitle().equals("Common_Rust"))
                {
                    detectionData.originalTitle=recognition.getTitle();
                    detectionData.name="Common Rust";
                    detectionData.preventions="Spraying of recommended product @ 2.5 g/l as soon as pustules appear and repeat at 10 days interval till flowering.";
                    detectionData.symptoms="Lesions begin as flecks on leaves that develop into small tan spots. Spots turn into elongated brick-red to cinnamon-brown pustules with jagged appearance. Found on both upper AND lower leaf surfaces. Pustules turn dark brown to black late in the season. Occurs on leaf only, NOT on sheaths, stalks, ear shanks and husk leaves";
                    detectionData.product1= "Mancozeb Fungicide";
                    detectionData.product2= "Chlorothalonil";
                }
            else if(recognition.getTitle().equals("Grey_Leaf_Spot") || recognition.getTitle().equals("Gray_Leaf_Spot"))
                {
                    detectionData.originalTitle=recognition.getTitle();
                    detectionData.name="Grey Leaf Spot";
                    detectionData.preventions="Spraying of recommended product @ 2.5 g/l as soon as pustules appear and repeat at 10 days interval till flowering.";
                    detectionData.symptoms="Gray leaf spot lesions begin as small necrotic pinpoints with chlorotic halos, these are more visible when leaves are backlit. Coloration of initial lesions can range from tan to brown before sporulation begins.Lesion expansion is limited by parallel leaf veins, resulting in the blocky shaped 'spots'. As sporulation commences, the lesions take on a more gray coloration. Entire leaves can be killed when weather conditions are favorable, and rapid disease progression causes lesions to merge.";
                    detectionData.product1= "Propiconazole";
                    detectionData.product2= "Azoxystrobin";
                }
            else
                {

                    textViewResult.setText("Healthy");
                    remedyText.setText("Take care of crop.");

                    return;
                }
                    Intent i1 = new Intent(DetectionActivity.this,ResultActivity.class);
                    i1.putExtra("DetectionData", detectionData);
                    startActivity(i1);
            }
            }
        });
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        initTensorFlowAndLoadModel();
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = new ImageClassifier(
                            getAssets(),
                            mModelPath,
                            mLabelPath,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Start OnActivity");
        if (resultCode == RESULT_OK) {
            System.out.println("Start OnActivity 1");
            if (requestCode == 1) {
                System.out.println("Start OnActivity 2");
                if( data != null && data.getExtras()!=null) {
                    System.out.println("Start OnActivity 3");
                    _selectedBitmap = (Bitmap) data.getExtras().get("data");
                    _selectedBitmap = scaleImage(_selectedBitmap);
                    imageViewResult.setImageBitmap(_selectedBitmap);
                }
            } else if (requestCode == 2) {
                System.out.println("Start OnActivity 4 ");
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                _selectedBitmap = (BitmapFactory.decodeFile(picturePath));
                _selectedBitmap = scaleImage(_selectedBitmap);
                Log.w("gallery", picturePath);
                imageViewResult.setImageBitmap(_selectedBitmap);
            }
        }

    }

    Bitmap scaleImage( Bitmap bitmap) {
        System.out.println("Start bitmap");
        int orignalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = (float) INPUT_SIZE / orignalWidth;
        float scaleHeight = (float) INPUT_SIZE / originalHeight;
        Matrix matrix =new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true);
    }


}