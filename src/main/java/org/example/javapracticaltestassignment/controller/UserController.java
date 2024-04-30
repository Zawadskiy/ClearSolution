package org.example.javapracticaltestassignment.controller;

import jakarta.validation.Valid;
import org.example.javapracticaltestassignment.converter.UserConverter;
import org.example.javapracticaltestassignment.converter.UserPartialRequestConverter;
import org.example.javapracticaltestassignment.converter.UserRequestConverter;
import org.example.javapracticaltestassignment.domain.User;
import org.example.javapracticaltestassignment.dto.request.UserPartialRequest;
import org.example.javapracticaltestassignment.dto.request.UserRequest;
import org.example.javapracticaltestassignment.dto.response.ListResponse;
import org.example.javapracticaltestassignment.dto.response.SingleResponse;
import org.example.javapracticaltestassignment.dto.response.UserResponse;
import org.example.javapracticaltestassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserRequestConverter userRequestConverter;
    private final UserPartialRequestConverter userPartialRequestConverter;
    private final UserConverter userConverter;


    @Autowired
    public UserController(UserRequestConverter userRequestConverter, UserConverter userConverter,
                          UserService userService, UserPartialRequestConverter userPartialRequestConverter) {
        this.userRequestConverter = userRequestConverter;
        this.userConverter = userConverter;
        this.userService = userService;
        this.userPartialRequestConverter = userPartialRequestConverter;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequest request) {

        User newUser = userService.create(userRequestConverter.convert(request));

        return ResponseEntity.created(UriComponentsBuilder.fromUriString("/users/" + newUser.getId())
                        .build()
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@Valid @RequestBody UserRequest request, @PathVariable long id) {

        userService.update(userRequestConverter.convert(request, id));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SingleResponse<UserResponse>> patch(@Valid @RequestBody UserPartialRequest update, @PathVariable long id) {

        userService.updatePartial(userPartialRequestConverter.convert(update, id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        userService.delete(id);

        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<UserResponse>> get(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("StartDate must be before endDate");
        }

        List<UserResponse> result = userConverter.convert(userService.getInRange(startDate, endDate));

        return new ResponseEntity<>(new ListResponse<>(result), HttpStatus.OK);
    }
}
