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
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class EditDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private ImageView imageViewPicDiary;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button updateDiaryBtn;
    private Button cancelDiaryBtn;

    private String subject;
    private String message;

    Diary diary;
    DiaryAccountDb diaryAccountDb;

    int positionOfDiaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
        this.diaryAccountDb = new DiaryAccountDb(this);
        Init();
    }

    private void Init(){
        Intent intent = getIntent();
        positionOfDiaryList = intent.getIntExtra(DiaryListActivity.POSITION, 0);

        diary = intent.getParcelableExtra(DiaryListActivity.DIARY_LIST);

        byte[] newProfile = diary.getbyteDiaryPic();
        String subj = diary.getSubject();
        String msg = diary.getMessage();

        imageViewPicDiary = findViewById(R.id.edt_diary_add_capture);
        editTextSubject = findViewById(R.id.edit_diary_subject);
        editTextMessage = findViewById(R.id.edit_diary_message);
        updateDiaryBtn = findViewById(R.id.edit_btn_addDiary);
        cancelDiaryBtn = findViewById(R.id.edit_diary_btn_cancel);

        Bitmap bitmapImages = BitmapFactory.decodeByteArray(newProfile, 0, newProfile.length);
        imageViewPicDiary.setImageBitmap(bitmapImages);
        editTextSubject.setText(subj);
        editTextMessage.setText(msg);

        imageViewPicDiary.setOnClickListener(this);
        updateDiaryBtn.setOnClickListener(this);
        cancelDiaryBtn.setOnClickListener(this);
    }//End of Init

    public void Validation(){

        subject = editTextSubject.getText().toString();
        message = editTextMessage.getText().toString();

        if(IsError()){
            ShowErrorDialog();
        }else{
            UpdateList();
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
            case R.id.edit_btn_addDiary:
                Validation();
                break;
            case R.id.edt_diary_add_capture:
                CaptureImage();
                break;
            case R.id.edit_diary_btn_cancel:
                finish();
                break;
        }
    }//End of OnClick


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
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewPicDiary.setImageBitmap(photo);
            diary.setBitmapImageDiary(photo);
        }
    }


    public void UpdateList(){
        Bitmap bitmap = ((BitmapDrawable) imageViewPicDiary.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        String getSubjet = editTextSubject.getText().toString();
        String getMessage = editTextMessage.getText().toString();

        diary.setbyteDiaryPic(imageInByte);
        diary.setSubject(getSubjet);
        diary.setMessage(getMessage);
        diary.setDate(CalendarD());
        diary.setTime(Time());

        Intent intentUpdateList = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        long diaryId = bundle.getLong("diary_id");
        diaryAccountDb.UpdateDiary(diaryId,imageInByte,getSubjet,getMessage,CalendarD(),Time());

        intentUpdateList.putExtra("update_list", positionOfDiaryList);
        intentUpdateList.putExtra("edit_diary", diary);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }


    public void ShowErrorDialog(){
        String str_subject = "Subject is Missing!\n";
        String str_message = "\nMessage is Missing!\n";

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Error/s!");
        error.setMessage((subject.matches("") ? str_subject : str_subject.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                        (message.matches("") ? str_message : str_message.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
    }

    private void CaptureImage()
    {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
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
        int pm_am = c.get(Calendar.AM_PM);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String a_p = "";
        if(pm_am == 0)
        {
            a_p = "am";
        }else{
            a_p = "pm";
        }

        if(hour > 12)
        {
            hour = hour - 12;
        }

        String time = String.format("%02d" , hour)+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));
        return time + " "+ a_p;
    }
}