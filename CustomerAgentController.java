package com.usecase.agent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.agent.llmenrichment.EnrichedCustomer;
import com.usecase.agent.service.AgentService;


@RestController
@RequestMapping("/agent")
public class CustomerAgentController {

    private final AgentService service;

    public CustomerAgentController(AgentService service) {
        this.service = service;
    }

    @GetMapping("/enrich")
    public EnrichedCustomer enrich(@RequestParam String name) {
        return service.enrich(name);
    }
}
