package authService.controller;

import authService.entities.RefreshTokens;
import authService.request.AuthRequestDTO;
import authService.request.RefreshTokenRequestDTO;
import authService.response.JwtResponseDTO;
import authService.service.JwtService;
import authService.service.RefreshTokenService;
import authService.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

/**
 *  this controller manages user authentication and the issuance of
 *  JWT tokens, providing endpoints for both login and token refresh
 *  OPERATIONS
 */

@Controller
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()) {
            RefreshTokens refreshTokens = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String userId = userDetailService.getUserByUsername(authRequestDTO.getUsername());
            if (Objects.nonNull(userId) && Objects.nonNull(refreshTokens)) {
                return new ResponseEntity<>(JwtResponseDTO.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                        .token(refreshTokens.getToken())
                        .build(), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokens::getUserInfo)
                .map(userInfo -> {
                    String acessToken= jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(acessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(()->new RuntimeException("Refresh Token is not in DB>>!!"));
    }

}
