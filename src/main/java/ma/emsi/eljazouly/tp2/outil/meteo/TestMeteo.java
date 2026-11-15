package ma.emsi.eljazouly.tp2.outil.meteo;

/**
 * Teste la méthode isPluiePrevue de MeteoTool, sans LLM.
 *
 * @author Abdessamad El Jazouly
 */
public class TestMeteo {

    public static void main(String[] args) {
        MeteoTool meteo = new MeteoTool();
        // Paris : latitude 48.85, longitude 2.35
        System.out.println("Pluie à Paris pendant 3 jours à partir d'aujourd'hui : "
                + meteo.isPluiePrevue(48.85, 2.35, 0, 3));
        System.out.println("Pluie à Paris pendant 3 jours à partir de demain : "
                + meteo.isPluiePrevue(48.85, 2.35, 1, 3));
        // Casablanca : latitude 33.57, longitude -7.59
        System.out.println("Pluie à Casablanca pendant 3 jours à partir d'aujourd'hui : "
                + meteo.isPluiePrevue(33.57, -7.59, 0, 3));
    }
}
