package com.shoppingproduct.repository;

import com.shoppingproduct.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image getImageById(Long id);
}
