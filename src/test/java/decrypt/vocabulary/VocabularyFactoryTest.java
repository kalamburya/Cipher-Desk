package decrypt.vocabulary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VocabularyFactoryTest {
    @Test
    void shouldReturnCorrectVocabularyForEnum() {
        assertInstanceOf(EnglishVocabulary.class, VocabularyFactory.create(VocabularyType.EN));
        assertInstanceOf(RussianVocabulary.class, VocabularyFactory.create(VocabularyType.RU));
    }
}