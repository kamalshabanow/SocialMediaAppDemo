package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.UserRequest;
import org.social.dto.response.UserResponse;
import org.social.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{username}")
    public UserResponse getOneUserByUsername(@PathVariable String username){
        return userService.getOneUserByUsername(username);
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        return this.userService.createUser(userRequest);
    }

    @PutMapping("/{username}")
    public UserResponse updateUser(@PathVariable String username, @RequestBody UserRequest userRequest){
        return this.userService.updateUserByUsername(username,userRequest);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username){
        if(this.userService.deleteUserByUsername(username)){
            return ResponseEntity.ok("user deleted successfully");
        }else{
            return ResponseEntity.badRequest().body("an error occurred, user not deleted");
        }
    }
}
