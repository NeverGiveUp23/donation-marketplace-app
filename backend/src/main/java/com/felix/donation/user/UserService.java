package com.felix.donation.user;

import com.felix.donation.user.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    // this is the data access service, it is another way to access the database
    private final UserDAO userDAO;
    private final UserDTOMapper userDTOMapper;

    public UserService(@Qualifier("postgresJPA") UserDAO userDAO, UserDTOMapper userDTOMapper) {
        this.userDAO = userDAO;
        this.userDTOMapper = userDTOMapper;
    }


    // method to check if the user exists
    private void checkIfUserDoesNotExists(Long userId){
        // check if the customer exists
        boolean userDoesNotExist = !userDAO.existUserById(userId);
        if(userDoesNotExist){
            throw new IllegalStateException(
                    String.format("User with id %s does not exist", userId)
            );
        };
    };

    // method to check if the user with the specific email exists
    private void checkIfEmailExists(String userEmail, String emailAlreadyTakenMessage){
        boolean userEmailDoesExist = userDAO.existsUserWithEmail(userEmail);
        if(userEmailDoesExist){
            throw new IllegalStateException(emailAlreadyTakenMessage);
        };
    };


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDAO.SelectUserByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("User with email %s not found", email)
                        )
                );
    }


    public List<UserDTO> selectAllUsers(){
        return userDAO.selectAllUsers()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> selectUserById(Long userId){
        return userDAO.selectUserById(userId)
                .map(userDTOMapper);
    }



    public void signUpUser(UserRegistrationRequest userRegReq){
        String email = userRegReq.email();
        checkIfEmailExists(email, "Email already taken");

        // create the user and save
        User newUser = new User(
                userRegReq.firstName(),
                userRegReq.lastName(),
                userRegReq.email(),
                userRegReq.age(),
                userRegReq.password(),
                userRegReq.userRole()
                );
        userDAO.insertUser(newUser);
    }


    public UserDTO getUser(long id) {
        return userDAO.selectUserById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id %s not found".formatted(id)
                ));
    };


}
