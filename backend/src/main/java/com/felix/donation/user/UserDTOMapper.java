package com.felix.donation.user;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;
@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getPassword(),
                user.getUserRole(),
                Collections.singletonList(user.getUserAuthRole())
        );
    }
}
