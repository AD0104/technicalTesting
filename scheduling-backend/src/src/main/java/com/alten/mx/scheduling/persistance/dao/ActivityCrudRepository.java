package com.alten.mx.scheduling.persistance.dao;

import org.springframework.data.repository.CrudRepository;

import com.alten.mx.scheduling.persistance.entity.Activity;

public interface ActivityCrudRepository extends CrudRepository<Activity, Integer> {}
