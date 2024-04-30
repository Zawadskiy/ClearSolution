package org.example.javapracticaltestassignment.repository;

import org.example.javapracticaltestassignment.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Imaging we have her a working repository
@Repository
public class UserRepository {

    public User findUser(long id) {
        return new User();
    }

    public User save(User save) {
        return new User();
    }

    public void delete(long id) {
    }

    public List<User> findAll(LocalDate from, LocalDate to) {
        return new ArrayList<>();
    }
}
