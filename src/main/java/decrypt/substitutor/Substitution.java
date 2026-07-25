package decrypt.substitutor;

public class Substitution {
    private final char oldLetter;
    private final char newLetter;

    public Substitution(char oldLetter, char newLetter) {
        if ( !Character.isLowerCase(oldLetter) || !Character.isLowerCase(newLetter) ) {
            throw new IllegalArgumentException("Символы должны быть в нижнем регистре");
        }
        this.oldLetter = oldLetter;
        this.newLetter = newLetter;
    }

    public char getOldLetter() {
        return oldLetter;
    }

    public char getNewLetter() {
        return newLetter;
    }
}
