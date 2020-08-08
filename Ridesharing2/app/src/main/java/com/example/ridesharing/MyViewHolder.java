package com.example.ridesharing;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvfrom,tvto,tvdate;
    View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvdate=itemView.findViewById(R.id.tvdate);
        tvfrom=itemView.findViewById(R.id.tvfrom);
        tvto=itemView.findViewById(R.id.tvto);
        view=itemView;
    }
}
