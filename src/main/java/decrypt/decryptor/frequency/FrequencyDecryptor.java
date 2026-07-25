package decrypt.decryptor.frequency;

import decrypt.decryptor.Decryptor;
import decrypt.substitutor.Substitution;
import decrypt.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrequencyDecryptor implements Decryptor {
    private final Vocabulary vocabulary;

    public FrequencyDecryptor(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Override
    public List<Substitution> decrypt(String data) {
        FrequencyAnalysisResult frequencyAnalysisResult = FrequencyAnalysis.computeCharacterFrequencies(data);
        List<Substitution> substitutions = createSubstitutions(frequencyAnalysisResult);
        return Collections.unmodifiableList(substitutions);
    }

    @Override
    public List<Substitution> encrypt(String data) {
        FrequencyAnalysisResult result = FrequencyAnalysis.computeCharacterFrequencies(data);
        List<Substitution> baseSubstitutions = createSubstitutions(result);

        List<Substitution> inverseSubstitutions = new ArrayList<>();
        for (Substitution sub : baseSubstitutions) {
            inverseSubstitutions.add(new Substitution(sub.getNewLetter(), sub.getOldLetter()));
        }
        return inverseSubstitutions;
    }

    private List<Substitution> createSubstitutions(FrequencyAnalysisResult frequencyAnalysisResult) {
        List<Substitution> substitutions = new ArrayList<>();

        List<Character> lettersInText = new ArrayList<>();
        for (char letter: vocabulary.getLetters()) {
            if (frequencyAnalysisResult.getNumberOfAppearances(letter) > 0) lettersInText.add(letter);
        }

        lettersInText.sort(
                Comparator.comparingInt((Character c) -> frequencyAnalysisResult.getNumberOfAppearances(c))
                        .reversed()
                        .thenComparing(Comparator.naturalOrder())
        );

        List<Character> freqOrder = vocabulary.getLettersOrderedByFrequency();

        for (int i = 0; i < lettersInText.size(); i++) {
            char originalLetter = lettersInText.get(i);
            char replacementLetter = freqOrder.get(i);
            substitutions.add(new Substitution(originalLetter, replacementLetter));
        }

        return substitutions;
    }
}
