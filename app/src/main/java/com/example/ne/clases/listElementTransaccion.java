package com.example.ne.clases;

public class listElementTransaccion {
    private String codigo;
    private String permiso;
    private String trabajador;
    private String horas;
    private String estado;
    private String color;

    public listElementTransaccion(String codigo, String permiso, String trabajador, String horas, String estado, String color) {
        this.codigo = codigo;
        this.permiso = permiso;
        this.trabajador = trabajador;
        this.horas = horas;
        this.estado = estado;
        this.color = color;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
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
