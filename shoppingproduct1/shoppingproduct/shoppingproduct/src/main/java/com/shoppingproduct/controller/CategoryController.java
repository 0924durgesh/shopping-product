package com.shoppingproduct.controller;


import com.shoppingproduct.model.Category;
import com.shoppingproduct.response.ApiResponse;
import com.shoppingproduct.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/categories")//time :2:09
public class CategoryController {

    private static final String INTERNAL_SEVER_ERROR ="INTERNAL_SEVER_ERROR ";
    private  final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //1 http://localhost:9080/categories/allCategories
    @GetMapping("/allCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok().body(categoryList);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(null);

        }
    }
//http://localhost:9080/categories/addCategory
    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        try
        {   Category savedcategory=categoryService.addCategory(category);
           //  return ResponseEntity.ok(new ApiResponse("save category success",savedcategory));
        return ResponseEntity.ok().body(savedcategory);
        }
        catch (Exception e)
        {
           // return ResponseEntity.status(CONFLICT).body(new ApiResponse("Error:",e.getMessage()));
        return ResponseEntity.ok().body(null);
        }
   }

  //http://localhost:9080/categories/categoryById/1
   @GetMapping("/categoryById/{id}")
    public ResponseEntity<Category>  getCategoryById(@PathVariable Long id)
   {
          try{
       Category category=categoryService.getCategoryById(id);
      // return ResponseEntity.ok(new ApiResponse("found category success",category));
        return  ResponseEntity.ok(category);
            }
        catch (Exception e)
    {   System.out.print(e.getMessage());
      //  return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",e.getMessage()));
    return ResponseEntity.ok(null);
    }
   }

//if Use @RequestParam-> http://localhost:9080/categories/categoryByName?categoryName="Gadget"
    // http://localhost:9080/categories/categoryByName/Gadget
    @GetMapping("/categoryByName/{categoryName}")
    public ResponseEntity<Category>  getCategoryByName(@PathVariable String categoryName)
    {
        try{
            Category category=categoryService.getCategoryByName(categoryName);
          //  return ResponseEntity.ok(new ApiResponse("found category success",category));
               return ResponseEntity.ok(category);
       }
        catch (Exception e)
        {
           // return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",e.getMessage()));
         return (ResponseEntity<Category>) ResponseEntity.notFound();
        }
    }
    @GetMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String>  deleteCategoryById(@PathVariable Long categoryId)
    {
        try{
            categoryService.deleteCategoryById(categoryId);
            //return ResponseEntity.ok(new ApiResponse("found category success",null));
        return  ResponseEntity.ok("delete category success");
        }
        catch (Exception e)
        {
          //  return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",e.getMessage()));
         return (ResponseEntity<String>) ResponseEntity.status(Integer.parseInt(e.getMessage()));
        }
    }


    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category,@PathVariable Long id)
    {   try
    {      Category updateCategory=categoryService.updateCategory(category,id);
           return ResponseEntity.ok(new ApiResponse("update ",updateCategory));
    }catch (Exception e)
    {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error:",null));
    }

    }




}
