package decrypt.substitutor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SubstitutionTest {

    @Test
    void throwsExceptionWhenOldLetterIsUppercase() {
        assertThrows(IllegalArgumentException.class,
                () -> new Substitution('A', 'b'));
    }

    @Test
    void throwsExceptionWhenNewLetterIsUppercase() {
        assertThrows(IllegalArgumentException.class,
                () -> new Substitution('a', 'B'));
    }
}