package com.huudan.webfluxpatterns.sec04.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huudan.webfluxpatterns.sec04.dto.OrchestrationRequestContext;

public class DebugUtil {

    public static void print(OrchestrationRequestContext ctx){
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
