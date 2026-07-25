package decrypt.vocabulary;
import java.util.*;
public class EnglishVocabulary implements Vocabulary{
    private final List<Character> alphabet = Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    );

    private final List<Character> frequencyOrder = Arrays.asList(
            'e', 't', 'a', 'o', 'h', 'i', 'n', 's', 'r', 'd', 'l', 'u', 'm',
            'w', 'c', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z'
    );

    public EnglishVocabulary() {}

    @Override
    public Set<Character> getLetters() {
        return new HashSet<>(alphabet);
    }

    @Override
    public List<Character> getLettersOrderedByFrequency() {
        return new ArrayList<>(frequencyOrder);
    }

    @Override
    public int getIndexOfLetter(char c) {
        if (!Character.isLowerCase(c)) {
            throw new IllegalArgumentException("Символ должен быть в нижнем регистре: " + c);
        }
        int index = alphabet.indexOf(c);
        if (index == -1) {
            throw new IllegalArgumentException("Символа нет в алфавите: " + c);
        }
        return index;
    }

    @Override
    public char getLetterByIndex(int index) {
        if (index < 0 || index >= alphabet.size()) {
            throw new IllegalArgumentException("Некорректный индекс: " + index);
        }
        return alphabet.get(index);
    }
}
