package com.softix.app_back.profile;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/me")
    public MyProfileDTO findMyProfile() {
        return profileService.findMyProfile();
    }

    @PutMapping("/me")
    public MyProfileDTO updateMyProfile(@Valid @RequestBody UpdateMyProfileRequest request) {
        return profileService.updateMyProfile(request);
    }

}