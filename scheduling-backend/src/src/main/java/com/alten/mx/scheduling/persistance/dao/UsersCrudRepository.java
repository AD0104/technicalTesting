package com.alten.mx.scheduling.persistance.dao;

import org.springframework.data.repository.CrudRepository;

import com.alten.mx.scheduling.persistance.entity.Users;
import java.util.List;

public interface UsersCrudRepository extends CrudRepository<Users, Integer>{

    List<Users> findByEmail(String email);
}
