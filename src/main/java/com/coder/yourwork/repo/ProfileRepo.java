package com.coder.yourwork.repo;

import com.coder.yourwork.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Optional<Profile> getProfileByAuth_Id(Long id);
}
