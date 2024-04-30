package org.example.javapracticaltestassignment.service;

import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        return userRepository.findUser(id);
    }

    @Transactional
    public User update(User update) {

        User user = userRepository.findUser(update.getId());

        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setEmail(update.getEmail());
        user.setAddress(update.getAddress());
        user.setPhoneNumber(update.getPhoneNumber());
        user.setBirthDate(user.getBirthDate());

        return userRepository.save(user);
    }

    @Transactional
    public User updatePartial(User update) {

        User user = userRepository.findUser(update.getId());

        setIfNotNull(update.getFirstName(), user::setFirstName);
        setIfNotNull(update.getLastName(), user::setLastName);
        setIfNotNull(update.getEmail(), user::setEmail);
        setIfNotNull(update.getAddress(), user::setAddress);
        setIfNotNull(update.getPhoneNumber(), user::setPhoneNumber);
        setIfNotNull(update.getBirthDate(), user::setBirthDate);

        return userRepository.save(user);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(long id) {
        userRepository.delete(id);
    }

    public List<User> getInRange(LocalDate from, LocalDate to) {
        return userRepository.findAll(from, to);
    }

    private <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }
}
