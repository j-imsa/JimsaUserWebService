package ir.jimsa.user.ws.service;

import ir.jimsa.user.ws.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
