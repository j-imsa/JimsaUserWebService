package ir.jimsa.user.ws.ui.controller;

import ir.jimsa.user.ws.service.UserService;
import ir.jimsa.user.ws.shared.dto.UserDto;
import ir.jimsa.user.ws.ui.model.request.RequestOperationName;
import ir.jimsa.user.ws.ui.model.request.RequestOperationStatus;
import ir.jimsa.user.ws.ui.model.request.UserCreateRest;
import ir.jimsa.user.ws.ui.model.response.OperationStatusModel;
import ir.jimsa.user.ws.ui.model.response.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public User getUser(@PathVariable String userId) {
        User returnValue = new User();
        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public User createUser(@RequestBody UserCreateRest newUser) {
        User returnValue = new User();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(newUser, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public User updateUser(@PathVariable String userId, @RequestBody UserCreateRest updateUser) {
        User returnValue = new User();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(updateUser, userDto);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(userId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}
