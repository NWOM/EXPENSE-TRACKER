package authService.service;

import authService.entities.UserInfo;
import authService.entities.RefreshTokens;
import authService.repository.RefreshTokenRepository;
import authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired  RefreshTokenRepository refreshTokenRepository;
    @Autowired  UserRepository userRepository;
    public RefreshTokens createRefreshToken(String username){
        UserInfo userInfoExtracted=userRepository.findByUsername(username);
        RefreshTokens refreshTokens=RefreshTokens.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshTokens);
    }
    public RefreshTokens verifyExpiration(RefreshTokens tokens){
        if(tokens.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(tokens);
            throw new RuntimeException(tokens.getToken()+"Refresh Token is Expired Pls make  a login ");
        }
        return tokens;
    }
    public Optional<RefreshTokens> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
