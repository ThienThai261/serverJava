package com.example.demo22.Rep;

import com.example.demo22.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Chúng ta có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}
