package com.alten.mx.scheduling.persistance.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.alten.mx.scheduling.persistance.dto.enums.EEstatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_scheduling_activity")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "actv_id")
    public Integer id;
    @Column(name = "actv_name")
    public String nombre;
    @Column(name = "actv_date")
    public LocalDateTime fecha;
    @Column(name = "actv_time_start")
    public LocalTime hora_ini;
    @Column(name = "actv_time_end")
    public LocalTime hora_fin;
    @Column(name = "actv_comments")
    public String comentarios;
    @Column(name = "actv_status")
    public EEstatus estatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usr_id", nullable = false)
    private Users user;
}
