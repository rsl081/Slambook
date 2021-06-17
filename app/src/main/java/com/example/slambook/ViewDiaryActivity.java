package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewDiaryActivity extends AppCompatActivity {

    DiaryAccountDb accountDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);
        this.accountDb = new DiaryAccountDb(this);
        Init();
    }

    private void Init ()
    {
        Bundle bundle = this.getIntent().getExtras();

        Diary diary = bundle.getParcelable("ViewDiary");
        ImageView profilePicImageView = findViewById(R.id.profile_pic_diary);
        TextView txtSubject = findViewById(R.id.view_subject);
        TextView txtDate = findViewById(R.id.view_date_diary);
        TextView txtTime = findViewById(R.id.view_time);
        TextView txtDiary = findViewById(R.id.view_diary);

        byte[] byteImg = diary.getbyteDiaryPic();
        Bitmap bitmapImages = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
        profilePicImageView.setImageBitmap(bitmapImages);
        txtSubject.setText(diary.getSubject());
        txtDate.setText(diary.getDate());
        txtTime.setText(diary.getTime());
        txtDiary.setText(diary.getMessage());

        Button btnUpdate = findViewById(R.id.btn_go_back_todiarylist);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}