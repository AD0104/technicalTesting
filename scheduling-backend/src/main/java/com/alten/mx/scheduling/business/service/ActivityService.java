package com.alten.mx.scheduling.business.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.mx.scheduling.business.mapping.ActivityMapper;
import com.alten.mx.scheduling.persistance.dao.ActivityCrudRepository;
import com.alten.mx.scheduling.persistance.dao.UsersCrudRepository;
import com.alten.mx.scheduling.persistance.dto.ActivityDto;
import com.alten.mx.scheduling.persistance.dto.GenericResponse;
import com.alten.mx.scheduling.persistance.dto.enums.EEstatus;
import com.alten.mx.scheduling.persistance.dto.exceptions.NoSuchUserException;
import com.alten.mx.scheduling.persistance.entity.Activity;
import com.alten.mx.scheduling.persistance.entity.Users;

@Service
public class ActivityService {

    @Autowired
    private ActivityCrudRepository activityCrudRepository;

    @Autowired
    private UsersCrudRepository uCrudRepository;

    @Autowired
    private ActivityMapper activityMapper;

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    public GenericResponse processActivityToSave(ActivityDto activityDto, String userName) {
        logger.info("ActivityDto: {}", activityDto);
        if (activityDto.getNombre().isEmpty())
            return new GenericResponse(400, "El campo 'nombre' no puede estar vacio");

        if (activityDto.getFecha() == null)
            return new GenericResponse(400, "El campo 'fecha' no puede estar vacio");

        if (activityDto.getHoraIni() == null)
            return new GenericResponse(400, "El campo 'horaIni' no puede estar vacio");

        if (activityDto.getEstatus() == null) {
            activityDto.setEstatus(1);
        }

        Activity activity = activityMapper.toEntity(activityDto);

        try {

            Optional<Users> optUser = this.uCrudRepository.findFirstByEmail(userName);
            if (!optUser.isPresent())
                return new GenericResponse(400, "Usuario no encontrado");

            activity.setUser(optUser.get());
            activityCrudRepository.save(activity);
        } catch (Exception e) {
            logger.error("Ocurrio un error mientras se intentaba guardar la actividad: {}, Error: {}", activity, e.getMessage());
        }

        return new GenericResponse(200, "Ok");
    }

    public GenericResponse doActivityUpdate(Integer id, ActivityDto activityDto) {
        Optional<Activity> optActv = this.activityCrudRepository.findById(id);    
        if (!optActv.isPresent())
            return new GenericResponse(400, "No existe una actividad con el id ingresado");

        if (!optActv.get().getNombre().equals(activityDto.getNombre()) ) {
            optActv.get().setNombre( activityDto.getNombre() );
        }

        if (optActv.get().getFecha() != activityDto.getFecha() ) {
            optActv.get().setFecha( activityDto.getFecha() );
        }

        if (optActv.get().getHoraIni() != activityDto.getHoraIni() ) {
            optActv.get().setHoraIni( activityDto.getHoraIni() );
        }

        if (optActv.get().getHoraFin() != activityDto.getHoraFin() ) {
            optActv.get().setHoraFin( activityDto.getHoraFin() );
        }

        if (!optActv.get().getComentarios().equals(activityDto.getComentarios()) ) {
            optActv.get().setComentarios( activityDto.getComentarios() );
        }

        optActv.get().setEstatus(EEstatus.findByCode(activityDto.getEstatus()));

        try {
            this.activityCrudRepository.save(optActv.get());
        } catch (Exception e) {
            logger.error("Ocurrio un error al actualizar la actividad con ID: {}, Error: {}", id, e.getMessage());
            return new GenericResponse(500, "Error al actualizar la actividad");
        }
        return new GenericResponse(200, "Ok");
    }
}
