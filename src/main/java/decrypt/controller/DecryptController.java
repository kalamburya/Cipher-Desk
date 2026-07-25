package decrypt.controller;

import decrypt.decryptor.DecryptorType;
import decrypt.decryptor.Mode;
import decrypt.model.HistoryRecord;
import decrypt.service.CipherService;
import decrypt.service.HistoryService;
import decrypt.vocabulary.VocabularyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DecryptController {

    private static final Logger log = LoggerFactory.getLogger(DecryptController.class);

    private final CipherService cipherService;
    private final HistoryService historyService;

    public DecryptController(CipherService cipherService, HistoryService historyService) {
        this.cipherService = cipherService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String inputText,
            @RequestParam(required = false) VocabularyType vocabularyName,
            @RequestParam(required = false) DecryptorType decryptorName,
            @RequestParam(required = false, defaultValue = "0") Integer rotShift,
            @RequestParam(required = false) Mode mode,
            HttpSession session,
            Model model
    ) {
        model.addAttribute("inputText", inputText != null ? inputText : "");
        model.addAttribute("vocabularyName", vocabularyName != null ? vocabularyName : VocabularyType.EN);
        model.addAttribute("decryptorName", decryptorName != null ? decryptorName : DecryptorType.ROT);
        model.addAttribute("rotShift", rotShift != null ? rotShift : 0);
        model.addAttribute("mode", mode != null ? mode : Mode.DECRYPT);
        model.addAttribute("history", historyService.get(session));

        return "index";
    }

    @PostMapping("/process")
    public String process(
            @RequestParam String inputText,
            @RequestParam VocabularyType vocabularyName,
            @RequestParam DecryptorType decryptorName,
            @RequestParam(required = false, defaultValue = "0") int rotShift,
            @RequestParam Mode mode,
            HttpSession session,
            Model model
    ) {
        try {
            String result = cipherService.process(inputText, vocabularyName, decryptorName, rotShift, mode);
            historyService.add(session, new HistoryRecord(
                    inputText, result, mode, decryptorName, rotShift, vocabularyName, LocalDateTime.now()));
            model.addAttribute("result", result);
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка обработки запроса: {}", e.getMessage());
            model.addAttribute("error", "Ошибка: " + e.getMessage());
        }

        populateRequestEcho(model, inputText, mode, vocabularyName, decryptorName, rotShift);
        model.addAttribute("history", historyService.get(session));

        return "index";
    }

    @DeleteMapping("/clear-history")
    @ResponseBody
    public ResponseEntity<Void> clearHistory(HttpSession session) {
        historyService.clear(session);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedError(Exception e, HttpSession session, Model model) {
        log.error("Непредвиденная ошибка при обработке запроса", e);

        model.addAttribute("error", "Внутренняя ошибка сервера. Попробуйте позже.");
        model.addAttribute("history", historyService.get(session));
        model.addAttribute("inputText", "");
        model.addAttribute("mode", Mode.DECRYPT);
        model.addAttribute("vocabularyName", VocabularyType.EN);
        model.addAttribute("decryptorName", DecryptorType.ROT);
        model.addAttribute("rotShift", 3);

        return "index";
    }

    private void populateRequestEcho(Model model, String inputText, Mode mode,
                                     VocabularyType vocabularyName, DecryptorType decryptorName, int rotShift) {
        model.addAttribute("inputText", inputText);
        model.addAttribute("mode", mode);
        model.addAttribute("vocabularyName", vocabularyName);
        model.addAttribute("decryptorName", decryptorName);
        model.addAttribute("rotShift", rotShift);
    }
}