package ir.jimsa.user.ws.service.impl;

import ir.jimsa.user.ws.exception.UserServiceException;
import ir.jimsa.user.ws.io.UserRepository;
import ir.jimsa.user.ws.io.entity.UserEntity;
import ir.jimsa.user.ws.service.UserService;
import ir.jimsa.user.ws.shared.Constants;
import ir.jimsa.user.ws.shared.Utils;
import ir.jimsa.user.ws.shared.dto.UserDto;
import ir.jimsa.user.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final Utils utils;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity existUser = userRepository.findUserEntityByEmail(userDto.getEmail());

        if (existUser != null) {
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(utils.generateUserId(Constants.USER_ID_LENGTH));

        UserEntity storedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        UserEntity existUser = userRepository.findUserEntityByUserId(userId);

        if (existUser == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        existUser.setName(userDto.getName());
        existUser.setFamily(userDto.getFamily());

        UserEntity updatedUser = userRepository.save(existUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;

    }

    @Override
    public void deleteUser(String userId) {
        UserEntity existUser = userRepository.findUserEntityByUserId(userId);

        if (existUser == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(existUser);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        if (page > 0) {
            page--;
        }
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        List<UserEntity> userEntities = userEntityPage.getContent();

        return userEntities.stream()
                .map(userEntity -> {
                    UserDto userDto = new UserDto();
                    BeanUtils.copyProperties(userEntity, userDto);
                    return userDto;
                })
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
