package com.example.bancotextil;

import java.time.LocalDateTime;

public class PostData {
    String pubId, userId, titulo, tipo, numero, cantidad, direccion, desc, nombre;
    LocalDateTime fecha;

    public PostData() {
    }

    public PostData(String pubId, String userId, String titulo, String tipo, String numero, String cantidad, String direccion, String desc, String nombre, LocalDateTime fecha) {
        this.pubId = pubId;
        this.userId = userId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.numero = numero;
        this.cantidad = cantidad;
        this.direccion = direccion;
        this.desc = desc;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
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

    public LocalDateTime getFecha() { return fecha; }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
