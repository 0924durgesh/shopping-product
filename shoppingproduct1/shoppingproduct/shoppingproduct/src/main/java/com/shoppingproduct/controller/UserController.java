package com.shoppingproduct.controller;

import com.shoppingproduct.AddRequest.CreateUserRequest;
import com.shoppingproduct.AddRequest.UserUpdateRequest;
import com.shoppingproduct.model.UserDetails;
import com.shoppingproduct.response.ApiResponse;
import com.shoppingproduct.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId)
    {   try {
        UserDetails user=userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("success",user));
    }catch (Exception exception)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", exception.getMessage()));

    }

    }
    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request)
    {
        try {
            UserDetails user=userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse<>("success",user));
        }catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", exception.getMessage()));

        }

    }
    @PutMapping("/updateUser/")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId)
    {
        try {
            UserDetails user=userService.updateUser(request,userId);
            return ResponseEntity.ok(new ApiResponse<>("success",user));
        }catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", exception.getMessage()));

        }
    }

    public ResponseEntity<ApiResponse> deleteUser(Long userId)
    {
        try {
           userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse<>("delete success",null));
        }catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", exception.getMessage()));

        }

    }

}
