package com.huudan.profileservice.controller;

import com.google.gson.Gson;
import com.huudan.commonservice.utils.CommonFunction;
import com.huudan.profileservice.model.ProfileDTO;
import com.huudan.profileservice.service.ProfileService;
import com.huudan.profileservice.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final Gson gson = new Gson();

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>> getAllProfile() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @GetMapping(value = "/checkDuplicate/{email}")
    public ResponseEntity<Mono<Boolean>> checkDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(profileService.checkDuplicate(email));
    }

    @PostMapping
    public ResponseEntity<Mono<ProfileDTO>> createNewProfile(@RequestBody String requestStr) {
        InputStream inputStream = ProfileController.class.getClassLoader().getResourceAsStream(Constant.JSON_REQ_CREATE_PROFILE);
        CommonFunction.jsonValidate(inputStream, requestStr);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.createNewProfile(gson.fromJson(requestStr, ProfileDTO.class)));
    }
}
