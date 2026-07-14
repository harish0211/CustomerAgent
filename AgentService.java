package com.usecase.agent.service;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import com.usecase.agent.llmenrichment.EnrichedCustomer;
import com.usecase.agent.tools.CustomerTool;

@Service
public class AgentService {

    private final OpenAiChatModel chatModel;
    private final CustomerTool tools = new CustomerTool();

    public AgentService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public EnrichedCustomer enrich(String name) {
        // Step 1: Fetch base data
        var customer = tools.fetchCustomer(name);

        // Step 2: Build prompt for enrichment
        String promptText = """
            You are an enrichment agent.
            Base data: { "name": "%s", "email": "%s", "city": "%s" }
            Enrich with a profile summary.
            Return JSON with fields: name, email, city, profileSummary.
            """.formatted(customer.name(), customer.email(), customer.city());

        Prompt prompt = new Prompt(promptText);

        // Step 3: Call LLM
        ChatResponse response = chatModel.call(prompt);

        // Step 4: Extract structured output
     //   String output = response.getResult().getOutputText();
        
        String output = response.getResults()
                .get(0)                // first generation
                .getOutput()           // ChatOutput object
                .getText();

        // Step 5: Guardrails (basic validation)
        if (!output.contains("profileSummary")) {
            throw new IllegalStateException("Invalid enrichment output");
        }

        // Step 6: Human-in-the-loop
        return new EnrichedCustomer(
            customer.name(),
            customer.email(),
            customer.city(),
            "Pending Approval: " + output
        );
    }
}
