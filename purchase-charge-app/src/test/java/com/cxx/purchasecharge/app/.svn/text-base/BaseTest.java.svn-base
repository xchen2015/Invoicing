package com.cxx.purchasecharge.app;

import org.junit.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class BaseTest
{

    @Test
    public void testEncryptor ()
    {
        String salt = KeyGenerators.string ().generateKey ();
        System.out.println (salt);
        StandardPasswordEncoder encoder = new StandardPasswordEncoder ("secret");
        String password = encoder.encode ("a");
        System.out.println (password);
        System.out.println (Encryptors.noOpText ().encrypt ("boss"));
        // System.out.println (Encryptors.queryableText (password, salt).encrypt
        // ("boss"));
        // System.out.println (Encryptors.text (password, salt).encrypt
        // ("boss"));
        // System.out.println (Encryptors.standard (password, salt).encrypt
        // ("boss".getBytes ()));
    }
}
