package decrypt.vocabulary;
import java.util.*;

public interface Vocabulary {
    Set<Character> getLetters();
    List<Character> getLettersOrderedByFrequency();
    int getIndexOfLetter(char c);
    char getLetterByIndex(int index);
    default int getTotalLetters() {
        return getLetters().size();
    }
}
