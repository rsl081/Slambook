package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.CellIdentity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    //IMG
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private byte[] selectedImage;

    private ImageView diaryImageViewPic;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button addDiaryBtn;
    private Button cancelDiaryBtn;

    private String subject;
    private String message;
    Diary diary;
    //SQLite
    private DiaryAccountDb diaryDb;
    private AccountDb accountDb;
    Intent intentUpdateList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        this.accountDb = new AccountDb(this);
        this.diaryDb = new DiaryAccountDb(this);

        try {
            Uri uri = Uri.parse("android.resource://com.example.slambook/drawable/unisex");
            InputStream stream = getContentResolver().openInputStream(uri);
            selectedImage = getBytes(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Init();
    }


    private void Init(){
        diaryImageViewPic = findViewById(R.id.diary_add_capture);
        editTextSubject = findViewById(R.id.diary_edt_subject);
        editTextMessage = findViewById(R.id.diary_edt_message);
        addDiaryBtn = findViewById(R.id.btn_addDiary);
        cancelDiaryBtn = findViewById(R.id.diary_btn_cancel);

        //Listener
        diaryImageViewPic.setOnClickListener(this);
        addDiaryBtn.setOnClickListener(this);
        cancelDiaryBtn.setOnClickListener(this);

    }//End of Init


    public void Validation(){

        subject = editTextSubject.getText().toString();
        message = editTextMessage.getText().toString();

        if(IsError()){
            ShowErrorDialog();
        }else{
            AddListDiary();
        }

    }

    boolean IsError(){
        if(subject.equals("") || message.equals("")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_addDiary:
                Validation();
                break;
            case R.id.diary_add_capture:
                CaptureImage();
                break;
            case R.id.diary_btn_cancel:
//                TimeZone tz = TimeZone.getTimeZone("Asia/Taipei");
//                Calendar c = Calendar.getInstance(tz);
//                int hours = c.get(Calendar.HOUR_OF_DAY);
//                int min = c.get(Calendar.MINUTE);
//                int pm_am = c.get(Calendar.AM_PM);
//                String a_p = "";
//                if(pm_am == 0)
//                {
//                    a_p = "am";
//                }else{
//                    a_p = "pm";
//                }
//
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//
//                String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
//                        String.format("%02d" , c.get(Calendar.MINUTE));
//
//                Toast.makeText(this, time + " "+ a_p, Toast.LENGTH_LONG).show();
//                Toast.makeText(this, month + 1 +"/" + day + "/" + year, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }//End of OnClick

    private String CalendarD()
    {
        TimeZone tz = TimeZone.getTimeZone("Asia/Taipei");
        Calendar c = Calendar.getInstance(tz);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return month + 1 +"/" + day + "/" + year;
    }

    private String Time()
    {
        TimeZone tz = TimeZone.getTimeZone("Asia/Taipei");
        Calendar c = Calendar.getInstance(tz);
//        int hours = c.get(Calendar.HOUR_OF_DAY);
//        int min = c.get(Calendar.MINUTE);
        int pm_am = c.get(Calendar.AM_PM);
        String a_p = "";
        if(pm_am == 0)
        {
            a_p = "am";
        }else{
            a_p = "pm";
        }

        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));
        return time + " "+ a_p;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    selectedImage = stream.toByteArray();
                    diaryImageViewPic.setImageBitmap(photo);
                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        InputStream iStream = getContentResolver().openInputStream(uri);
                        selectedImage = getBytes(iStream);
                        diaryImageViewPic.setImageURI(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }//switch
    }

    private void CaptureImage()
    {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            DialogForImages();
        }
    }

    private void DialogForImages()
    {
        String[] items = {"Take Photo", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Take Photo")){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }else if(items[which].equals("Choose from gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , GALLERY_REQUEST);
                }else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void AddListDiary()
    {
        intentUpdateList = new Intent();
        String getSubject = editTextSubject.getText().toString();
        String getMessage = editTextMessage.getText().toString();

        Bundle bundle = this.getIntent().getExtras();
        long accountID = bundle.getLong("add_diary",0);

        diary = diaryDb.createDiary(selectedImage,getSubject,getMessage,CalendarD(),Time(),accountID);

        intentUpdateList.putExtra("new_diary", diary);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }

    //=====================================================
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void ShowErrorDialog(){
        String str_subject = "Subject is Missing!\n";
        String str_message = "\nDiary is Missing!\n";

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Missing In Action!");
        error.setMessage((subject.matches("") ? str_subject : str_subject.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                        (message.matches("") ? str_message : str_message.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
    }
}