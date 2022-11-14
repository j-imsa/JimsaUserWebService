package ir.jimsa.user.ws.ui.controller;

import ir.jimsa.user.ws.service.UserService;
import ir.jimsa.user.ws.shared.dto.UserDto;
import ir.jimsa.user.ws.ui.model.request.UserRest;
import ir.jimsa.user.ws.ui.model.response.User;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public User createUser(@RequestBody UserRest newUser){
        User returnValue = new User();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(newUser, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

}