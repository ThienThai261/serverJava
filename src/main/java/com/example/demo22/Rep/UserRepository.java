package com.example.demo22.Rep;

import com.example.demo22.Model.Accounts;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<Accounts, Integer> {
    Optional<Accounts> findByUsername(String username); // Updated to 'Accounts'
}
