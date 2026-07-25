package decrypt.decryptor.rot;

import decrypt.substitutor.Substitution;
import decrypt.substitutor.Substitutor;
import decrypt.vocabulary.EnglishVocabulary;
import decrypt.vocabulary.Vocabulary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RotNDecryptorTest {
    private Vocabulary vocabulary;

    @BeforeEach
    void setUp() {
        vocabulary = new EnglishVocabulary();
    }

    @Test
    void encryptionWithShift3ShiftsLettersForwardAndWrapsAroundAlphabet() {
        RotNDecryptor decryptor = new RotNDecryptor(3, vocabulary);

        List<Substitution> substitutions = decryptor.encrypt("az");

        char newA = getReplacement(substitutions, 'a');
        char newZ = getReplacement(substitutions, 'z');

        assertEquals('d', newA);
        assertEquals('c', newZ);
    }

    @Test
    void decryptionWithShift3ShiftsLettersBackwardAndWrapsAroundAlphabet() {
        RotNDecryptor decryptor = new RotNDecryptor(3, vocabulary);

        List<Substitution> substitutions = decryptor.decrypt("dc");

        char oldD = getReplacement(substitutions, 'd');
        char oldC = getReplacement(substitutions, 'c');

        assertEquals('a', oldD);
        assertEquals('z', oldC);
    }

    @Test
    void encryptionAndDecryptionAreInverseOperations() {
        RotNDecryptor rot = new RotNDecryptor(5, vocabulary);
        String original = "hello world";

        Substitutor encryptor = new Substitutor(rot.encrypt(original));
        String encrypted = encryptor.substitute(original);

        Substitutor decryptor = new Substitutor(rot.decrypt(original));
        String decrypted = decryptor.substitute(encrypted);

        assertEquals(original, decrypted);
    }

    @Test
    void shiftGreaterThanAlphabetSizeWorksAsShiftModuloAlphabetSize() {
        RotNDecryptor rot30 = new RotNDecryptor(30, vocabulary);
        RotNDecryptor rot4 = new RotNDecryptor(4, vocabulary);

        assertEquals(
                getReplacement(rot4.encrypt("x"), 'a'),
                getReplacement(rot30.encrypt("x"), 'a')
        );
    }

    private char getReplacement(List<Substitution> substitutions, char target) {
        return substitutions.stream()
                .filter(s -> s.getOldLetter() == target)
                .findFirst()
                .orElseThrow()
                .getNewLetter();
    }
}