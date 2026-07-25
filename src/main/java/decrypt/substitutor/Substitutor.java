package decrypt.substitutor;
import java.util.*;
public class Substitutor {
    private final Map<Character, Character> substitutionsToLetter;

    public Substitutor(List<Substitution> substitutions) {
        if (substitutions == null) {
            throw new IllegalArgumentException("Замены не могут быть null");
        }
        substitutionsToLetter = new HashMap<>();
        for (Substitution sub : substitutions) {
            char oldChar = sub.getOldLetter();
            if (substitutionsToLetter.containsKey(oldChar)) {
                throw new IllegalArgumentException("Дубликат среди старых символов: " + oldChar);
            }
            substitutionsToLetter.put(oldChar, sub.getNewLetter());
        }
    }

    public String substitute(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Данные не могут быть null");
        }
        StringBuilder result = new StringBuilder();
        for (char ch : data.toCharArray()) {
            char lowChar = Character.toLowerCase(ch);
            Character replacement = substitutionsToLetter.get(lowChar);

            if (replacement != null) {
                result.append(Character.isUpperCase(ch)
                        ? Character.toUpperCase(replacement)
                        : replacement);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
