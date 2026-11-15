package ma.emsi.eljazouly.tp2;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

/**
 * Test 3 : un traducteur avec PromptTemplate et Prompt.
 *
 * @author Abdessamad El Jazouly
 */
public class Test3Traducteur {

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

        // Template avec 1 paramètre : le texte à traduire
        PromptTemplate template = PromptTemplate.from("Traduis le texte suivant en anglais : {{texte}}");
        Prompt prompt = template.apply(Map.of("texte",
                "Le TP de LangChain4j à l'EMSI de Casablanca est très intéressant."));
        System.out.println("Prompt : " + prompt.text());
        System.out.println("Réponse : " + model.chat(prompt.text()));
        System.out.println("--------------------------------------------------");

        // Template avec 2 paramètres : la langue et le texte à traduire
        PromptTemplate template2 = PromptTemplate.from(
                "Traduis le texte suivant en {{langue}} : {{texte}}");
        Prompt prompt2 = template2.apply(Map.of(
                "langue", "espagnol",
                "texte", "Demain, j'irai à la plage avec mes amis s'il fait beau."));
        System.out.println("Prompt : " + prompt2.text());
        System.out.println("Réponse : " + model.chat(prompt2.text()));
    }
}
