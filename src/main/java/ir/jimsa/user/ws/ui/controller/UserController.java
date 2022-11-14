package ir.jimsa.user.ws.ui.controller;

import ir.jimsa.user.ws.service.UserService;
import ir.jimsa.user.ws.shared.dto.UserDto;
import ir.jimsa.user.ws.ui.model.request.UserCreateRest;
import ir.jimsa.user.ws.ui.model.response.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        User returnValue = new User();

        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public User createUser(@RequestBody UserCreateRest newUser) {
        User returnValue = new User();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(newUser, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

}
