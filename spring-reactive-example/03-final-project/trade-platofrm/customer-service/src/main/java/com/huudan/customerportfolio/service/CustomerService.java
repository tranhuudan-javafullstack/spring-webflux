package com.huudan.customerportfolio.service;

import com.huudan.customerportfolio.dto.CustomerInformation;
import com.huudan.customerportfolio.entity.Customer;
import com.huudan.customerportfolio.exceptions.ApplicationExceptions;
import com.huudan.customerportfolio.mapper.EntityDtoMapper;
import com.huudan.customerportfolio.repository.CustomerRepository;
import com.huudan.customerportfolio.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public CustomerService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return this.customerRepository.findById(customerId)
                                      .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                                      .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformation> buildCustomerInformation(Customer customer) {
        return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
                                           .collectList()
                                           .map(list -> EntityDtoMapper.toCustomerInformation(customer, list));
    }

}
