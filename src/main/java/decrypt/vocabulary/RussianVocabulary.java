package decrypt.vocabulary;
import java.util.*;
public class RussianVocabulary implements Vocabulary {
    private final List<Character> alphabet = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л',
            'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш',
            'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'
    );

    private final List<Character> frequencyOrder = Arrays.asList(
            'о', 'е', 'а', 'н', 'и', 'т', 'с', 'л', 'в', 'р', 'к', 'м', 'у',
            'д', 'п', 'ы', 'я', 'б', 'г', 'з', 'ч', 'ь', 'й', 'х', 'ж', 'ш',
            'ю', 'ц', 'э', 'щ', 'ф', 'ъ', 'ё'
    );

    public RussianVocabulary() {}

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
