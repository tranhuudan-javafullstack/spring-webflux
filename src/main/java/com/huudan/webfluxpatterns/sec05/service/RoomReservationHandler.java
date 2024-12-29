package com.huudan.webfluxpatterns.sec05.service;

import com.huudan.webfluxpatterns.sec05.client.RoomClient;
import com.huudan.webfluxpatterns.sec05.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoomReservationHandler extends ReservationHandler {

    RoomClient client;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toRoomRequest)
                .transform(this.client::reserve)
                .map(this::toResponse);
    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return RoomReservationRequest.create(
                request.getCity(),
                request.getFrom(),
                request.getTo(),
                request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(RoomReservationResponse response) {
        return ReservationItemResponse.create(
                response.getReservationId(),
                this.getType(),
                response.getCategory(),
                response.getCity(),
                response.getCheckIn(),
                response.getCheckOut(),
                response.getPrice()
        );
    }

}
