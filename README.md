Request Lifecycle
1.	User Request
 A GET request is sent to /agent/enrich with the query parameter name=Harish.
2.	REST Controller
	The CustomerAgentController receives the request.
	It delegates to the CustomerAgentService.
3.	Agent Service
	Orchestrates the workflow.
	Calls CustomerTools to fetch base customer data.
	Builds a Prompt with the data and sends it to the LLM via OpenAiChatModel.
4.	OpenAiChatModel
	The LLM enriches the customer data with a profile summary.
	Returns a structured response.
5.	Guardrails
	Validate that the JSON output contains required fields (name, email, city, profileSummary).
	If validation fails, throw an exception.
6.	Human-in-the-Loop
	Output is marked "Pending Approval" so a human must confirm before finalizing.
7.	Structured JSON Response
	Returned to the user as JSON.

plaintext
 ┌───────────────────┐
 │   User Request    │
 │ /agent/enrich     │
 └─────────┬─────────┘
           │
           ▼
 ┌───────────────────┐
 │ REST Controller   │
 └─────────┬─────────┘
           │
           ▼
 ┌───────────────────┐
 │ Agent Service     │
 │ Orchestration     │
 └─────────┬─────────┘
           │
   ┌───────┴─────────┐
   │                 │
   ▼                 ▼
┌─────────────────┐ ┌───────────────────┐
│ CustomerTools   │ │ OpenAiChatModel   │
│ fetchCustomer() │ │ LLM Enrichment    │
└─────────┬───────┘ └─────────┬─────────┘
          │                   │
          ▼                   ▼
 ┌───────────────────┐   ┌───────────────────┐
 │ Base Data         │   │ Profile Summary   │
 └─────────┬─────────┘   └─────────┬─────────┘
           │                       │
           ▼                       ▼
 ┌───────────────────┐   ┌───────────────────┐
 │ Guardrails        │   │ Human-in-the-Loop │
 │ Validate JSON     │   │ Pending Approval  │
 └─────────┬─────────┘   └─────────┬─────────┘
           │                       │
           ▼                       ▼
         ┌───────────────────────────┐
         │ Structured JSON Response   │
         └───────────────────────────┘
