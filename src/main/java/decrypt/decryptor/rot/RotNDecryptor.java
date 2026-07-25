package decrypt.decryptor.rot;

import decrypt.decryptor.Decryptor;
import decrypt.substitutor.Substitution;
import decrypt.vocabulary.Vocabulary;
import java.util.*;

public class RotNDecryptor implements Decryptor {

    private final int shift;
    private final Vocabulary vocabulary;

    public RotNDecryptor(int shift, Vocabulary vocabulary) {
        if (vocabulary == null) {
            throw new IllegalArgumentException("Vocabulary не может быть null");
        }
        this.shift = shift;
        this.vocabulary = vocabulary;
    }

    @Override
    public List<Substitution> decrypt(String data) { return shift(-1); }

    @Override
    public List<Substitution> encrypt(String data) { return shift(+1); }

    private List<Substitution> shift(int direction) {
        List<Substitution> substitutions = new ArrayList<>();
        int total = vocabulary.getTotalLetters();
        for (char letter : vocabulary.getLetters()) {
            int oldIndex = vocabulary.getIndexOfLetter(letter);
            int newIndex = Math.floorMod(oldIndex + direction * shift, total);
            substitutions.add(new Substitution(letter, vocabulary.getLetterByIndex(newIndex)));
        }
        return substitutions;
    }
}
