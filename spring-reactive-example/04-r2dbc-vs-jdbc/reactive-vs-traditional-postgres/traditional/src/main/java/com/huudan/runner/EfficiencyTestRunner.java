package com.huudan.runner;

import com.huudan.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "efficiency.test", havingValue = "true")
public class EfficiencyTestRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(EfficiencyTestRunner.class);

    private final CustomerRepository repository;

    public EfficiencyTestRunner(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        log.info("starting");
        var list = this.repository.findAll();
        log.info("list size: {}", list.size());
    }

}
