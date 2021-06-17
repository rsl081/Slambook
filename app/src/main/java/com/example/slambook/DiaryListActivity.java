package com.example.slambook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DiaryListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewDiary;
    private RecyclerView.LayoutManager myLayoutManager;

    private RecyclerView recyclerViewAccount;
    private RecyclerView.LayoutManager myLayoutManagerAccount;

    public static final String POSITION = "com.example.slambook.POSITION";
    public static final String DIARY_LIST = "com.example.slambook.DIARY_LIST";
    //Add the Person objects to an ArrayList
    ArrayList<Diary> diaryList = new ArrayList<>();

    DiaryListAdapter diaryListAdapter;
    //Add the Accounts objects to an ArrayList
    ArrayList<Accounts> accountList = new ArrayList<>();

    AccountListAdapter accountListAdapter;
    public static final int REQUEST_CODE_ADD_COMPANY = 40;
    public static final String EXTRA_ADDED_ACCOUNT = "extra_key_added_account";
    public static final String EXTRA_ADDED_DIARY = "extra_key_added_diary";

    //Database
    private AccountDb accountDb;
    private DiaryAccountDb diaryAccountDb;
    long createdDiary;
    long sendRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);

        this.accountDb = new AccountDb(this);
        this.diaryAccountDb = new DiaryAccountDb(this);
        Init();
    }

    private void Init(){
        Intent intent = getIntent();

        if(intent != null){
            Accounts createdAccount = intent.getParcelableExtra(EXTRA_ADDED_ACCOUNT);
            createdDiary = intent.getLongExtra(EXTRA_ADDED_DIARY,0);

            if(intent.hasExtra("RegisteredUser")){
                String registeredUsername = intent.getStringExtra("RegisteredUser");
                sendRegister = accountDb.Sender(registeredUsername);
            }

            if(diaryList != null)
            {
                diaryList = (ArrayList<Diary>) diaryAccountDb.getAllDiary(createdDiary);
            }
            accountList.add(createdAccount);

            //diary_recycleView
            recyclerViewDiary = findViewById(R.id.diary_recycleView);
            recyclerViewDiary.setHasFixedSize(true);
            myLayoutManager = new LinearLayoutManager(this);
            diaryListAdapter = new DiaryListAdapter(this, diaryList);
            recyclerViewDiary.setLayoutManager(myLayoutManager);
            recyclerViewDiary.setAdapter(diaryListAdapter);
            diaryListAdapter.setOnItemClickListener(new DiaryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String diarySubject = diaryList.get(position).getSubject();
                    long getDiaryId = diaryAccountDb.SenderDiary(diarySubject);

                    Intent intentToViewEntryAct = new Intent(DiaryListActivity.this, ViewDiaryActivity.class);
                    intentToViewEntryAct.putExtra("ViewPerson", getDiaryId);
                    intentToViewEntryAct.putExtra("ViewDiary", diaryList.get(position));
                    startActivity(intentToViewEntryAct);
                }
            });

            diaryListAdapter.setOnClickListener(new DiaryListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    DeleteList(position);
                }
            });

            diaryListAdapter.setOnClickListener2(new DiaryListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    String diarySubject = diaryList.get(position).getSubject();
                    long getDiaryId = diaryAccountDb.SenderDiary(diarySubject);
                    Intent intentToEditEntryAct = new Intent(DiaryListActivity.this, EditDiaryActivity.class);
                    intentToEditEntryAct.putExtra(POSITION, position);
                    intentToEditEntryAct.putExtra("diary_id", getDiaryId);
                    intentToEditEntryAct.putExtra(DIARY_LIST, diaryList.get(position));
                    startActivityForResult(intentToEditEntryAct, 1);
                }
            });

            recyclerViewAccount = findViewById(R.id.diary_recycleView_account);
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

        Button addEntry = findViewById(R.id.btn_add_new_diary);

        addEntry.setOnClickListener(this);
    }// end of Curly braces INIT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("update_list", 0);
                Diary edit_diary = data.getParcelableExtra("edit_diary");

                diaryList.set(position, edit_diary);
                diaryListAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                if(data.getExtras() != null){
                    Diary diary = data.getParcelableExtra("new_diary");
                    diaryList.add(0,diary);
                    diaryListAdapter.notifyDataSetChanged();
                }
            }
        }//end of if requestCode 2
    }

    public void Logout()
    {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Are you sure you want to logout?");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(DiaryListActivity.this, DiaryLoginActivity.class));
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DiaryListActivity.this, "Cancelled so sad!", Toast.LENGTH_LONG).show();
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
                String diarySubject = diaryList.get(position).getSubject();
                long getPersonId = diaryAccountDb.SenderDiary(diarySubject);
                diaryAccountDb.DeleteDiary(getPersonId);
                diaryList.remove(position);
                diaryListAdapter.notifyDataSetChanged();
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DiaryListActivity.this, "Cancel!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of Logout CURLY BRACES

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_new_diary:
                AddNewDiary();
                break;
        }
    }

    public void AddNewDiary(){
        Intent addEntryIntent = new Intent(DiaryListActivity.this, AddDiaryActivity.class);
        if(sendRegister == 0){
            addEntryIntent.putExtra("add_diary", createdDiary);
        }else{
            addEntryIntent.putExtra("add_diary", sendRegister);
        }

        startActivityForResult(addEntryIntent, 2);
    }

}