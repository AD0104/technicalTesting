package com.alten.mx.scheduling.persistance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDto {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
