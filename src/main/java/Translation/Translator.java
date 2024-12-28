package Translation;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


public class Translator {

    // API Key for authentication
    static String apiKey = "API_KEY";

    public static String translateTitle(String Title) {

        Translate translate = TranslateOptions.newBuilder()
                .setApiKey(apiKey).build()
                .getService();

        Translation translation = translate.translate(Title, Translate.TranslateOption.sourceLanguage("es"), Translate.TranslateOption.targetLanguage("en"));

        return translation.getTranslatedText();
    }
}

