package decrypt.vocabulary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RussianVocabularyTest {

    private final Vocabulary vocabulary = new RussianVocabulary();

    @Test
    void russianAlphabetHas33Letters() {
        assertEquals(33, vocabulary.getTotalLetters());
    }

    @Test
    void indexAndLetterMappingAreConsistent() {
        assertEquals(0, vocabulary.getIndexOfLetter('а'));
        assertEquals('а', vocabulary.getLetterByIndex(0));

        assertEquals(32, vocabulary.getIndexOfLetter('я'));
        assertEquals('я', vocabulary.getLetterByIndex(32));
    }

    @Test
    void throwsExceptionWhenGettingIndexOfEnglishLetter() {
        // английская буква не входит в русский алфавит
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getIndexOfLetter('a'));
    }

    @Test
    void throwsExceptionWhenGettingLetterByIndexOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getLetterByIndex(-1));
        assertThrows(IllegalArgumentException.class, () -> vocabulary.getLetterByIndex(33));
    }
}