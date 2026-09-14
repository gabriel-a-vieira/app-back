package com.softix.app_back.profile;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public MyProfileDTO findMyProfile() {
        return profileService.findMyProfile();
    }

    @PutMapping("/me")
    public MyProfileDTO updateMyProfile(@Valid @RequestBody UpdateMyProfileRequest request) {
        return profileService.updateMyProfile(request);
    }

}