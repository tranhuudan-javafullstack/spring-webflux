package com.huudan.orderservice;

import com.huudan.orderservice.client.ProductClient;
import com.huudan.orderservice.client.UserClient;
import com.huudan.orderservice.dto.ProductDto;
import com.huudan.orderservice.dto.PurchaseOrderRequestDto;
import com.huudan.orderservice.dto.PurchaseOrderResponseDto;
import com.huudan.orderservice.dto.UserDto;
import com.huudan.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserClient userClient;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private OrderFulfillmentService fulfillmentService;

	@Test
	void contextLoads() {

		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
				.map(t -> buildDto(t.getT1(), t.getT2()))
				.flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
				.doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
				.expectNextCount(4)
				.verifyComplete();


	}

	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto){
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
