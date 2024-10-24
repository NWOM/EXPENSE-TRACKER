package authService.service;


import authService.entities.UserInfo;
import authService.eventProducer.UserInfoEvent;
import authService.eventProducer.UserInfoProducer;
import authService.model.UserInfoDTO;
import authService.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImpl implements UserDetailsService
{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;
    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDTO userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public String signupUser(UserInfoDTO userInfoDto){
        //        ValidationUtil.validateUserAttributes(userInfoDto);
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
            return null;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
        // pushEventToQueue
        userInfoProducer.sendEventToKafka(userInfoEventToPublish(userInfoDto,userId));
        return userId;
    }

    public String getUserByUsername(String userName) {
        return Optional.of(userRepository.findByUsername(userName)).map(UserInfo::getUserId).orElse(null);
    }
    private UserInfoEvent userInfoEventToPublish(UserInfoDTO userInfoDTO,String userId){
        return UserInfoEvent.builder()
                .userId(userId)
                .firstName(userInfoDTO.getFirstName())
                .lastName(userInfoDTO.getLastName())
                .email(userInfoDTO.getEmail())
                .phoneNumber(userInfoDTO.getPhoneNumber())
                .build();
    }
}