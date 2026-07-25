package decrypt.decryptor.frequency;

import decrypt.substitutor.Substitution;
import decrypt.vocabulary.EnglishVocabulary;
import decrypt.vocabulary.Vocabulary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyDecryptorTest {

    @Test
    void shouldMapMostFrequentCharToTopFrequencyInVocabulary() {
        Vocabulary vocabulary = new EnglishVocabulary();
        FrequencyDecryptor decryptor = new FrequencyDecryptor(vocabulary);

        String sampleData = "zzzaa";
        List<Substitution> substitutions = decryptor.decrypt(sampleData);

        Substitution firstSub = substitutions.stream()
                .filter(s -> s.getOldLetter() == 'z')
                .findFirst()
                .orElseThrow();

        Substitution secondSub = substitutions.stream()
                .filter(s -> s.getOldLetter() == 'a')
                .findFirst()
                .orElseThrow();

        assertEquals('e', firstSub.getNewLetter());
        assertEquals('t', secondSub.getNewLetter());
    }
}