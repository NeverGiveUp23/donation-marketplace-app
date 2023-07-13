package com.felix.donation.user;

import java.util.List;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        String password,
        UserRole userRole,
        List<UserAuthRole> userAuthRoles)
{
}
