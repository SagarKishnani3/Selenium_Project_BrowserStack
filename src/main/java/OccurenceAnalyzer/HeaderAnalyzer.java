package OccurenceAnalyzer;

import java.util.HashMap;
import java.util.List;

public class HeaderAnalyzer {


    // Analyze word frequencies in translated headers

    public static void analyzeTranslatedHeaders(List<String> translatedHeaders) {
        HashMap<String, Integer> wordCount = new HashMap<>();

        for (String header : translatedHeaders) {
            // Split the header into words (ignoring case and punctuation)
            String[] words = header.toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Print words with more than two occurrences
        System.out.println("Repeated words (more than twice):");
        if(!translatedHeaders.isEmpty()){
            int size=0;
            for (String word : wordCount.keySet()) {
                int count = wordCount.get(word);
                if (count >= 2) {
                    System.out.println(word + ": " + count);
                }
                size++;
            }
            if(size==0){
                System.out.println("No repeated words");
            }
        }
    }
}

