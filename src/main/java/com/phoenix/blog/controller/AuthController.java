package com.phoenix.blog.controller;

import com.phoenix.blog.service.AuthService;
import com.phoenix.blog.service.RefreshTokenService;
import com.phoenix.blog.DTO.AuthenticationResponse;
import com.phoenix.blog.DTO.LoginRequest;
import com.phoenix.blog.DTO.RefreshTokenRequest;
import com.phoenix.blog.DTO.RegisterRequest;
import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ModelAndView signup(@RequestBody RegisterRequest registerRequest) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            authService.signup(registerRequest);
            modelAndView.setViewName("success");
        }
        catch (NonUniqueResultException ex){
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activation Success", OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully");
    }
}
