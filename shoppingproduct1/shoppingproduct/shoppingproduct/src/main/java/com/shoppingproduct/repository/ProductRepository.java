package com.shoppingproduct.repository;

import com.shoppingproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepository extends JpaRepository<Product,Long> {

    public Optional<Product> findById(Long id);

    //List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    List<Product> findByName(String product);

    List<Product> findAll();
    List<Product> findByBrand(String brand);
   // List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product>findByBrandAndName(String brand,String productName);

   // List<Product> findByCategoryNameAndProductName(String category, String product);

    Long countByBrandAndName(String brand, String name);


    @Query("SELECT p FROM Product p WHERE p.category.name = :category AND p.brand = :brand")
    List<Product> findByCategoryNameAndBrand(@Param("category") String category, @Param("brand") String brand);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.name = :product")
    List<Product> findByCategoryNameAndProductName(@Param("category") String category, @Param("product") String product);



}
