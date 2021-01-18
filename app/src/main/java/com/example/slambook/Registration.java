package com.example.slambook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.slambook.DialogOthersGender;
import com.example.slambook.R;
import com.example.slambook.SpinnersActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Registration extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {

    //Edit Text
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private EditText editEmailAddress;
    private EditText editLn;
    private EditText editFn;
    private EditText editMn;
    private EditText editStreet;
    private EditText editHouseNum;
    private EditText editPhone;
    private EditText editSecurityQ1;
    private EditText editSecurityQ2;
    private EditText editSecurityQ3;

    //Radio Button
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioButton rbOthers;

    //Button
    private Button btnRegister;
    private EditText editClickableBday;

    //CheckBox
    private CheckBox[] checkHobbies = new CheckBox[10];

    //Username -> Bday
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String bday;

    //FullName
    private String lastName;
    private String firstName;
    private String middleName;
    private String fullName;
    private String gender = "";

    //Address
    private String street;
    private String houseNum;
    private String barangay;
    private String municipality;
    private String province;
    private String address;
    SpinnersActivity mySpinners = new SpinnersActivity(this);

    //Contact -> Security Question
    private String contact;
    private String selected;
    private StringBuilder stringBuildHobby = new StringBuilder();
    private List<String> listHobbies = new ArrayList<String>();
    private String securityQ1;
    private String securityQ2;
    private String securityQ3;
    private String secQuesText1 ="sample";
    private String secQuesText2;
    private String secQuesText3;


    DatePickerDialog dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        initialization();
    }

    public void initialization(){
        //Username -> Bday
        editUsername = findViewById(R.id.edttxt_usrname);
        editPassword = findViewById(R.id.edttxt_pass);
        editConfirmPassword = findViewById(R.id.edttxt_cnfrmpass);
        editEmailAddress = findViewById(R.id.edttxt_email);
        editClickableBday = findViewById(R.id.birthday);

        //Full Name
        editLn = findViewById(R.id.edttxt_lastName);
        editFn = findViewById(R.id.edttxt_firstName);
        editMn = findViewById(R.id.edttxt_middleName);

        //Gender
        rbMale = findViewById(R.id.rd_m);
        rbFemale = findViewById(R.id.rd_f);
        rbOthers = findViewById(R.id.rd_o);

        //Address
        editStreet = findViewById(R.id.edttxt_street);
        editHouseNum = findViewById(R.id.edttxt_housenmbr);
        mySpinners.MySpinner();

        //Contact -> Security Question
        editPhone = findViewById(R.id.edit_text_phone);
        checkHobbies[0] = findViewById(R.id.id_Sleepingcheckbox);
        checkHobbies[1] = findViewById(R.id.id_DancingcheckBox);
        checkHobbies[2] = findViewById(R.id.id_CookingcheckBox);
        checkHobbies[3] = findViewById(R.id.id_SingingcheckBox);
        checkHobbies[4] = findViewById(R.id.id_BakingcheckBox);
        checkHobbies[5] = findViewById(R.id.id_GardeningcheckBox);
        checkHobbies[6] = findViewById(R.id.id_WritingcheckBox);
        checkHobbies[7] = findViewById(R.id.id_ReadingcheckBox);
        checkHobbies[8] = findViewById(R.id.id_WatchingcheckBox);
        checkHobbies[9] = findViewById(R.id.id_PunchingcheckBox);
        editSecurityQ1 = findViewById(R.id.id_SQ1answer);
        editSecurityQ2 = findViewById(R.id.id_SQ2answer);
        editSecurityQ3 = findViewById(R.id.id_SQ3answer);

        //Button
        btnRegister = findViewById(R.id.id_registerButton);

        //listener
        btnRegister.setOnClickListener(this);
        editClickableBday.setOnClickListener(this);
        checkHobbies[0].setOnClickListener(this);
        checkHobbies[1].setOnClickListener(this);
        checkHobbies[2].setOnClickListener(this);
        checkHobbies[3].setOnClickListener(this);
        checkHobbies[4].setOnClickListener(this);
        checkHobbies[5].setOnClickListener(this);
        checkHobbies[6].setOnClickListener(this);
        checkHobbies[7].setOnClickListener(this);
        checkHobbies[8].setOnClickListener(this);
        checkHobbies[9].setOnClickListener(this);
        OnClickRadioButton();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_registerButton:
                Validation();
                break;
            case R.id.birthday:
                datepickerdialog();
                break;
            case R.id.id_Sleepingcheckbox:
            case R.id.id_DancingcheckBox:
            case R.id.id_CookingcheckBox:
            case R.id.id_SingingcheckBox:
            case R.id.id_BakingcheckBox:
            case R.id.id_GardeningcheckBox:
            case R.id.id_WritingcheckBox:
            case R.id.id_ReadingcheckBox:
            case R.id.id_WatchingcheckBox:
            case R.id.id_PunchingcheckBox:
                CheckBox cb1 = (CheckBox) v;
                selected = cb1.getText().toString();
                if(cb1.isChecked()){
                    listHobbies.add(selected);
                }else{
                    listHobbies.remove(selected);
                }
                break;
        }

    }

    public void OnClickRadioButton(){
        final RadioGroup group= (RadioGroup) findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rd_m:
                        gender = rbMale.getText().toString();
                        break;
                    case R.id.rd_f:
                        gender = rbFemale.getText().toString();
                        break;
                    case R.id.rd_o:
                        openDialog();
                        break;
                }
            }
        });
    }

    public void openDialog() {
        DialogOthersGender exampleDialog = new DialogOthersGender();
        exampleDialog.show(getSupportFragmentManager(), "gender others dialog");
    }

    @Override
    public void applyTexts(String username) {
        rbOthers.setText(username);
        gender = rbOthers.getText().toString();
    }

    public void datepickerdialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                editClickableBday.setText(month+1+"/"+dayOfMonth+"/"+year);
            }
        }, year, month,day);
        editClickableBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    }

    public void Validation(){
        //Username -> Bday
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        confirmPassword = editConfirmPassword.getText().toString().trim();
        email = editEmailAddress.getText().toString().trim();
        bday = editClickableBday.getText().toString();
        //Full Name
        lastName = editLn.getText().toString().trim();
        firstName = editFn.getText().toString().trim();
        middleName = editMn.getText().toString().trim();
        fullName = lastName + firstName + middleName;

        //Address
        street = editStreet.getText().toString().trim();
        houseNum = editHouseNum.getText().toString().trim();
        barangay = mySpinners.Barangay();
        municipality = mySpinners.Municipality();
        province = mySpinners.Province();
        address = street + " " + houseNum + " " + barangay + "," + municipality + "," + province;
        //Contact -> Security Question
        contact = editPhone.getText().toString();
        securityQ1 = editSecurityQ1.getText().toString().trim();
        securityQ2 = editSecurityQ2.getText().toString().trim();
        securityQ3 = editSecurityQ3.getText().toString().trim();
        secQuesText1 = mySpinners.SecQ1Method();
        secQuesText2 = mySpinners.SecQ2Method();
        secQuesText3 = mySpinners.SecQ3Method();

        if(!password.equals(confirmPassword)){
            ShowPasswordDialog();
        }else if(IsError()){
            ShowErrorDialog();
            //listHobbies.clear();
        }
        else{
            for (String s : listHobbies)
            {
                stringBuildHobby.append(s).toString();
                stringBuildHobby.append(" ");
            }
            ShowSuccessDialog();
        }
    }// end of ValidationMethod


    boolean IsError(){
        if( username.equals("") || password.equals("") ||
                confirmPassword.equals("") || email.equals("") ||
                bday.equals("") || gender.equals("") || listHobbies.isEmpty() ||
                securityQ1.equals("") || securityQ2.equals("") || securityQ3.equals("") ||
                contact.isEmpty() || lastName.equals("") || firstName.equals("")){
            return true;
        }else{
            return false;
        }
    }

    public void ShowErrorDialog(){
        String user = "Username is Missing!\n";
        String pass = "\nPassword is Missing!\n";
        String confirmPass = "\nConfirmPassword is Missing!\n";
        String emailAdd = "\nEmail Address is Missing!\n";
        String birthday = "\nBirthday is Missing!\n";
        String gendrs = "\nGender is Missing!\n";
        String fn = "\nFirstName is Missing!\n";
        String ln = "\nLastName is Missing!\n";
        String strt = "\nStreet is Missing!\n";
        String houseNumber = "\nHouse Number is Missing!\n";
        String phone = "\nContact is Missing!\n";
        String hby = "\nHobby is Missing!\n";
        String q1 = "\nSecurity Question 1 is Missing!\n";
        String q2 = "\nSecurity Question 2 is Missing!\n";
        String q3 = "\nSecurity Question 3 is Missing!\n";

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Error/s!");
        error.setMessage(   (username.matches("") ? user : user.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (password.matches("") ? pass : pass.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (confirmPassword.matches("") ? confirmPass : confirmPass.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (email.matches("") ? emailAdd : emailAdd.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (bday.matches("") ? birthday : birthday.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (gender.matches("") ? gendrs : gendrs.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (firstName.matches("") ? fn : fn.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (lastName.matches("") ? ln : ln.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (street.matches("") ? strt : strt.replaceAll("[\n]", " ").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (houseNum.matches("") ? houseNumber : houseNumber.replaceAll("[\n]", " ").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (contact.matches("") ? phone : phone.replaceAll("[\n]", " ").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (listHobbies.isEmpty() ? hby : hby.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")).replaceAll("[0-9]","") +
                (securityQ1.matches("") ? q1 : q1.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "").replaceAll("[0-9]","")) +
                (securityQ2.matches("") ? q2 : q2.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "").replaceAll("[0-9]","")) +
                (securityQ3.matches("") ? q3 : q3.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "").replaceAll("[0-9]","")));

        error.setPositiveButton("Okay",null);
        error.show();
    }


    public void ShowSuccessDialog(){
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Success!");
        error.setMessage(  "Username: \n" + username + "\n" +
                "\nPassword: \n" + password + "\n" +
                "\nEmailAddress: \n" + email + "\n" +
                "\nBirthday: \n" + bday + "\n" +
                (fullName.matches("") ? "\nFullName:*\n" : "\nFullName: \n" +(fullName = lastName + " " + firstName + " " + middleName)) + "\n" +
                "\nGender: \n" + gender + "\n" +
                "\nAddress: \n" + address + "\n" +
                "\nContact: \n" + contact + "\n" +
                "\nHobby/s: \n" + stringBuildHobby + "\n" +
                "\n"+secQuesText1+ ": \n" + securityQ1 + "\n" +
                "\n"+secQuesText2+ ": \n" + securityQ2 + "\n" +
                "\n"+secQuesText3+ ": \n" + securityQ3);
        error.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stringBuildHobby.delete(0, stringBuildHobby.length());
                Toast.makeText(getApplicationContext(), "Registration Was Successful!", Toast.LENGTH_SHORT).show();
            }
        });
        error.show();

    }

    public void ShowPasswordDialog(){
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Password Error!");
        error.setMessage("Password and Confirm Password did not match");
        error.setPositiveButton("Okay",null);
        error.show();
    }

}

