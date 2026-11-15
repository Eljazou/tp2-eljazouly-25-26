package ma.emsi.eljazouly.tp2;

/**
 * Assistant IA pour le test 7 ; son implémentation est fournie par LangChain4j (AiServices).
 *
 * @author Abdessamad El Jazouly
 */
public interface AssistantMeteo {

    // Prend un message de l'utilisateur et retourne une réponse du LLM.
    String chat(String userMessage);
}
