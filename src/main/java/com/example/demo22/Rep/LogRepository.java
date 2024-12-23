package com.example.demo22.Rep;

import com.example.demo22.Model.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {
}
