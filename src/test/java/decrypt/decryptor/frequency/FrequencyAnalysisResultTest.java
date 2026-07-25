package decrypt.decryptor.frequency;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FrequencyAnalysisResultTest {

    @Test
    void constructorWithUppercaseKey() {
        Map<Character, Integer> badMap = Map.of('A', 1);

        assertThrows(IllegalArgumentException.class,
                () -> new FrequencyAnalysisResult(badMap));
    }

    @Test
    void constructorWithNullMap() {
        assertThrows(IllegalArgumentException.class,
                () -> new FrequencyAnalysisResult(null));
    }

    @Test
    void getNumberOfAppearancesWithUppercaseArgument() {
        FrequencyAnalysisResult result = new FrequencyAnalysisResult(Map.of('a', 1));

        assertThrows(IllegalArgumentException.class,
                () -> result.getNumberOfAppearances('A'));
    }
}