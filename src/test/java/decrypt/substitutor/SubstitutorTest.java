package decrypt.substitutor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubstitutorTest {
    @Test
    void shouldSubstituteCharactersPreservingCase() {
        List<Substitution> substitutions = List.of(
                new Substitution('a', 'x'),
                new Substitution('b', 'y')
        );
        Substitutor substitutor = new Substitutor(substitutions);

        String result = substitutor.substitute("A - b, a!");

        assertEquals("X - y, x!", result);
    }

    @Test
    void shouldThrowExceptionOnDuplicateOldLetters() {
        List<Substitution> invalidSubstitutions = List.of(
                new Substitution('a', 'x'),
                new Substitution('a', 'z')
        );

        assertThrows(IllegalArgumentException.class,
                () -> new Substitutor(invalidSubstitutions));
    }
}