package com.felix.donation.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository("postgresJPA")
public class UserJPADataAccessService implements UserDAO{

    private final UserRepository userRepository;
    @Override
    public List<User> selectAllUsers() {
        Page<User> page =  userRepository.findAll(Pageable.ofSize(10));
        return page.getContent();
    }

    @Override
    public Optional<User> selectUserById(Long id) {
        return (userRepository.findById(id));
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existUserById(Long id) {
        return userRepository.existsUserById(id);
    }

    @Override
    public void updateUser(User update) {
        userRepository.save(update);
    }

    @Override
    public Optional<User> SelectUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
