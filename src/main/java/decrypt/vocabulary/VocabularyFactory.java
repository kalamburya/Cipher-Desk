package decrypt.vocabulary;

public class VocabularyFactory {
    private VocabularyFactory() {}

    public static Vocabulary create(VocabularyType type) {
        return switch (type) {
            case EN -> new EnglishVocabulary();
            case RU -> new RussianVocabulary();
        };
    }
}
