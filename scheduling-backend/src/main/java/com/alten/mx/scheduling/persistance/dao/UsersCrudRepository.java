package com.alten.mx.scheduling.persistance.dao;

import org.springframework.data.repository.CrudRepository;

import com.alten.mx.scheduling.persistance.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UsersCrudRepository extends CrudRepository<Users, Integer>{

    List<Users> findByEmail(String email);
    Optional<Users> findFirstByEmail(String email);
}
