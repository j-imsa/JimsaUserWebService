package ir.jimsa.user.ws.service.impl;

import ir.jimsa.user.ws.io.entity.UserEntity;
import ir.jimsa.user.ws.io.entity.UserRepository;
import ir.jimsa.user.ws.service.UserService;
import ir.jimsa.user.ws.shared.Constants;
import ir.jimsa.user.ws.shared.Utils;
import ir.jimsa.user.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final Utils utils;

    public UserServiceImpl(UserRepository userRepository, Utils utils) {
        this.userRepository = userRepository;
        this.utils = utils;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity existUser = userRepository.findUserEntityByEmail(userDto.getEmail());

        if (existUser != null) {
            throw new RuntimeException("User already exists!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword("enc pass");
        userEntity.setUserId(utils.generateUserId(Constants.USER_ID_LENGHT));

        UserEntity storedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser, returnValue);

        return returnValue;
    }
}
