package ir.jimsa.user.ws.service;

import ir.jimsa.user.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page, int limit);
}
