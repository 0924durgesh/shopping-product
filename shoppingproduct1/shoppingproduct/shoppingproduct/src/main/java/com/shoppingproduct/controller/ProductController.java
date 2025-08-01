package com.shoppingproduct.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.shoppingproduct.AddRequest.AddRequestProduct;
import com.shoppingproduct.AddRequest.ProductUpdateRequest;
import com.shoppingproduct.exceptions.ResourceNotFoundException;

import com.shoppingproduct.model.Product;
import com.shoppingproduct.response.ApiResponse;

import com.shoppingproduct.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
//localhost:8080/api-prefix  //  controller work start time:1:40 //2:18
public class ProductController {

    @Autowired
    private ProductService productService;

    //1 end point
    //   @GetMapping("/allProducts")
//    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse> getAllProduct() {
//        List<Product> products = productService.getAllProducts();
//        return ResponseEntity.ok().body(new ApiResponse("product success", products));
//    }

    //localhost:9080/product/products
    //@GetMapping(value = "/student/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productService.getAllProducts();
     return ResponseEntity.ok(products);
}

    //2 end point
    @GetMapping("/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId)
    {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse<>("success", product));
        }
     catch(ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error:",null));
        }
    }
    //3 end point
    @GetMapping("/{productId}/productName")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable Long productName)
    {
        try {
            Product product = productService.getProductById(productName);
            return ResponseEntity.ok(new ApiResponse<>("success", product));
        }
        catch(ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error:",null));
        }
    }


    //4 end point
//    @PostMapping(value="/addProduct" , produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddRequestProduct product)
//    {
//        try {
//            productService.addProduct(product);
//            return ResponseEntity.ok().body(new ApiResponse("add product success", product));
//        }
//         catch(ResourceNotFoundException e)
//        {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error:",null));
//        }
//    }

// @PostMapping(value="/addProduct" , produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/addProduct")  //time:2:25
    //public ResponseEntity<ApiResponse> addProduct(@RequestBody AddRequestProduct product)
    public ResponseEntity<Product> addProduct(@RequestBody AddRequestProduct product)
    {
        try {
            Product product1=productService.addProduct(product);
            //ApiResponse<?> response = new ApiResponse<>("Product added", product1);
           // return ResponseEntity.ok(response);
            return ResponseEntity.ok(product1);
        }catch(Exception e)
        {
        // return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //5 end point
    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest requestProduct, @PathVariable Long productId)
    {   try {
        Product product = productService.updateProduct(requestProduct, productId);
        return ResponseEntity.ok(new ApiResponse<>("update success", product));
    }catch (Exception e)
    {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",e.getMessage()));
    }}
    //6 end point
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {
        try {
             productService.deleteProductById( productId);
            return ResponseEntity.ok(new ApiResponse<>("delete  success", null));
        }catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",e.getMessage()));
        }

    }

    //7 end point
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName,@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products != null) {
                return ResponseEntity.ok(new ApiResponse<>("found", products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something error", null));
        }

       }
    //8 end point
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String categoryName,@PathVariable String brandName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(categoryName, brandName);
            if (products != null) {
                return ResponseEntity.ok(new ApiResponse<>("found", products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something error", null));
        }

    }
    //9 end point
    @GetMapping("/products/by/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByBrand(category);
            if (products != null) {
                return ResponseEntity.ok(new ApiResponse<>("found", products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something error", null));
        }

    }

    //10 end point
    @GetMapping("/products/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndName(@PathVariable String category,@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndName(category,productName);
            if (products != null) {
                return ResponseEntity.ok(new ApiResponse<>("found", products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something error", null));
        }
 }

    //11 end point
    @GetMapping("/products/by/brand")
//    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        public List<Product> getProductsByBrand(@RequestParam String brand) {

            try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products != null) {
            //  return ResponseEntity.ok(new ApiResponse<>("found", products));
           return ResponseEntity.ok(products).getBody();

            }
         //   return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));

        } catch (Exception e) {
            return (List<Product>) ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something error", null));
        }
    //    System.out.print("inside getProductByBrand.");
        return (List<Product>) ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something 2 error", null));


    }



  @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand,@PathVariable String productName)
  {
      try {
          Long count = productService.countProductsByBrandAndName(brand, productName);
          return ResponseEntity.ok().body(new ApiResponse("Number:-", count));
      }
      catch (Exception e)
      {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error:-",null));
      }
  }
}
