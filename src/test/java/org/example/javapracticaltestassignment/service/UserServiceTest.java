package org.example.javapracticaltestassignment.service;

import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@Import(UserService.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void findUser() {
        User newUser = new User();
        newUser.setId(1);

        Mockito.when(userRepository.findUser(1)).thenReturn(newUser);

        User user = userService.getUser(1);

        assertThat(user.getId()).isEqualTo(newUser.getId());
    }

    @Test
    public void updateUser() {

        User oldUser = new User();
        oldUser.setId(1);
        oldUser.setFirstName("OldJohn");

        User newUser = new User();
        newUser.setId(1);
        newUser.setFirstName("NewJohn");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstName("NewJohn");

        Mockito.when(userRepository.findUser(1)).thenReturn(oldUser);
        Mockito.when(userRepository.save(any())).thenReturn(newUser);

        User update = userService.update(updatedUser);

        assertThat(update.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(oldUser.getFirstName()).isEqualTo(updatedUser.getFirstName());
    }

    @Test
    public void updateUserPartial() {

        User oldUser = new User();
        oldUser.setId(1);
        oldUser.setFirstName("OldJohn");
        oldUser.setLastName("OldDou");

        User newUser = new User();
        newUser.setId(1);
        newUser.setFirstName("NewJohn");
        newUser.setLastName("OldDou");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstName("NewJohn");

        Mockito.when(userRepository.findUser(1)).thenReturn(oldUser);
        Mockito.when(userRepository.save(any())).thenReturn(newUser);

        User result = userService.updatePartial(updatedUser);

        assertThat(result.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(result.getLastName()).isEqualTo(oldUser.getLastName());
        assertThat(oldUser.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(oldUser.getLastName()).isEqualTo("OldDou");
    }

    @Test
    public void createUser() {
        User createUser = new User();
        createUser.setFirstName("John");

        User newUser = new User();
        newUser.setId(1);
        newUser.setFirstName("John");

        Mockito.when(userRepository.save(any())).thenReturn(newUser);

        User result = userService.create(createUser);

        assertThat(result.getFirstName()).isEqualTo(createUser.getFirstName());
    }
}
