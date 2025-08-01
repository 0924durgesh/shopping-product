package com.shoppingproduct.repository;

import com.shoppingproduct.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    User findByEmail(@Param("email") String email); // email unique hona chahiye

    @Query("select name from Category  c where c.name= :name")
    Category findByName(@Param("name") String name);
    boolean existsByName(String name);
}
