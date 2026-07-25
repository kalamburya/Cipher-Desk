package decrypt.decryptor;

import decrypt.substitutor.Substitution;

import java.util.List;

public interface Decryptor {
    List<Substitution> decrypt(String data);
    List<Substitution> encrypt(String data);
}
