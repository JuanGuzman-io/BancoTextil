package com.example.bancotextil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataRVModal dataRVModal = dataRVModalArrayList.get(position);
        holder.nameTV.setText(dataRVModal.getNombre());
        holder.titleTV.setText(dataRVModal.getTitulo());
        //holder.dateTV.setText(dataRVModal.getFecha().toLocalDate().toString());
        holder.tipoTV.setText(dataRVModal.getTipo());
        holder.cantidadTV.setText(dataRVModal.getCantidad() + " kg");
        if(!dataRVModal.getEstado()) {
            holder.chipEstado.setText("Cerrado");
            holder.chipEstado.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.peach)));
        } else {
            holder.chipEstado.setText("Disponible");
            holder.chipEstado.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal_700)));
        }

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTV;
        //private TextView dateTV;
        private final TextView titleTV;
        private final TextView tipoTV;
        private final TextView cantidadTV;
        private final Chip chipEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameIV);
            titleTV = itemView.findViewById(R.id.titleIV);
            //dateTV = itemView.findViewById(R.id.dateIV);
            tipoTV = itemView.findViewById(R.id.tipoIV);
            cantidadTV = itemView.findViewById(R.id.cantidadIV);
            chipEstado = itemView.findViewById(R.id.chipEstado);
        }
    }

    public interface DataClickInterface {
        void onDataClick(int position);
    }
}
