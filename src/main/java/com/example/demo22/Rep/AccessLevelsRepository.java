package com.example.demo22.Rep;

import com.example.demo22.Model.AccessLevels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccessLevelsRepository extends JpaRepository<AccessLevels, Integer> {
    Optional<AccessLevels> findByIdAccount(int idAccount);
}

