package com.felix.donation.user;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> selectAllUsers();

    Optional<User> selectUserById(Long id);

    void insertUser(User user);

    boolean existsUserWithEmail(String email);

    void deleteUserById(Long id);

    boolean existUserById(Long id);

    void updateUser(User update);

    Optional<User> SelectUserByEmail(String email);
}
