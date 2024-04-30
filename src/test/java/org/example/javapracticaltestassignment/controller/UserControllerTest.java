package org.example.javapracticaltestassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.javapracticaltestassignment.converter.UserConverter;
import org.example.javapracticaltestassignment.converter.UserPartialRequestConverter;
import org.example.javapracticaltestassignment.converter.UserRequestConverter;
import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.dto.request.UserPartialRequest;
import org.example.javapracticaltestassignment.dto.request.UserRequest;
import org.example.javapracticaltestassignment.handler.GlobalExceptionHandler;
import org.example.javapracticaltestassignment.service.UserService;
import org.example.javapracticaltestassignment.validator.UserRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import({UserController.class,
        UserRequestConverter.class, UserConverter.class, UserPartialRequestConverter.class,
        GlobalExceptionHandler.class,
        UserRequestValidator.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private ObjectWriter ow;

    private final UserRequest request = new UserRequest();
    private final UserPartialRequest partialRequest = new UserPartialRequest();
    private final User user = new User();


    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ow = mapper.writer().withDefaultPrettyPrinter();

        List<User> userList = new ArrayList<>();

        userList.add(user);

        given(userService.getInRange(Mockito.any(), Mockito.any())).willReturn(userList);
    }

    @Test
    public void putUser()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", "test@test.com");
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", "address");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "firstName", "test");
        ReflectionTestUtils.setField(request, "lastName", "test");
        ReflectionTestUtils.setField(request, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(request, "phoneNumber", "123");

        given(userService.update(Mockito.any())).willReturn(user);

        mvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void putUserRequiredFieldsNull()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", null);
        ReflectionTestUtils.setField(user, "firstName", null);
        ReflectionTestUtils.setField(user, "lastName", null);
        ReflectionTestUtils.setField(user, "birthDate", null);
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", "address");
        ReflectionTestUtils.setField(request, "email", null);
        ReflectionTestUtils.setField(request, "firstName", null);
        ReflectionTestUtils.setField(request, "lastName", null);
        ReflectionTestUtils.setField(request, "birthDate", null);
        ReflectionTestUtils.setField(request, "phoneNumber", "123");

        given(userService.update(Mockito.any())).willReturn(user);

        mvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void putUserOptionalFieldsNull()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", null);
        ReflectionTestUtils.setField(user, "email", "test@test.com");
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", null);
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", null);
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "firstName", "test");
        ReflectionTestUtils.setField(request, "lastName", "test");
        ReflectionTestUtils.setField(request, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(request, "phoneNumber", null);

        given(userService.update(Mockito.any())).willReturn(user);

        mvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createUserRequiredFieldsNull()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", null);
        ReflectionTestUtils.setField(user, "firstName", null);
        ReflectionTestUtils.setField(user, "lastName", null);
        ReflectionTestUtils.setField(user, "birthDate", null);
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", "address");
        ReflectionTestUtils.setField(request, "email", null);
        ReflectionTestUtils.setField(request, "firstName", null);
        ReflectionTestUtils.setField(request, "lastName", null);
        ReflectionTestUtils.setField(request, "birthDate", null);
        ReflectionTestUtils.setField(request, "phoneNumber", "123");

        given(userService.create(Mockito.any())).willReturn(user);

        mvc.perform(post("/users")
                        .contentType("application/json")
                        .content(ow.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createUserOptionalFieldsNull()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", null);
        ReflectionTestUtils.setField(user, "email", "test@test.com");
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", null);
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", null);
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "firstName", "test");
        ReflectionTestUtils.setField(request, "lastName", "test");
        ReflectionTestUtils.setField(request, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(request, "phoneNumber", null);

        given(userService.create(Mockito.any())).willReturn(user);

        mvc.perform(post("/users").contentType("application/json")
                        .content(ow.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/users/20"))
                .andDo(print());
    }

    @Test
    public void createUser()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", "test@test.com");
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", "address");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "firstName", "test");
        ReflectionTestUtils.setField(request, "lastName", "test");
        ReflectionTestUtils.setField(request, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(request, "phoneNumber", "123");

        given(userService.create(Mockito.any())).willReturn(user);

        mvc.perform(post("/users").contentType("application/json")
                        .content(ow.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/users/20"))
                .andDo(print());
    }

    @Test
    public void getUsers()
            throws Exception {

        mvc.perform(get("/users").param("startDate", LocalDate.of(2020, 1, 1).toString())
                        .param("endDate", LocalDate.of(2020, 1, 1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andDo(print());
    }

    @Test
    public void getUsersFromIsAfterTo()
            throws Exception {

        mvc.perform(get("/users").param("startDate", LocalDate.of(2022, 1, 1).toString())
                        .param("endDate", LocalDate.of(2020, 1, 1).toString()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void deleteUser()
            throws Exception {

        mvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void patchUser()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", "test@test.com");
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(request, "address", "address");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "firstName", "test");
        ReflectionTestUtils.setField(request, "lastName", "test");
        ReflectionTestUtils.setField(request, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(request, "phoneNumber", "123");

        given(userService.updatePartial(Mockito.any())).willReturn(user);

        mvc.perform(patch("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void patchUserRequiredFieldsNull()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", null);
        ReflectionTestUtils.setField(user, "firstName", null);
        ReflectionTestUtils.setField(user, "lastName", null);
        ReflectionTestUtils.setField(user, "birthDate", null);
        ReflectionTestUtils.setField(user, "phoneNumber", "123");
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(partialRequest, "address", "address");
        ReflectionTestUtils.setField(partialRequest, "email", null);
        ReflectionTestUtils.setField(partialRequest, "firstName", null);
        ReflectionTestUtils.setField(partialRequest, "lastName", null);
        ReflectionTestUtils.setField(partialRequest, "birthDate", null);
        ReflectionTestUtils.setField(partialRequest, "phoneNumber", "123");

        given(userService.updatePartial(Mockito.any())).willReturn(user);

        mvc.perform(patch("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(partialRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void patchUserBadEmail()
            throws Exception {

        ReflectionTestUtils.setField(user, "address", "address");
        ReflectionTestUtils.setField(user, "email", null);
        ReflectionTestUtils.setField(user, "firstName", "test");
        ReflectionTestUtils.setField(user, "lastName", "test");
        ReflectionTestUtils.setField(user, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(user, "phoneNumber", null);
        ReflectionTestUtils.setField(user, "id", 20);

        ReflectionTestUtils.setField(partialRequest, "address", null);
        ReflectionTestUtils.setField(partialRequest, "email", "test@@@test.com");
        ReflectionTestUtils.setField(partialRequest, "firstName", "test");
        ReflectionTestUtils.setField(partialRequest, "lastName", "test");
        ReflectionTestUtils.setField(partialRequest, "birthDate", LocalDate.of(1995, 1, 1));
        ReflectionTestUtils.setField(partialRequest, "phoneNumber", null);

        given(userService.updatePartial(Mockito.any())).willReturn(user);

        mvc.perform(patch("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(partialRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}