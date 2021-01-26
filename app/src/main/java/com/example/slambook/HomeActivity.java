package com.example.slambook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView max;
    //Add the Person objects to an ArrayList
    ArrayList<Person> peopleList = new ArrayList<>();
    PersonListAdapter personListAdapter;
    //Add the Accounts objects to an ArrayList
    ArrayList<String> accountList = new ArrayList<>();
    AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_list);

        max = findViewById(R.id.textView2);

        ListView myListView = (ListView) findViewById(R.id.listView);
        ListView myListView1 = (ListView) findViewById(R.id.listView_username);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        Bundle data = getIntent().getExtras();
        String anna = intent.getStringExtra("anna");
        Person account_anna_1 = (Person) data.getParcelable("anna_1");

        String lorna = intent.getStringExtra("lorna");
        Person account_lorna_1 = (Person) data.getParcelable("lorna_1");
        Person account_lorna_2 = (Person) data.getParcelable("lorna_2");

        String fe = intent.getStringExtra("fe");
        Person account_fe_1 = (Person) data.getParcelable("fe_1");
        Person account_fe_2 = (Person) data.getParcelable("fe_2");
        Person account_fe_3 = (Person) data.getParcelable("fe_3");


        if(username.equals("Anna") && password.equals("123")){
            accountList.add(anna);
            peopleList.add(account_anna_1);
        }else if(username.equals("Lorna") && password.equals("Th3Q41ckBr0wnF0x")){
            accountList.add(lorna);
            peopleList.add(account_lorna_1);
            peopleList.add(account_lorna_2);
        }else if(username.equals("_Fe_") && password.equals("p@zzW0rd")){
            accountList.add(fe);
            peopleList.add(account_fe_1);
            peopleList.add(account_fe_2);
            peopleList.add(account_fe_3);
        }

//        Person account_fe_1 = new Person("Fe Santos", "BestFriend", "March 12 2020",
//                "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars");
//
//                //Add the Person objects to an ArrayList
//        ArrayList<Person> peopleList = new ArrayList<>();
//        //Add the Accounts objects to an ArrayList
//        ArrayList<String> accountList = new ArrayList<>();
//
//
//        accountList.add("happy");
//        peopleList.add(account_fe_1);


        personListAdapter = new PersonListAdapter(this, R.layout.individual_entry_1, peopleList);
        myListView.setAdapter(personListAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToViewEntryAct = new Intent(HomeActivity.this, ViewEntry.class);
                intentToViewEntryAct.putExtra("ViewList", peopleList.get(i));
                startActivity(intentToViewEntryAct);
            }
        });

        personListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
            @Override
            public void OnClickListener(int position) {
                peopleList.remove(position);
                personListAdapter.notifyDataSetChanged();
            }
        });

        accountListAdapter = new AccountListAdapter(this, R.layout.individual_user_2, accountList);
        myListView1.setAdapter(accountListAdapter);

        accountListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
            @Override
            public void OnClickListener(int position) {
                Logout();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("editTextValue", 0);
                String goodAh = Integer.toString(position);
                max.setText(goodAh);
                Log.d("happy", "hahah");
                peopleList.set(position,  new Person(R.drawable.woman, "PutangIna mo", "BestFriend", "March 12 2020",
                        "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars"));
                personListAdapter.notifyDataSetChanged();
//                Person g = new Person(R.drawable.woman, "PutangIna mo", "BestFriend", "March 12 2020",
//                        "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars");
            }
        }
    }

    public void Logout() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Are you sure you want to logout?");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               startActivity(new Intent(HomeActivity.this, Login.class));
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Cancelled so sad!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of Logout CURLY BRACES


}