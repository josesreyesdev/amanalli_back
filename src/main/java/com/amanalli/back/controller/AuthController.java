package com.amanalli.back.controller;

import com.amanalli.back.infra.security.JwtResponse;
import com.amanalli.back.infra.security.TokenService;
import com.amanalli.back.mappers.UsuarioAuthRequest;
import com.amanalli.back.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<JwtResponse> authUser(@RequestBody UsuarioAuthRequest usuarioAuthRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                usuarioAuthRequest.email(), usuarioAuthRequest.password()
        );
        var userAuth = authenticationManager.authenticate(authToken);

        var JWTToken = tokenService.generateToken((Usuario) userAuth.getPrincipal());
        return ResponseEntity.ok(new JwtResponse(JWTToken));
    }
}
