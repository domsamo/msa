package fbc.userservice.service;

import fbc.userservice.dto.UserDto;
import fbc.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring security
 * UserDetailsService를 상속받아 loadUserByUsername을 구현해야 한다.
 */
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
