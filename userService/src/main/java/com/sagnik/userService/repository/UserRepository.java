package com.sagnik.userService.repository;

import com.sagnik.userService.entities.UserInfo;
import com.sagnik.userService.entities.UserInfoDto;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends CrudRepository<UserInfo,String> {
    Optional<UserInfo> findByUserId(String userId);
}
