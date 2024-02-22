package com.example.model;

import java.io.Serializable;

public class ESBExcepcionType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;

    private String descripcion;

    public ESBExcepcionType() {
    }

    public ESBExcepcionType(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String value) {
        this.codigo = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }
}
