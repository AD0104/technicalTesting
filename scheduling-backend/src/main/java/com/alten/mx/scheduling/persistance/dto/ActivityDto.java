package com.alten.mx.scheduling.persistance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDto {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime horaIni;
    private LocalTime horaFin;
    private String comentarios;
    private Integer estatus;
    
}
