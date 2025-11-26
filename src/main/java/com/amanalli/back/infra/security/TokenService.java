package com.amanalli.back.infra.security;

import com.amanalli.back.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("amanalli")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getIdUsuario())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw  new RuntimeException();
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token is null");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //validando la firma del token
            verifier = JWT.require(algorithm)
                    .withIssuer("amanalli")
                    .build()
                    .verify(token);

            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("Excepción JWTVerificationException: "+ exception.getMessage());
        }

        if ((verifier != null ? verifier.getSubject() : null) == null) {
            throw new RuntimeException("Verifier invalid");
        }
        return verifier.getSubject();
    }
}
