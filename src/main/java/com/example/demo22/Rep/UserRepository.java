package com.example.demo22.Rep;

import com.example.demo22.Model.Accounts;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<Accounts, Integer> { // Updated to 'Accounts'
}
