package com.shoppingproduct.service.user;

import com.shoppingproduct.AddRequest.CreateUserRequest;
import com.shoppingproduct.AddRequest.UserUpdateRequest;
import com.shoppingproduct.model.UserDetails;

public interface IUserService {
    UserDetails getUserById(Long userId);
    UserDetails createUser(CreateUserRequest request);
    UserDetails updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

}
