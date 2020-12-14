package com.example.ne.clases;

public class listElementPermiso {
    private String codigo;
    private String nombre;
    private String estado;
    private String color;

    public listElementPermiso(String codigo, String nombre, String estado, String color) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
        this.color = color;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
