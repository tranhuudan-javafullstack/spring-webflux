package com.huudan.paymentprocessingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.huudan.paymentprocessingservice", "com.huudan.commonservice"})
public class PaymentprocessingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentprocessingserviceApplication.class, args);
	}

}
