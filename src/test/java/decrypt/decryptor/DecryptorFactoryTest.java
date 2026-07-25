package decrypt.decryptor;

import decrypt.decryptor.frequency.FrequencyDecryptor;
import decrypt.decryptor.rot.RotNDecryptor;
import decrypt.vocabulary.EnglishVocabulary;
import decrypt.vocabulary.Vocabulary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DecryptorFactoryTest {

    private final Vocabulary vocabulary = new EnglishVocabulary();

    @Test
    void createWithRotTypeReturnsRotNDecryptorInstance() {
        Decryptor decryptor = DecryptorFactory.create(DecryptorType.ROT, 3, vocabulary);

        assertInstanceOf(RotNDecryptor.class, decryptor);
    }

    @Test
    void createWithFreqTypeReturnsFrequencyDecryptorInstance() {
        Decryptor decryptor = DecryptorFactory.create(DecryptorType.FREQ, 0, vocabulary);

        assertInstanceOf(FrequencyDecryptor.class, decryptor);
    }

    @Test
    void createWithNullTypeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> DecryptorFactory.create(null, 3, vocabulary));
    }

    @Test
    void createWithNullVocabularyThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> DecryptorFactory.create(DecryptorType.ROT, 3, null));
    }
}