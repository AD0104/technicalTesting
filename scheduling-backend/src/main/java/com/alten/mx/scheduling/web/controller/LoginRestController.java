package com.alten.mx.scheduling.web.controller;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alten.mx.scheduling.business.mapping.UsersMapper;
import com.alten.mx.scheduling.business.service.AuthService;
import com.alten.mx.scheduling.persistance.dao.UsersCrudRepository;
import com.alten.mx.scheduling.persistance.dto.GenericResponse;
import com.alten.mx.scheduling.persistance.dto.JwtAuthResponse;
import com.alten.mx.scheduling.persistance.dto.LoginDto;
import com.alten.mx.scheduling.persistance.dto.UsersDto;
import com.alten.mx.scheduling.persistance.entity.Users;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/auth")
public class LoginRestController {

    protected static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    UsersCrudRepository uRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/user/{userName}/registered", method = RequestMethod.GET)
    public ResponseEntity<GenericResponse> getUserNameIsAvailable(@PathVariable("userName") String userName) {
        logger.info("[LoginRestController.getUserNameIsAvailable] Ini Call");
        GenericResponse response = new GenericResponse(200, "Ok");
        if (uRepository.findByEmail(userName).size() > 0) {
            response.setStatus(400);
            response.setMessage("El correo ya se ha registrado anteriormente");
        }
        logger.info("[LoginRestController.getUserNameIsAvailable] Fin Call");
        return new ResponseEntity<GenericResponse>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> postNewUser(@RequestBody UsersDto user) {
        logger.info("[LoginRestController.postNewUser] Ini Call");

        GenericResponse response = new GenericResponse(200, "Ok");
        if (uRepository.findByEmail(user.getEmail()).size() > 0) {
            response.setStatus(400);
            response.setMessage("No se puede registrar al usuario");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users uEntity = usersMapper.toEntity(user);
            uRepository.save(uEntity);
        } catch (Exception e) {
            logger.error("[LoginRestController.postNewUser] Fin Call, Error: {}", e.getMessage());
            response.setStatus(400);
            response.setMessage("No se puede registrar al usuario");
        }

        logger.info("[LoginRestController.postNewUser] Fin Call");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity optionsLogin() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> postLogin(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Control-Allow-Credentials", "true");
        httpHeaders.set("Access-Control-Allow-Origin", "http://localhost:4200/");

        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(jwtAuthResponse);

        //return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}
