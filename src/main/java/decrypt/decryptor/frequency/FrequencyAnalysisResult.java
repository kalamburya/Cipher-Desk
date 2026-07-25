package decrypt.decryptor.frequency;

import java.util.*;

public class FrequencyAnalysisResult {
    private final Map<Character, Integer> charToFrequency;

    public FrequencyAnalysisResult(Map<Character, Integer> charToFrequency) {
        if (charToFrequency == null) {
            throw new IllegalArgumentException("Хэш-таблица не может быть пустой");
        }
        for (Character c : charToFrequency.keySet()) {
            checkLowerCase(c);
        }
        this.charToFrequency = new HashMap<>(charToFrequency);
    }

    public int getNumberOfAppearances(char c) {
        checkLowerCase(c);
        return charToFrequency.getOrDefault(c, 0);
    }

    private void checkLowerCase(char c) {
        if (Character.isLetter(c) && !Character.isLowerCase(c)) {
            throw new IllegalArgumentException("Символ должен быть в нижнем регистре: " + c);
        }
    }
}
