package com.example.bancotextil;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataRVModal implements Parcelable {
    private String pubid, userId, titulo, tipo, numero, cantidad, direccion, desc, nombre;
    private Boolean estado;
    //private LocalDateTime fecha;

    public DataRVModal() {
    }

    public DataRVModal(String pubid, String userId, String titulo, String tipo, String numero, String cantidad, String direccion, String desc, String nombre, LocalDateTime fecha, Boolean estado) {
        this.pubid = pubid;
        this.userId = userId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.numero = numero;
        this.cantidad = cantidad;
        this.direccion = direccion;
        this.desc = desc;
        this.nombre = nombre;
        this.estado = estado;
        //this.fecha = fecha;
    }

    protected DataRVModal(Parcel in) {
        pubid = in.readString();
        userId = in.readString();
        titulo = in.readString();
        tipo = in.readString();
        numero = in.readString();
        cantidad = in.readString();
        direccion = in.readString();
        desc = in.readString();
        nombre = in.readString();
        estado = in.readBoolean();
    }

    public static final Creator<DataRVModal> CREATOR = new Creator<DataRVModal>() {
        @Override
        public DataRVModal createFromParcel(Parcel in) {
            return new DataRVModal(in);
        }

        @Override
        public DataRVModal[] newArray(int size) {
            return new DataRVModal[size];
        }
    };

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pubid);
        parcel.writeString(userId);
        parcel.writeString(titulo);
        parcel.writeString(tipo);
        parcel.writeString(numero);
        parcel.writeString(cantidad);
        parcel.writeString(direccion);
        parcel.writeString(desc);
        parcel.writeString(nombre);
        parcel.writeBoolean(estado);
    }
}
