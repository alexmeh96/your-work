package com.coder.yourwork.service;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Profile;
import com.coder.yourwork.model.Role;
import com.coder.yourwork.model.User;
import com.coder.yourwork.repo.ExecutorRepo;
import com.coder.yourwork.repo.ProfileRepo;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;
    private final ExecutorRepo executorRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(ProfileRepo profileRepo, UserRepo userRepo, ExecutorRepo executorRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
        this.executorRepo = executorRepo;
    }

    public Profile getUserProfile(Long userId) {
        return profileRepo.getProfileByAuth_Id(userId).orElse(null);
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public long countUser() {
        return userRepo.count();
    }

    public boolean createFirstUser(User user) {
        Profile profile = new Profile();

        user.setProfile(profile);
        profile.setAuth(user);
        userRepo.save(user);
        return true;
    }

    public boolean executorExist(Long userId) {
        return executorRepo.existsByAuth_Id(userId);
    }

    public List<User> allUser() {
        return userRepo.findAll();
    }

    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    public void updateUserRole(User user, List<String> roleList) {
        if (roleList == null) {
            user.getRoles().clear();
        } else {
            Set<Role> roles = roleList.stream().map(Role::valueOf).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        userRepo.save(user);
    }

    public boolean updateUserEmail(User user, UserDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            return false;
        }

        user.setEmail(userDto.getEmail());
        userRepo.save(user);

        return true;
    }

    public boolean updateUserPassword(User user, UserDto userDto) {
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);

        return true;
    }
}
