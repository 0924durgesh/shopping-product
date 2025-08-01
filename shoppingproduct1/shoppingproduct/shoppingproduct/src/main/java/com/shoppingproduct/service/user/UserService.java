package com.shoppingproduct.service.user;

import com.shoppingproduct.AddRequest.CreateUserRequest;
import com.shoppingproduct.AddRequest.UserUpdateRequest;

import com.shoppingproduct.exceptions.AllReadyExistsException;
import com.shoppingproduct.model.UserDetails;
import com.shoppingproduct.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service  //5:47
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails getUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override//5:56
    public UserDetails createUser(CreateUserRequest request) {

        return Optional.of(request).filter(user->!userRepository
                .existsByEmailId(request.getEmail()))
                .map(req->{
                    UserDetails user=new UserDetails();
                     user.setFirstName(request.getFirstName());
                     user.setLastName(request.getLastName());
                     user.setEmail(request.getEmail());
                     user.setPassword(request.getPassword());
                     return userRepository.save(user);
                        }).orElseThrow(()->new AllReadyExistsException("Opps "+request.getEmail()+"  user all ready exists"));


    }

    @Override
    public UserDetails updateUser(UserUpdateRequest request, Long userId) {

       UserDetails exitingUser=userRepository.findByUserId(userId);
       if(exitingUser!=null) {
           exitingUser.setFirstName(request.getFirstName());
           exitingUser.setLastName(request.getLastName());
           userRepository.save(exitingUser);
       }
       else
       {
           return null;
       }
     return exitingUser;
    }

    @Override
    public void deleteUser(Long id) {
        //userRepository.findById(userId).ifPresentOrElse(userRepository::delete,()->{throw new ResourceNotFoundException("User not found!");});

        UserDetails user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        if(user!=null)
        {
            userRepository.deleteById(id);
        }

    }
}
