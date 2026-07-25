package decrypt.decryptor;

import decrypt.decryptor.frequency.FrequencyDecryptor;
import decrypt.decryptor.rot.RotNDecryptor;
import decrypt.vocabulary.Vocabulary;

public class DecryptorFactory {

    private DecryptorFactory() {}

    public static Decryptor create(DecryptorType type, int rotShift, Vocabulary vocabulary) {

        if (type == null) {
            throw new IllegalArgumentException("Тип дешифратора не может быть null");
        }
        if (vocabulary == null) {
            throw new IllegalArgumentException("Алфавит не может быть null");
        }

        return switch (type) {
            case FREQ -> new FrequencyDecryptor(vocabulary);
            case ROT -> new RotNDecryptor(rotShift, vocabulary);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип дешифратора: " + type);
        };
    }
}
