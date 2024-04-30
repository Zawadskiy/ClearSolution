package org.example.javapracticaltestassignment.converter;

import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        return convert(Collections.singletonList(source)).get(0);
    }

    @Override
    public List<UserResponse> convert(List<User> source) {
        return source.stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    private UserResponse mapToUserResponse(User user) {

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setPhoneNumber(user.getPhoneNumber());

        return userResponse;
    }
}
