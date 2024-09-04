package authService.controller;

import authService.entities.RefreshTokens;
import authService.model.UserInfoDTO;
import authService.response.JwtResponseDTO;
import authService.service.JwtService;
import authService.service.RefreshTokenService;
import authService.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            String userId = userDetailService.signupUser(userInfoDTO);
            if (Objects.isNull(userId)) {
                return new ResponseEntity<>("Alreaady Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshTokens refreshTokens = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken)
                    .token(refreshTokens.getToken()).userId(userId).build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception iin User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/auth/v1/ping")
    public ResponseEntity<String> ping(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.isAuthenticated()){
            String userId=userDetailService.getUserByUsername(authentication.getName());
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok(userId);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
