package com.usecase.agent.llmenrichment;

public record EnrichedCustomer(
	    String name,
	    String email,
	    String city,
	    String profileSummary
	) {}