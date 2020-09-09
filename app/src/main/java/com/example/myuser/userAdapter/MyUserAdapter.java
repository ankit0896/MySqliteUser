package com.example.myuser.userAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myuser.R;
import com.example.myuser.model.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyViewHolder>{

    Context context;
    List<User> modelList;
    OnItemClickListner onItemClickListner;

    public MyUserAdapter(Context context, List<User> modelList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.modelList = modelList;
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public MyUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_user_layout, parent, false),onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserAdapter.MyViewHolder holder, int position) {
        holder.name.setText(modelList.get(position).getName());
        holder.email.setText(modelList.get(position).getEmail());
        holder.location.setText(modelList.get(position).getLocation());
        Bitmap bitmap = BitmapFactory.decodeByteArray(modelList.get(position).getImage(), 0, modelList.get(position).getImage().length);
        holder.imageView.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,delete;
        TextView name,email,location;
        public MyViewHolder(@NonNull View itemView, OnItemClickListner onItemClickListner) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            email = itemView.findViewById(R.id.tv_email);
            location = itemView.findViewById(R.id.tv_location);
            imageView = itemView.findViewById(R.id.image_view);
            delete = itemView.findViewById(R.id.iv_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUserAdapter.this.onItemClickListner.onDeleteClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onDeleteClick(int position);

    }
}
