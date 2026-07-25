package decrypt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import decrypt.decryptor.Decryptor;
import decrypt.decryptor.DecryptorFactory;
import decrypt.decryptor.DecryptorType;
import decrypt.decryptor.Mode;
import decrypt.substitutor.Substitution;
import decrypt.substitutor.Substitutor;
import decrypt.vocabulary.Vocabulary;
import decrypt.vocabulary.VocabularyFactory;
import decrypt.vocabulary.VocabularyType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CipherService {

    private static final Logger log = LoggerFactory.getLogger(CipherService.class);

    public String process(String inputText, VocabularyType vocabularyName,
                          DecryptorType decryptorName, int rotShift, Mode mode) {

        log.debug("Запуск обработки: mode={}, vocabulary={}, decryptor={}, rotShift={}",
                mode, vocabularyName, decryptorName, rotShift);

        Vocabulary vocabulary = VocabularyFactory.create(vocabularyName);
        validateTextMatchesVocabulary(inputText, vocabulary);

        Decryptor decryptor = DecryptorFactory.create(decryptorName, rotShift, vocabulary);

        List<Substitution> substitutions = mode == Mode.ENCRYPT
                ? decryptor.encrypt(inputText)
                : decryptor.decrypt(inputText);

        String result = new Substitutor(substitutions).substitute(inputText);

        log.debug("Обработка завершена, длина результата: {} символов", result.length());

        return result;
    }

    private void validateTextMatchesVocabulary(String inputText, Vocabulary vocabulary) {
        Set<Character> letters = vocabulary.getLetters();
        for (char ch : inputText.toCharArray()) {
            char lower = Character.toLowerCase(ch);
            if (Character.isLetter(lower) && !letters.contains(lower)) {
                throw new IllegalArgumentException(
                        "Текст содержит символы, не входящие в выбранный алфавит: '" + ch + "'"
                );
            }
        }
    }
}