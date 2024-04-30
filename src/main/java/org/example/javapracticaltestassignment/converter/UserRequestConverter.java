package org.example.javapracticaltestassignment.converter;

import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.dto.request.UserRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserRequestConverter implements ConverterEx<UserRequest, User> {

    @Override
    public User convert(UserRequest source) {
        return convert(Collections.singletonList(source)).get(0);
    }

    @Override
    public List<User> convert(List<UserRequest> source) {
        return source.stream()
                .map(this::mapToUser)
                .toList();
    }

    @Override
    public User convert(UserRequest source, long id) {

        User user = convert(source);
        user.setId(id);

        return user;
    }

    private User mapToUser(UserRequest userRequest) {

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
