package decrypt.decryptor.frequency;
import java.util.*;

public class FrequencyAnalysis {
    private FrequencyAnalysis() {}

    public static FrequencyAnalysisResult computeCharacterFrequencies(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Data не может быть null");
        }
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            c = Character.toLowerCase(c);
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
            } else {
                frequencies.put(c, 1);
            }
        }
        return new FrequencyAnalysisResult(frequencies);
    }
}
