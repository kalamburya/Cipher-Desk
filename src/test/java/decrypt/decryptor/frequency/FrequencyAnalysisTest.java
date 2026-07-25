package decrypt.decryptor.frequency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FrequencyAnalysisTest {

    @Test
    void getNumberOfAppearancesWithCorrectData() {
        FrequencyAnalysisResult result = FrequencyAnalysis.computeCharacterFrequencies("aaabbc");

        assertEquals(3, result.getNumberOfAppearances('a'));
        assertEquals(2, result.getNumberOfAppearances('b'));
        assertEquals(1, result.getNumberOfAppearances('c'));
        assertEquals(0, result.getNumberOfAppearances('z'));
    }

    @Test
    void getNumberOfAppearancesWithDifferentCases() {
        FrequencyAnalysisResult result = FrequencyAnalysis.computeCharacterFrequencies("AaA");

        assertEquals(3, result.getNumberOfAppearances('a'));
    }

    @Test
    void getNumberOfAppearancesWithNullData() {
        assertThrows(IllegalArgumentException.class,
                () -> FrequencyAnalysis.computeCharacterFrequencies(null));
    }
}