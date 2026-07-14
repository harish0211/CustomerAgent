package com.usecase.agent.tools;



public class CustomerTool {

    public CustomerData fetchCustomer(String name) {
        // Simulated microservice call
        return new CustomerData(name, name.toLowerCase() + "@example.com", "Plano");
    }


public record CustomerData(String name, String email, String city) {}
}