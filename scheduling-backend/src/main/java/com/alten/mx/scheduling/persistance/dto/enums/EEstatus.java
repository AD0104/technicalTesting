package com.alten.mx.scheduling.persistance.dto.enums;

import java.util.NoSuchElementException;

public enum EEstatus {
    ESTATUS_PENDIENTE (1, "Pendiente"),
    ESTATUS_PROCESO (2, "Iniciado"),
    ESTATUS_FINAL (3, "Finalizado");

    public final int code;
    public final String descripcion;

    EEstatus(int code, String descripcion) {
        this.code = code;
        this.descripcion = descripcion;
    }

    public int getCode() { return this.code; }
    public String getDescripcion() { return this.descripcion; }

    public static EEstatus findByCode(int code) throws NoSuchElementException {
        for( EEstatus item : values() ){
            if (item.code == code) 
                return item;
        }
        return null;
    }
}
