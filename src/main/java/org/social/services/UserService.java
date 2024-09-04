package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.UserRequest;
import org.social.dto.response.UserResponse;
import org.social.entities.User;
import org.social.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public List<UserResponse> getAllUser() {
        return this.userRepository.findAll().stream().map(UserResponse::convertUserToUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse getOneUserByUsername(String username) {
        User foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not found"));
        return UserResponse.convertUserToUserResponse(foundUser);
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserRequest.convertUserRequestToUser(userRequest);
        User savedUser = this.userRepository.save(user);
        return UserResponse.convertUserToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse updateUserByUsername(String username, UserRequest userRequest) {
        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not found"));
        User requestUser = UserRequest.convertUserRequestToUser(userRequest);
        User user = updateUser(foundUser, requestUser);
        User savedUser = userRepository.save(user);
        return UserResponse.convertUserToUserResponse(savedUser);
    }
    @Transactional(Transactional.TxType.SUPPORTS)
    protected User updateUser(User foundUser, User requestUser){
        Optional.ofNullable(requestUser.getName()).ifPresent(foundUser::setName);
        Optional.ofNullable(requestUser.getSurname()).ifPresent(foundUser::setSurname);
        Optional.ofNullable(requestUser.getBirthdate()).ifPresent(foundUser::setBirthdate);
        return foundUser;
    }

    public Boolean deleteUserByUsername(String username) {
        try {
            User foundUser = this.userRepository.findByUsername(username).orElseThrow(()-> new IllegalArgumentException(STR."\{username}" + " is not found"));
            this.userRepository.delete(foundUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
