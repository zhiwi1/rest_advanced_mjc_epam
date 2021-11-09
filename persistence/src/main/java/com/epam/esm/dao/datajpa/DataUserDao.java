package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DataUserDao extends JpaRepository<User, Long> {
   Optional<User> findByName(String name);
}