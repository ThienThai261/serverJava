package com.example.demo22.Rep;

import com.example.demo22.Model.Images;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Images, Integer> {
    @Query("SELECT i FROM Images i WHERE i.idProduct = :productId")
    List<Images> findByIdProduct(@Param("productId") String productId);
}