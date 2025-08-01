package com.shoppingproduct.repository;

import com.shoppingproduct.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long> {

    UserDetails findByUserId(Long userId);
    boolean existsByEmailId(String email);
}
