package com.example.slambook;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

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
    private Button cancelBtn;
    private String gender = "";
    private String getGender = "";

    private String name;
    private String remark;
    private String birthday;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;

    DatePickerDialog dateDialog;
    Person person;

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

        person = intent.getParcelableExtra(HomeActivity.PEOPLE_LIST);

        Bitmap newProfile = person.getBitmapImage();
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
        cancelBtn = findViewById(R.id.btn_cancel);


        if(person.getBitmapImage() != null){
            imageViewPic.setImageBitmap(newProfile);
        }else{
            imageViewPic.setImageResource(profilePic);
        }

        editTextName.setText(name);
        editTextRemark.setText(remark);
        if(gender.equals("female")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioFemale.setText(capFirstLetter + gender.substring(1));
            radioFemale.setChecked(true);
            getGender = radioFemale.getText().toString();
        }else if(gender.equals("male")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioMale.setText(capFirstLetter + gender.substring(1));
            radioMale.setChecked(true);
            getGender = radioMale.getText().toString();
        }else{
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioOtherGender.setText(capFirstLetter + gender.substring(1));
            radioOtherGender.setChecked(true);
            getGender = radioOtherGender.getText().toString();
        }

        editTextBday.setText(birthday);
        editTextAddress.setText(address);
        editTextContact.setText(contact);
        editTextHobbies.setText(hobbies);
        editTextGoals.setText(goals);

        imageViewPic.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        radioFemale.setOnClickListener(this);
        radioMale.setOnClickListener(this);
        radioOtherGender.setOnClickListener(this);
    }//End of Init

    public void datepickerdialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                editTextBday.setText(month+1+"/"+dayOfMonth+"/"+year);
            }
        }, year, month,day);
        editTextBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    }

    public void Validation(){

        name = editTextName.getText().toString();
        remark = editTextRemark.getText().toString();
        birthday = editTextBday.getText().toString();
        address = editTextAddress.getText().toString();
        contact = editTextContact.getText().toString();
        hobbies = editTextHobbies.getText().toString();
        goals = editTextGoals.getText().toString();

        if(IsError()){
            ShowErrorDialog();
        }else{
            UpdateList();
        }

    }

    boolean IsError(){
        if( name.equals("") || remark.equals("") ||
            birthday.equals("") || address.equals("") ||
            contact.equals("") || gender.equals("") ||
            hobbies.isEmpty() || goals.equals("")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_editEntry:
                    Validation();
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
            case R.id.birthday:
                datepickerdialog();
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
            case R.id.btn_cancel:
                finish();
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
            person.setBitmapImage(photo);
        }
    }



    public void UpdateList(){
        Bitmap getProfilePic = person.getBitmapImage();
        String getName = editTextName.getText().toString();
        String getRemark = editTextRemark.getText().toString();
        String getBday = editTextBday.getText().toString();
        String getAddress = editTextAddress.getText().toString();
        String getContact = editTextContact.getText().toString();
        String getHobbies = editTextHobbies.getText().toString();
        String getGoals = editTextGoals.getText().toString();

        person.setBitmapImage(getProfilePic);
        person.setAccountName(getName);
        person.setRemark(getRemark);
        person.setGender(getGender);
        person.setBirthday(getBday);
        person.setAddress(getAddress);
        person.setContact(getContact);
        person.setHobbies(getHobbies);
        person.setGoals(getGoals);

        Intent intentUpdateList = new Intent();
        intentUpdateList.putExtra("edit_person", person);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }


    public void ShowErrorDialog(){
        String str_name = "Name is Missing!\n";
        String str_remark = "\nRemark is Missing!\n";
        String str_birthday = "\nBirthday is Missing!\n";
        String str_genders = "\nGender is Missing!\n";
        String str_address = "\nAddress Address is Missing!\n";
        String str_contact = "\nContact is Missing!\n";
        String str_hobbies = "\nHobbies is Missing!\n";
        String str_goals = "\nGoals is Missing!\n";


        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Error/s!");
        error.setMessage(   (name.matches("") ? str_name : str_name.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (remark.matches("") ? str_remark : str_remark.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (birthday.matches("") ? str_birthday : str_birthday.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (gender.matches("") ? str_genders : str_genders.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (address.matches("") ? str_address : str_address.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (contact.matches("") ? str_contact : str_contact.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (hobbies.matches("") ? str_hobbies : str_hobbies.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (goals.matches("") ? str_goals : str_goals.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
    }


}//End of Activity
