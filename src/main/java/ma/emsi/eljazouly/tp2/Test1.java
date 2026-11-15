package ma.emsi.eljazouly.tp2;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

/**
 * Test 1 : pour se chauffer. Pose quelques questions simples au LLM (Gemini).
 *
 * @author Abdessamad El Jazouly
 */
public class Test1 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .build();

        // Question simple
        poser(model, "Quelle est la capitale du Maroc ? Réponds en une phrase.");

        // L'heure qu'il est
        poser(model, "Quelle heure est-il ?");

        // 2 appels séparés : le LLM se souviendra-t-il de mon nom ?
        poser(model, "Bonjour, je m'appelle Abdessamad El Jazouly");
        poser(model, "Comment je m'appelle ?");
    }

    private static void poser(ChatModel model, String question) {
        String reponse = model.chat(question);
        System.out.println("Question : " + question);
        System.out.println("Réponse : " + reponse);
        System.out.println("--------------------------------------------------");
    }
}
