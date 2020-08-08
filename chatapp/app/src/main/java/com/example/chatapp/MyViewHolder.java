package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewques;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewques=itemView.findViewById(R.id.textViewques);

    }
}
