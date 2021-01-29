package com.example.slambook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager myLayoutManager;

    private RecyclerView recyclerViewAccount;
    private RecyclerView.LayoutManager myLayoutManagerAccount;

    public static final String POSITION = "com.example.slambook.POSITION";
    public static final String PEOPLE_LIST = "com.example.slambook.PEOPLE_LIST";
    //Add the Person objects to an ArrayList
    ArrayList<Person> peopleList = new ArrayList<>();

    PersonListAdapter personListAdapter;
    //Add the Accounts objects to an ArrayList
    ArrayList<Accounts> accountList = new ArrayList<>();

    AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_list);

        Init();
    }

    private void Init(){
        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        if(intent != null || data != null){

            Accounts new_regist_user = intent.getParcelableExtra("new_regist_user");
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");

            Accounts anna = intent.getParcelableExtra("anna");
            Person account_anna_1 = (Person) data.getParcelable("anna_1");

            Accounts lorna = intent.getParcelableExtra("lorna");
            Person account_lorna_1 = (Person) data.getParcelable("lorna_1");
            Person account_lorna_2 = (Person) data.getParcelable("lorna_2");

            Accounts fe = intent.getParcelableExtra("fe");
            Person account_fe_1 = (Person) data.getParcelable("fe_1");
            Person account_fe_2 = (Person) data.getParcelable("fe_2");
            Person account_fe_3 = (Person) data.getParcelable("fe_3");

            if(new_regist_user != null){
                accountList.add(new_regist_user);
            }else {
                if (username.equals("Anna") && password.equals("123")) {
                    accountList.add(anna);
                    peopleList.add(account_anna_1);
                } else if (username.equals("Lorna") && password.equals("Th3Q41ckBr0wnF0x")) {
                    accountList.add(lorna);
                    peopleList.add(account_lorna_1);
                    peopleList.add(account_lorna_2);
                } else if (username.equals("_Fe_") && password.equals("p@zzW0rd")) {
                    accountList.add(fe);
                    peopleList.add(account_fe_1);
                    peopleList.add(account_fe_2);
                    peopleList.add(account_fe_3);
                }
            }

            recyclerView = findViewById(R.id.recycleView);
            recyclerView.setHasFixedSize(true);
            myLayoutManager = new LinearLayoutManager(this);
            personListAdapter = new PersonListAdapter(this, peopleList);
            recyclerView.setLayoutManager(myLayoutManager);
            recyclerView.setAdapter(personListAdapter);
            personListAdapter.setOnItemClickListener(new PersonListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intentToViewEntryAct = new Intent(HomeActivity.this, ViewEntry.class);
                        intentToViewEntryAct.putExtra("ViewList", peopleList.get(position));
                        startActivity(intentToViewEntryAct);
                }
            });

            personListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    DeleteList(position);
                }
            });

            personListAdapter.setOnClickListener2(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    Intent intentToEditEntryAct = new Intent(HomeActivity.this, EditEntry.class);
                    intentToEditEntryAct.putExtra(POSITION, position);
                    intentToEditEntryAct.putExtra(PEOPLE_LIST, peopleList.get(position));
                    startActivityForResult(intentToEditEntryAct, 1);
                }
            });

            recyclerViewAccount = findViewById(R.id.recycleView_account);
            recyclerViewAccount.setHasFixedSize(true);
            myLayoutManagerAccount = new LinearLayoutManager(this);
            accountListAdapter = new AccountListAdapter(this, accountList);
            recyclerViewAccount.setLayoutManager(myLayoutManagerAccount);
            recyclerViewAccount.setAdapter(accountListAdapter);
            accountListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    Logout();
                }
            });
        }

        Button addEntry = findViewById(R.id.btn_add_new_entry);

        addEntry.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("update_list", 0);
                //Bitmap newProfilePic = (Bitmap) data.getParcelableExtra("profile_pic");
                Person edit_person = data.getParcelableExtra("edit_person");

                peopleList.set(position, edit_person);
                personListAdapter.notifyDataSetChanged();
                //data.removeExtra(String.valueOf(edit_person));
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                Person person = data.getParcelableExtra("new_person");
                peopleList.add(0,person);
                personListAdapter.notifyDataSetChanged();
                //data.removeExtra(String.valueOf(person));
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


    public void DeleteList(int position) {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Are you sure you want to delete?");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                peopleList.remove(position);
                personListAdapter.notifyDataSetChanged();
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Cancel!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of Logout CURLY BRACES



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_new_entry:
                AddNewEntry();
            break;
        }
    }

    public void AddNewEntry(){
        Intent addEntryIntent = new Intent(HomeActivity.this, AddEntry.class);
        startActivityForResult(addEntryIntent, 2);
    }
}