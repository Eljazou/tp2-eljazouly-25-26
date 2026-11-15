package ma.emsi.eljazouly.tp2;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.output.TokenUsage;

/**
 * Test 2 : coût en tokens d'une requête.
 *
 * @author Abdessamad El Jazouly
 */
public class Test2Couts {

    // Prix pour gemini-3.5-flash-lite, en dollars par million de tokens.
    private static final double PRIX_ENTREE = 0.30;
    private static final double PRIX_SORTIE = 2.50;

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

        String question = args.length > 0 ? String.join(" ", args)
                : "Quelle est la capitale du Maroc ? Réponds en une phrase.";
        ChatResponse chatResponse = model.chat(UserMessage.from(question));

        System.out.println("Question : " + question);
        System.out.println("Réponse : " + chatResponse.aiMessage().text());

        TokenUsage tokenUsage = chatResponse.tokenUsage();
        int entree = tokenUsage.inputTokenCount();
        int sortie = tokenUsage.outputTokenCount();
        System.out.println("Tokens en entrée : " + entree);
        System.out.println("Tokens en sortie : " + sortie);
        System.out.println("Total des tokens : " + tokenUsage.totalTokenCount());

        // Gemini 3.5 "réfléchit" avant de répondre : les tokens de réflexion ne sont pas comptés
        // dans outputTokenCount() mais ils sont facturés comme des tokens de sortie.
        int reflexion = tokenUsage.totalTokenCount() - entree - sortie;
        System.out.println("Tokens de réflexion (facturés en sortie) : " + reflexion);

        double cout = entree * PRIX_ENTREE / 1_000_000 + (sortie + reflexion) * PRIX_SORTIE / 1_000_000;
        System.out.printf("Coût de la requête avec gemini-3.5-flash-lite : %.8f $%n", cout);
        System.out.printf("Nombre de requêtes similaires pour dépenser 1 $ : %.0f%n", 1 / cout);
    }
}
