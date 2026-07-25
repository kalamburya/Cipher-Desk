package decrypt.controller;

import decrypt.model.HistoryRecord;
import decrypt.decryptor.Mode;
import decrypt.decryptor.DecryptorType;
import decrypt.vocabulary.VocabularyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DecryptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDoProcessEncryptionAndSaveHistory() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/process")
                        .session(session)
                        .param("inputText", "abc")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "3")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", "def"));

        @SuppressWarnings("unchecked")
        List<HistoryRecord> history = (List<HistoryRecord>) session.getAttribute("history");

        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals("abc", history.get(0).inputText());
        assertEquals("def", history.get(0).outputText());
    }

    @Test
    void shouldReturnHomePageWithDefaultSettings() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("inputText", ""))
                .andExpect(model().attribute("vocabularyName", VocabularyType.EN))
                .andExpect(model().attribute("decryptorName", DecryptorType.ROT))
                .andExpect(model().attribute("rotShift", 0))
                .andExpect(model().attribute("mode", Mode.DECRYPT))
                .andExpect(model().attributeExists("history"));
    }

    @Test
    void shouldPreserveRequestParametersOnHomePage() throws Exception {
        mockMvc.perform(get("/")
                        .param("inputText", "hello")
                        .param("vocabularyName", "RU")
                        .param("decryptorName", "FREQ")
                        .param("rotShift", "5")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("inputText", "hello"))
                .andExpect(model().attribute("vocabularyName", VocabularyType.RU))
                .andExpect(model().attribute("decryptorName", DecryptorType.FREQ))
                .andExpect(model().attribute("rotShift", 5))
                .andExpect(model().attribute("mode", Mode.ENCRYPT));
    }

    @Test
    void shouldProcessEmptyString() throws Exception {
        mockMvc.perform(post("/process")
                        .param("inputText", "")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "3")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", ""));
    }

    @Test
    void shouldProcessRotShiftGreaterThanAlphabetSize() throws Exception {
        mockMvc.perform(post("/process")
                        .param("inputText", "abc")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "30")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", "efg")); // 30 % 26 = 4 -> abc → efg
    }

    @Test
    void shouldProcessNegativeRotShift() throws Exception {
        mockMvc.perform(post("/process")
                        .param("inputText", "abc")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "-3")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", "xyz")); // -3 ≡ 23 → abc → xyz
    }

    @Test
    void shouldProcessNegativeRotShiftDecrypt() throws Exception {
        mockMvc.perform(post("/process")
                        .param("inputText", "xyz")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "-3")
                        .param("mode", "DECRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", "abc"));
    }

    @Test
    void shouldHandleForeignAlphabetError() throws Exception {
        mockMvc.perform(post("/process")
                        .param("inputText", "Привет")
                        .param("vocabularyName", "EN")
                        .param("decryptorName", "ROT")
                        .param("rotShift", "3")
                        .param("mode", "ENCRYPT"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void shouldClearHistory() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("history", List.of(new HistoryRecord("a", "b", Mode.ENCRYPT, DecryptorType.ROT, 3, VocabularyType.EN, null)));

        mockMvc.perform(delete("/clear-history").session(session))
                .andExpect(status().isOk());

        @SuppressWarnings("unchecked")
        List<HistoryRecord> history = (List<HistoryRecord>) session.getAttribute("history");
        assertNotNull(history);
        assertTrue(history.isEmpty());
    }
}