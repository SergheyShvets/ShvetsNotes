package com.svet.shvetsnotes.app.keystore;

import java.security.NoSuchAlgorithmException;

public interface Keystore {
    boolean hasPin();

    boolean checkPin(String pin);

    void saveNew(String pin) throws NoSuchAlgorithmException;
}

