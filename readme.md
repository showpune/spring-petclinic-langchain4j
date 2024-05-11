# Spring PetClinic Sample Application With OpenAI and Langchain4j

## Understanding the Spring Petclinic application with a few diagrams

[See the presentation here](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Run Petclinic locally

Spring Petclinic is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built
using [Maven](https://spring.io/guides/gs/maven/). You can build a jar
file and run it from the command line (it should work just as well with Java 17 or newer), Before you build the application, you need to setup some openai properties in `src/main/resources/application.properties`:
```bash
git clone https://github.com/showpune/spring-petclinic-langchain4j.git
cd spring-petclinic-langchain4j
mv src/main/resources/application.properties.example src/main/resources/application.properties
```

edit the `src/main/resources/application.properties` file and add the following properties:
```properties
langchain4j.azure.open-ai.chat-model.endpoint=https://*****.openai.azure.com/
langchain4j.azure.open-ai.chat-model.deployment-name=gpt-4
langchain4j.azure.open-ai.chat-model.api-key=**
```

You can build the application by running the following command:
```bash
./mvnw package
java -jar target/*.jar
```

You can then access the Petclinic at <http://localhost:8080/>
<img width="1042" alt="petclinic-screenshot" src="https://github.com/showpune/spring-petclinic-langchain4j/assets/1787505/52878caa-8bdd-48c4-a2e7-193f68054c3e">

And the OpenAI chatbot at <http://localhost:8080/chat.html>.
<img width="1042" alt="petclinic-screenshot" src="https://github.com/showpune/spring-petclinic-langchain4j/assets/1787505/11caef70-6411-4e72-9ae9-4902fb8ac96b">

## Workthrough of the chat agent
You can talk with the agent, it can help to recommend the vet according to the symptoms of the pet. The agent can also help to book an appointment with the vet.
![image](https://github.com/showpune/spring-petclinic-langchain4j/assets/1787505/e158ca83-0ada-4f8c-8843-6055b9cb017f)
Go to the owner page, you can see you are registered as an owner with the help of the agent
![image](https://github.com/showpune/spring-petclinic-langchain4j/assets/1787505/e7da4ede-5405-437d-a35f-fcd60af45ba7)

### Prompt
The prompt is defined in [agent](https://github.com/showpune/spring-petclinic-langchain4j/blob/master/src/main/java/org/springframework/samples/petclinic/chat/Agent.java)

## License

The Spring PetClinic sample application is released under version 2.0 of
the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
