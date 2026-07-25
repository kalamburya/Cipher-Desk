package decrypt.vocabulary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnglishVocabularyTest {

    private final Vocabulary vocabulary = new EnglishVocabulary();

    @Test
    void englishAlphabetHas26Letters() {
        assertEquals(26, vocabulary.getTotalLetters());
    }

    @Test
    void getIndexOfLetterAndGetLetterByIndexAreConsistent() {
        assertEquals(0, vocabulary.getIndexOfLetter('a'));
        assertEquals('a', vocabulary.getLetterByIndex(0));

        assertEquals(25, vocabulary.getIndexOfLetter('z'));
        assertEquals('z', vocabulary.getLetterByIndex(25));
    }

    @Test
    void throwsExceptionWhenGettingIndexOfUppercaseLetter() {
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getIndexOfLetter('A'));
    }

    @Test
    void throwsExceptionWhenGettingIndexOfLetterNotInAlphabet() {
        // русская буква не входит в английский алфавит
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getIndexOfLetter('я'));
    }

    @Test
    void throwsExceptionWhenGettingLetterByIndexOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getLetterByIndex(-1));
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getLetterByIndex(26));
    }

    @Test
    void lettersOrderedByFrequencyStartWithMostFrequentLetterE() {
        assertEquals('e', vocabulary.getLettersOrderedByFrequency().get(0));
    }
}