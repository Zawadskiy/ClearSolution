package org.example.javapracticaltestassignment.converter;

import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.dto.request.UserPartialRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserPartialRequestConverter implements ConverterEx<UserPartialRequest, User> {

    @Override
    public User convert(UserPartialRequest source, long id) {

        User user = convert(source);
        user.setId(id);

        return user;
    }

    @Override
    public User convert(UserPartialRequest source) {
        return convert(Collections.singletonList(source)).get(0);
    }

    @Override
    public List<User> convert(List<UserPartialRequest> source) {
        return source.stream()
                .map(this::mapToUser)
                .toList();
    }

    private User mapToUser(UserPartialRequest userRequest) {

        User user = new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setBirthDate(userRequest.getBirthDate());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        return user;
    }
}
