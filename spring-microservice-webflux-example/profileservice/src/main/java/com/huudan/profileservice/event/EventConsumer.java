package com.huudan.profileservice.event;

import com.google.gson.Gson;
import com.huudan.commonservice.utils.Constant;
import com.huudan.profileservice.model.ProfileDTO;
import com.huudan.profileservice.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
public class EventConsumer {
    private final Gson gson = new Gson();
    private final ProfileService profileService;

    public EventConsumer(ReceiverOptions<String, String> receiverOptions, ProfileService profileService) {
        this.profileService = profileService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDED_TOPIC)))
                .receive().subscribe(this::profileOnboarded);
    }

    public void profileOnboarded(ReceiverRecord<String, String> receiverRecord) {
        log.info("Profile Onboarded event");
        ProfileDTO dto = gson.fromJson(receiverRecord.value(), ProfileDTO.class);
        profileService.updateStatusProfile(dto).subscribe();
    }
}
