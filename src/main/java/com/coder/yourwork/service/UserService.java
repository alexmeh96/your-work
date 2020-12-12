package com.coder.yourwork.service;

import com.coder.yourwork.model.Profile;
import com.coder.yourwork.model.User;
import com.coder.yourwork.repo.ProfileRepo;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserService(ProfileRepo profileRepo, UserRepo userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }

    public Profile getUserProfile(Long userId) {
        return profileRepo.getProfileByAuth_Id(userId).orElse(null);
    }

    public long countUser() {
        return userRepo.count();
    }

    public boolean createFirstUser(User user) {
        userRepo.save(user);
        return true;
    }
}
