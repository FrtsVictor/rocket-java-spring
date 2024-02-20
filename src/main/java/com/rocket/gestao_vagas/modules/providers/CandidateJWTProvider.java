package com.rocket.gestao_vagas.modules.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CandidateJWTProvider {
    @Value("${security.auth.secret.candidate}")
    private String secret;

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        token = token.replace("Bearer", "").trim();

        try {
            return JWT.require(algorithm).build().verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

}
