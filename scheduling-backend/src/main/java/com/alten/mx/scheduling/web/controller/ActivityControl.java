package com.alten.mx.scheduling.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alten.mx.scheduling.business.service.ActivityService;
import com.alten.mx.scheduling.persistance.dao.ActivityCrudRepository;
import com.alten.mx.scheduling.persistance.dao.UsersCrudRepository;
import com.alten.mx.scheduling.persistance.dto.ActivityDto;
import com.alten.mx.scheduling.persistance.dto.GenericResponse;
import com.alten.mx.scheduling.persistance.entity.Activity;
import com.alten.mx.scheduling.persistance.entity.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/activity")
public class ActivityControl {

    private static final Logger logger = LoggerFactory.getLogger(ActivityControl.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityCrudRepository actvCrudRepository;

    @Autowired
    private UsersCrudRepository uCrudRepository;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> postRegisterActivity(@RequestBody ActivityDto activity, Principal principal) {
        logger.info("[ActivityControl.postRegisterActivity] Ini Call");

        GenericResponse response = this.activityService.processActivityToSave(activity, principal.getName());

        logger.info("[ActivityControl.postRegisterActivity] Fin Call");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Activity>> getListTasksByUser(Principal principal) {
        logger.info("[ActivityControl.getListTasksByUser] Ini Call");
        Optional<Users> user = this.uCrudRepository.findFirstByEmail(principal.getName());
        if (!user.isPresent())
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        List<Activity> activities = this.actvCrudRepository.findByUser(user.get());
        logger.info("[AcitivitControl.getListTasksByUser] Fin Call");
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Activity> getTaskById(@PathVariable("id") Integer id, Principal principal) {
        logger.info("[ActivityControl.getTaskById] Ini Call");
        Optional<Users> user = this.uCrudRepository.findFirstByEmail(principal.getName());
        if (!user.isPresent())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        Optional<Activity> activity = this.actvCrudRepository.findByUserAndId(user.get(), id);
        if(!activity.isPresent())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        
        logger.info("[AcitivitControl.getTaskById] Fin Call");
        return new ResponseEntity<>(activity.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteActivityById(@PathVariable("id") Integer id) {
        logger.info("[ActivityControl.deleteActivityById] Ini Call");

        try {
            this.actvCrudRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error mientras se intentaba borrar la actividad con ID: {}, Error: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
     
        logger.info("[ActivityControl.deleteActivityById] Fin Call");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<GenericResponse> updateActivityById(@PathVariable("id") Integer id, @RequestBody ActivityDto activityDto) {
        logger.info("[ActivityControl.updateActivityById] Ini Call");

        GenericResponse response = this.activityService.doActivityUpdate(id, activityDto);

        logger.info("[ActivityControl.updateActivityById] Fin Call");
        return new ResponseEntity<GenericResponse>(response, HttpStatusCode.valueOf(response.getStatus()) );
    }

}
