package com.shoppingproduct.service.product;

import com.shoppingproduct.AddRequest.AddRequestProduct;
import com.shoppingproduct.AddRequest.ProductUpdateRequest;
import com.shoppingproduct.model.Product;

import java.util.List;

public interface IProductService {

    //1 end point
   Product  addProduct(AddRequestProduct product);
 //2 end point

 List<Product> getAllProducts();
    //3 end point
   Product getProductById(Long id);
    //4 end point
   void deleteProductById(Long id);
    //5 end point
    Product updateProduct(ProductUpdateRequest product, Long id);


    //6 end point
    List<Product> getProductsByCategory(String category);
    //7 end point

    List<Product> getProductsByBrand(String brand);
    //8 end point

    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    //9 end point

    List<Product> getProductsByName(String name);
    //10 end point
    List<Product> getProductsByCategoryAndName(String category,String name);
    //11 end point
    List<Product> getProductsByBrandAndName(String brand,String productName);
    //12 end point
    Long countProductsByBrandAndName(String brand,String name);
}
