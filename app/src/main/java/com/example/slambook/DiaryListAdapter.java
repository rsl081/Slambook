package com.example.slambook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder>
{
    private ArrayList<Diary> diary;
    private Context mContext;
    private DiaryListAdapter.OnClickListener listener1;
    private DiaryListAdapter.OnClickListener listener2;


    private DiaryListAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(DiaryListAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    /**
     * Holds variables in a View
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicDiary;
        TextView date;
        TextView time;
        Button deleteBtn;
        Button editBtn;

        public ViewHolder(@NonNull View itemView, final DiaryListAdapter.OnItemClickListener listener) {
            super(itemView);
            this.profilePicDiary = (ImageView) itemView.findViewById(R.id.profile_diary);
            this.date = (TextView) itemView.findViewById(R.id.diary_date_layout);
            this.time = (TextView) itemView.findViewById(R.id.diary_time_layout);
            this.deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn_diary);
            this.editBtn = (Button) itemView.findViewById(R.id.editBtn_diary);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            this.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener1 != null){
                        listener1.OnClickListener(position);
                    }
                }
            });

            this.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener2 != null){
                        if (position != RecyclerView.NO_POSITION) {
                            listener2.OnClickListener(position);
                        }
                    }
                }
            });

        }
    }

    public DiaryListAdapter(Context context, ArrayList<Diary> objects) {
        this.mContext = context;
        this.diary = objects;
    }

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_individual_entry, parent, false);
        DiaryListAdapter.ViewHolder vh = new DiaryListAdapter.ViewHolder(v, onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        Diary diary = this.diary.get(position);
        if(diary != null) {
            byte[] images = diary.getbyteDiaryPic();
            Bitmap bitmapImages = BitmapFactory.decodeByteArray(images, 0, images.length);
            holder.profilePicDiary.setImageBitmap(bitmapImages);
            holder.date.setText(diary.getDate());
            holder.time.setText(diary.getTime());
        }
    }// end fo curly braces onBindViewHolder

    @Override
    public int getItemCount() {
        return this.diary.size();
    }


    public interface OnClickListener {
        void OnClickListener(int position);
    }

    public void setOnClickListener(DiaryListAdapter.OnClickListener listener1) {
        this.listener1 = listener1;
    }
    public void setOnClickListener2(DiaryListAdapter.OnClickListener listener2) {
        this.listener2 = listener2;
    }
}
