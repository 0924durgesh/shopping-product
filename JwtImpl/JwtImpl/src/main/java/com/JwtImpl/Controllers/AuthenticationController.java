package com.JwtImpl.Controllers;


import com.JwtImpl.Dtos.LoginResponse;
import com.JwtImpl.Dtos.LoginUserDto;
import com.JwtImpl.Dtos.RegisterUserDto;
import com.JwtImpl.Utils.JwtService;
import com.JwtImpl.entity.User;
import com.JwtImpl.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        System.out.println(registerUserDto.toString());
        User registeredUser = authenticationService.signup(registerUserDto);
        System.out.println(registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/test")
    public String test()
    {  System.out.print("test");
        return ResponseEntity.accepted().toString();
    }

}
