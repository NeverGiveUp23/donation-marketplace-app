package com.felix.donation.user;

import com.felix.donation.user.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userServiceTest;

    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    @Mock
    private UserDAO userDAO;


    @BeforeEach
    void setUp() {
        userServiceTest = new UserService(userDAO, userDTOMapper);
    }

    @Test
    void itShouldSelectAllUsers() {
        // Given
        userServiceTest.selectAllUsers();
        // Then
        verify(userDAO).selectAllUsers();

    }

    @Test
    void itShouldSelectUserById() {
        // Given
        long id = 1;
        User user = new User(
                "Pro", "Grammer", "felix@aol.com", 22, "password", UserRole.RECEIVER
        );
        // When
        when(userDAO.selectUserById(id)).thenReturn(Optional.of(user));

        UserDTO expected = userDTOMapper.apply(user);

        UserDTO actual = userServiceTest.getUser(id);
        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldThrowExceptionIfUserNotFound(){
        long id = 1;
        // When
        when(userDAO.selectUserById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> userServiceTest.getUser(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "User with id %s not found".formatted(id));
    }

    @Test
    void itShouldSignUpUser() {
        // Given
        // When
        // Then

    }
}