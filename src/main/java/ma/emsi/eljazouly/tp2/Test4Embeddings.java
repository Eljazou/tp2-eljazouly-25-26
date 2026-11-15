package ma.emsi.eljazouly.tp2;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

/**
 * Test 4 : similarité de 2 phrases par la métrique du cosinus appliquée à leurs embeddings.
 * Compare un modèle d'embeddings intégré (gratuit, entraîné en anglais)
 * et le modèle d'embeddings externe de Google.
 *
 * @author Abdessamad El Jazouly
 */
public class Test4Embeddings {

    private static final String[][] COUPLES = {
            {"Le chat dort sur le canapé.", "Le félin fait la sieste sur le sofa."},
            {"J'adore la chaleur de l'été.", "Je déteste la chaleur de l'été."},
            {"Il pleut beaucoup à Casablanca aujourd'hui.", "Le cours de Java commence à 9 heures."},
            {"Quelle est la capitale du Maroc ?", "Rabat est la capitale du Maroc."},
            {"Le médecin soigne ses patients à l'hôpital.", "L'infirmière s'occupe des malades à la clinique."},
            {"J'ai acheté une voiture neuve.", "La recette du tajine demande du safran."},
            {"The weather is nice today.", "Il fait beau aujourd'hui."}
    };

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null) {
            System.out.println("La variable d'environnement GEMINI_KEY n'est pas définie.");
            return;
        }

        // Modèle d'embeddings intégré à LangChain4j (ne coûte rien en tokens)
        EmbeddingModel modeleIntegre = new BgeSmallEnV15EmbeddingModel();

        // Modèle d'embeddings externe de Google
        EmbeddingModel modeleGoogle = GoogleGenAiEmbeddingModel.builder()
                .apiKey(cle)
                .modelName("gemini-embedding-001")
                .taskType(GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY)
                // 100 ms ne suffit pas (InterruptedIOException: timeout) ; 5 secondes conviennent.
                .timeout(Duration.ofSeconds(5))
                .build();

        for (String[] couple : COUPLES) {
            System.out.println("Phrase 1 : " + couple[0]);
            System.out.println("Phrase 2 : " + couple[1]);
            System.out.printf("  Similarité (BgeSmallEnV15, intégré) : %.4f%n", similarite(modeleIntegre, couple));
            System.out.printf("  Similarité (gemini-embedding-001)   : %.4f%n", similarite(modeleGoogle, couple));
            System.out.println("--------------------------------------------------");
        }
    }

    private static double similarite(EmbeddingModel modele, String[] couple) {
        Embedding e1 = modele.embed(couple[0]).content();
        Embedding e2 = modele.embed(couple[1]).content();
        return CosineSimilarity.between(e1, e2);
    }
}
