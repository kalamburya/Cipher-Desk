package decrypt.model;

import decrypt.decryptor.DecryptorType;
import decrypt.decryptor.Mode;
import decrypt.vocabulary.VocabularyType;

import java.time.LocalDateTime;

public record HistoryRecord (
    String inputText,
    String outputText,
    Mode mode,
    DecryptorType method,
    int rotShift,
    VocabularyType vocabulary,
    LocalDateTime timestamp
)
{}
