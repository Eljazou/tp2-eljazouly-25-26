package ma.emsi.eljazouly.tp2;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.eljazouly.tp2.outil.meteo.MeteoTool;

import java.util.Scanner;

/**
 * Test 7 : utilisation d'outils (prévisions de pluie et géolocalisation avec Open-Meteo).
 *
 * @author Abdessamad El Jazouly
 */
public class Test7Outils {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                // gemini-3.5-flash : quota gratuit de 20 requêtes par jour dépassé (429 RESOURCE_EXHAUSTED)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                // Logging des requêtes envoyées au LLM et de ses réponses
                .logRequestsAndResponses(true)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20)) // L'utilisation des outils génère des messages
                        .tools(new MeteoTool())  // Ajout de l'outil
                        .build();

        conversationAvec(assistant);
    }

    private static void conversationAvec(AssistantMeteo assistant) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                System.out.println("Question : " + question);
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}
