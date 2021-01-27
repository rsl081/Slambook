package com.example.slambook;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class EditEntry extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private ImageView imageViewPic;
    private EditText editTextName;
    private EditText editTextRemark;
    private EditText editTextBday;
    private RadioButton radioFemale;
    private RadioButton radioMale;
    private RadioButton radioOtherGender;
    private EditText editTextAddress;
    private EditText editTextContact;
    private EditText editTextHobbies;
    private EditText editTextGoals;
    private Button updateBtn;
    private String gender = "";
    String getGender = "";
    SpinnersActivity mySpinners = new SpinnersActivity(this);

    int positionOfPeopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry_list);

        Init();
    }

    private void Init(){
        Intent intent = getIntent();

        positionOfPeopleList = intent.getIntExtra(HomeActivity.POSITION, 0);

        Person person = intent.getParcelableExtra(HomeActivity.PEOPLE_LIST);

        int profilePic = person.getProfilePic();
        String name = person.getAccountName();
        String remark = person.getRemark();
        String birthday = person.getBirthday();
        gender = person.getGender().toLowerCase();
        String address = person.getAddress();
        String contact = person.getContact();
        String hobbies = person.getHobbies();
        String goals = person.getGoals();

        imageViewPic = findViewById(R.id.capture);
        editTextName = findViewById(R.id.edt_name);
        editTextRemark = findViewById(R.id.remark);
        editTextBday = findViewById(R.id.birthday);
        radioFemale = (RadioButton) findViewById(R.id.rd_female);
        radioMale = (RadioButton)findViewById(R.id.rd_male);
        radioOtherGender = (RadioButton)findViewById(R.id.rd_others);
        editTextAddress = findViewById(R.id.address);
        editTextContact = findViewById(R.id.contactnum);
        editTextHobbies = findViewById(R.id.hobbies);
        editTextGoals = findViewById(R.id.goals);
        updateBtn = findViewById(R.id.btn_editEntry);

        imageViewPic.setImageResource(profilePic);
        editTextName.setText(name);
        editTextRemark.setText(remark);
        if(gender.equals("female")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioFemale.setText(capFirstLetter + gender.substring(1));
            radioFemale.setChecked(true);
        }else if(gender.equals("male")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioMale.setText(capFirstLetter + gender.substring(1));
            radioMale.setChecked(true);
        }else{
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioOtherGender.setText(capFirstLetter + gender.substring(1));
            radioOtherGender.setChecked(true);
        }
        editTextBday.setText(birthday);
        editTextAddress.setText(address);
        editTextContact.setText(contact);
        editTextHobbies.setText(hobbies);
        editTextGoals.setText(goals);

        imageViewPic.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

        radioFemale.setOnClickListener(this);
        radioMale.setOnClickListener(this);
        radioOtherGender.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_editEntry:
                    UpdateList();
                break;
            case R.id.capture:
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            break;
            case R.id.rd_female:
                getGender = radioFemale.getText().toString();
            break;
            case R.id.rd_male:
                getGender = radioMale.getText().toString();
            break;
            case R.id.rd_others:
                openDialog();
            break;
        }
    }//End of OnClick

    public void openDialog() {
        DialogOthersGender exampleDialog = new DialogOthersGender();
        exampleDialog.show(getSupportFragmentManager(), "gender others dialog");
    }

    @Override
    public void applyTexts(String username) {
        radioOtherGender.setText(username);
        getGender = radioOtherGender.getText().toString();
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
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewPic.setImageBitmap(photo);
            data.putExtra("profile_pic", photo);
            setResult(RESULT_OK, data);
        }
    }

    public void UpdateList(){
        BitmapDrawable drawable = (BitmapDrawable) imageViewPic.getDrawable();
        Bitmap getProfilePic = drawable.getBitmap();

        String getName = editTextName.getText().toString();
        String getRemark = editTextRemark.getText().toString();
        String getBday = editTextBday.getText().toString();
        String getAddress = editTextAddress.getText().toString();
        String getContact = editTextContact.getText().toString();
        String getHobbies = editTextHobbies.getText().toString();
        String getGoals = editTextGoals.getText().toString();

        Intent intentUpdateList = new Intent();

        intentUpdateList.putExtra("update_list", positionOfPeopleList);
        intentUpdateList.putExtra("profile_pic", getProfilePic);
        intentUpdateList.putExtra("name", getName);
        intentUpdateList.putExtra("remark", getRemark);
        intentUpdateList.putExtra("bday", getBday);
        intentUpdateList.putExtra("gender", getGender);
        intentUpdateList.putExtra("address", getAddress);
        intentUpdateList.putExtra("contact", getContact);
        intentUpdateList.putExtra("hobbies", getHobbies);
        intentUpdateList.putExtra("goals", getGoals);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }


}//End of Activity
