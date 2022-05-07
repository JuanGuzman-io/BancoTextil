package com.example.bancotextil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataRVAdapter extends RecyclerView.Adapter<DataRVAdapter.ViewHolder> {
    private ArrayList<DataRVModal> dataRVModalArrayList;
    private Context context;
    private DataClickInterface dataClickInterface;
    int lastPos = -1;

    public DataRVAdapter(ArrayList<DataRVModal> dataRVModalArrayList, Context context, DataClickInterface dataClickInterface) {
        this.dataRVModalArrayList = dataRVModalArrayList;
        this.context = context;
        this.dataClickInterface = dataClickInterface;
    }

    @NonNull
    @Override
    public DataRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataRVModal dataRVModal = dataRVModalArrayList.get(position);
        holder.nameTV.setText(dataRVModal.getNombre());
        holder.titleTV.setText(dataRVModal.getTitulo());
        //holder.dateTV.setText(dataRVModal.getFecha().toString());
        holder.tipoTV.setText(dataRVModal.getTipo());
        holder.cantidadTV.setText(dataRVModal.getCantidad());
        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataClickInterface.onDataClick(position);
            }
        });
    }

    private void setAnimation(View v, int position) {
        if (position > lastPos ){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            v.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return dataRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV,  titleTV, tipoTV, cantidadTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameIV);
            titleTV = itemView.findViewById(R.id.titleIV);
            //dateTV = itemView.findViewById(R.id.dateIV);
            tipoTV = itemView.findViewById(R.id.tipoIV);
            cantidadTV = itemView.findViewById(R.id.cantidadIV);
        }
    }

    public interface DataClickInterface {
        void onDataClick(int position);
    }
}
