package org.springframework.samples.petclinic.chat;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.internal.ValidationUtils.ensureNotNull;

@Configuration
@EnableConfigurationProperties(OpenAIProperties.class)
public class AgentConfig {

	/**
	 * Configure a bean of type Agent
	 * @param chatLanguageModel: handles the chat interactions
	 * @param chatMemoryProvider: manages chat history or memory, allowing to maintain context across interactions.
	 * @param retrievalAugmentor: enhances the chat model by integrating external information retrieval capabilities.
	 * @param VetTools and OwnerTools: custom tools designed to handle specific tasks.
	 */
	@Bean
	Agent configurePetclinicChatAgent(ChatLanguageModel chatLanguageModel, ChatMemoryProvider chatMemoryProvider,
			RetrievalAugmentor retrievalAugmentor, VetTools VetTools, OwnerTools OwnerTools) {
		return AiServices.builder(Agent.class)
			.chatLanguageModel(chatLanguageModel)
			.tools(VetTools, OwnerTools)
			.chatMemoryProvider(chatMemoryProvider)
			.retrievalAugmentor(retrievalAugmentor)
			.build();
	}

	/**
	 * Configure a bean of type AzureOpenAiChatModel (implements ChatLanguageModel)
	 * @param properties: application properties
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(OpenAIProperties.PREFIX + ".chat-model.client-id")
	AzureOpenAiChatModel openAiChatModel(OpenAIProperties properties) {
		ChatModelProperties chatModelProperties = properties.getChatModel();
		return AzureOpenAiChatModel.builder()
			.endpoint(chatModelProperties.getEndpoint())
			.tokenCredential(
				new DefaultAzureCredentialBuilder()
					.managedIdentityClientId(chatModelProperties.getClientId())
					.build())
			.deploymentName(chatModelProperties.getDeploymentName())
			.build();
	}

	/**
	 * Configure a bean of type RetrievalAugmentor.
	 * @param chatLanguageModel: the chat model to be augmented with external content retrieval.
	 * @param contentRetriever: responsible for fetching contextually relevant responses.
	 * @return
	 */
	@Bean
	RetrievalAugmentor retrievalAugmentor(ChatLanguageModel chatLanguageModel, ContentRetriever contentRetriever) {
		String expandString = ExpandingQueryTransformer.DEFAULT_PROMPT_TEMPLATE.template()
				+ "\n All must returned by English";
		ExpandingQueryTransformer expandingQueryTransformer = ExpandingQueryTransformer.builder()
			.chatLanguageModel(chatLanguageModel)
			.promptTemplate(PromptTemplate.from(expandString))
			.build();
		return DefaultRetrievalAugmentor.builder()
			.contentRetriever(ensureNotNull(contentRetriever, "contentRetriever"))
			.queryTransformer(expandingQueryTransformer)
			.build();
	}

}
