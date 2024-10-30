package com.alten.mx.scheduling.persistance.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alten.mx.scheduling.persistance.entity.Activity;
import com.alten.mx.scheduling.persistance.entity.Users;

public interface ActivityCrudRepository extends CrudRepository<Activity, Integer> {
    List<Activity> findByUser (Users user);
    Optional<Activity> findByUserAndId(Users user, Integer id);
}
