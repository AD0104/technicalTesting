package com.alten.mx.scheduling.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.mx.scheduling.persistance.entity.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/activity")
public class ActivityControl {

    private static final Logger logger = LoggerFactory.getLogger(ActivityControl.class);

    @PostMapping("/register")
    public String postRegisterActivity(@RequestBody Activity activity) {
        logger.info("[ActivityControl.postRegisterActivity] Ini Call");

        logger.info("[ActivityControl.postRegisterActivity] Fin Call");
        return "Inside";
    }

}
