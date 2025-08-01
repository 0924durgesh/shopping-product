package com.shoppingproduct.service.product;

import com.shoppingproduct.AddRequest.AddRequestProduct;
import com.shoppingproduct.AddRequest.ProductUpdateRequest;
import com.shoppingproduct.exceptions.ProductNotFoundException;
import com.shoppingproduct.model.Category;
import com.shoppingproduct.model.Product;
import com.shoppingproduct.repository.CategoryRepository;
import com.shoppingproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service   // time: 24:00 before 44:00
public class ProductService implements IProductService{

     @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  CategoryRepository categoryRepository;


//    @Override  //time:44:21
//    public Product addProduct(AddRequestProduct request) {
//        //check if the category is found  in the DB
//        //if Yes ,set it as the new product category
//        //if no ,the save  it as a new category
//        //the set as the new product category.
//  //57:00
// Category category=Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
//         .orElseGet(()->{
//      return  new Category(request.getCategory().getName());
//        });
//          request.setCategory(category);
//          categoryRepository.save(category);
//       return  productRepository.save(createProduct(request,category));
//    }


    @Override  //time:44:21
    public Product addProduct(AddRequestProduct request) {
        //check if the category is found  in the DB
        //if Yes ,set it as the new product category
        //if no ,the save  it as a new category
        //the set as the new product category.
        //57:00
        Category category=Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(()->{
                    return  new Category(request.getCategory());
                });
        request.setCategory(String.valueOf(category));
        categoryRepository.save(category);
        return  productRepository.save(createProduct(request,category));
    }

    //time:50:00
    private  Product createProduct(AddRequestProduct requestProduct, Category category)
    {
              return  new Product(
                      requestProduct.getName(),
                      requestProduct.getBrand(),
                      requestProduct.getPrice(),
                      requestProduct.getInventory(),
                      requestProduct.getDescription(),
                       category
               );
    }


//    @Override
//    public Product addProduct(AddRequestProduct request) {
//        //check if the category is found  in the DB
//        //if Yes ,set it as the new product category
//        //if no ,the save  it as a new category
//        //the set as the new product category.
////    Optional<Category> category= Optional.ofNullable(categoryRepository.
////            findByName(request.getCategory().getName()));
//         Category category=Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
//                .orElseGet(()->{
//                    Category newCategory= new Category(request.getCategory());
//                             newCategory.setProducts(request.getName());
//                    return categoryRepository.save(newCategory);
//                }
//                );
//
//        //request.setCategory(category.getName()) ;
//        return  productRepository.save(createProduct(request,category));
//    }
  /*  private  Product createProduct(AddRequestProduct requestProduct, Category category)
    {
        return  new Product(
                requestProduct.getName(),
                requestProduct.getBrand(),
                requestProduct.getPrice(),
                requestProduct.getInventory(),
                requestProduct.getDescription(),
                 category

       );
    }*/

    @Override
    public Product getProductById(Long id) {
       return   productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found."));
      //  return product;
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.findById(id);
//                .ifPresentOrElse(productRepository::delete,
//                        () -> {
//                            throw new ProductNotFoundException("product not found..");
//                        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest productUpdateRequest, Long productId) {
  //      Product product= productRepository.findById(productId);

         return productRepository.findById(productId)
           .map(existingProduct->UpdateExistingProduct(existingProduct,productUpdateRequest))
              .orElseThrow(()->new ProductNotFoundException("Product not found .."));

    }

    //time:59:46
    private  Product UpdateExistingProduct(Product existingProduct, ProductUpdateRequest request)
    {   existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category=categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return  existingProduct;
    }
    @Override
    public List<Product> getProductsByName(String product) {
        return productRepository.findByName(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return  productRepository.findByBrand(brand);

    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }



    @Override
    public List<Product> getProductsByCategoryAndName(String category, String product) {
        return productRepository.findByCategoryNameAndProductName(category,product);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String productName) {
        List<Product> products= productRepository.findByBrandAndName(brand,productName);
        return products;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
