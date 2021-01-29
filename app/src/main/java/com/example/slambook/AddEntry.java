package com.example.slambook;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEntry extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {

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
    private Button addBtn;
    private Button cancelBtn;
    private String gender = "female";
    private String getGender = "";

    private String name;
    private String remark;
    private String birthday;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;

    Person person = new Person(R.drawable.woman,"Bino Santos", "BestFriend", "March 12 2020",
            "Lesbian", "Baliwag Bulacan", "09323216432", "Eating", "Cars");

    int positionOfPeopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry);

        Init();
    }

    private void Init(){
        imageViewPic = findViewById(R.id.add_capture);
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
        addBtn = findViewById(R.id.btn_addEntry);
        cancelBtn = findViewById(R.id.btn_cancel);

        if(radioFemale.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioFemale.setText(capFirstLetter + gender.substring(1));
            radioFemale.setChecked(true);
            getGender = radioFemale.getText().toString();
        }else if(radioMale.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioMale.setText(capFirstLetter + gender.substring(1));
            radioMale.setChecked(true);
            getGender = radioMale.getText().toString();
        }else if(radioOtherGender.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioOtherGender.setText(capFirstLetter + gender.substring(1));
            radioOtherGender.setChecked(true);
            getGender = radioOtherGender.getText().toString();
        }

        imageViewPic.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        radioFemale.setOnClickListener(this);
        radioMale.setOnClickListener(this);
        radioOtherGender.setOnClickListener(this);
    }//End of Init

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
            case R.id.btn_addEntry:
                Validation();
                break;
            case R.id.add_capture:
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
        intentUpdateList.putExtra("new_person", person);
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
        error.setTitle("Missing In Action!");
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
