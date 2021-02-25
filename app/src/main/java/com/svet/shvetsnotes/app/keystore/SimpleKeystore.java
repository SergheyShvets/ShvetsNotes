package com.svet.shvetsnotes.app.keystore;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpleKeystore implements Keystore {
    private Context context;

    private final String SAVE_HASH = "hash";
    private final String NEW_SALT = "salt";
    private SharedPreferences sharedHash;
    ;
    private static byte[] salt = new byte[16];


    public SimpleKeystore(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasPin() {
        sharedHash = context.getSharedPreferences(SAVE_HASH, context.MODE_PRIVATE);
        String s = sharedHash.getString(SAVE_HASH, "");
        if (sharedHash.contains(SAVE_HASH)) {
            return true;
        } else {
            return false;

        }
    }

    @Override
    public boolean checkPin(String pin) {
        String saltString = context.getSharedPreferences(NEW_SALT, context.MODE_PRIVATE).getString(NEW_SALT, "");
        String[] saltStringArray = saltString.split(";");

        for (int i = 0; i < saltStringArray.length; i++) {
            salt[i] = Byte.parseByte(saltStringArray[i]);
        }
        String pinHash = HashUtils.getSecurePassword(pin, salt);
        sharedHash = context.getSharedPreferences(SAVE_HASH, context.MODE_PRIVATE);
        String passwordHash = sharedHash.getString(SAVE_HASH, "");
        if (pinHash.equals(passwordHash)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveNew(String pin) {
        salt = HashUtils.getSalt();
        String saltStringForSave = "";
        for (int i = 0; i < salt.length; i++) {
            saltStringForSave += salt[i] + ";";
        }
        saveSharedPreference(NEW_SALT, saltStringForSave);
        String hashOfNewPasswordString = HashUtils.getSecurePassword(pin, salt);
        saveSharedPreference(SAVE_HASH, hashOfNewPasswordString);
    }

    private void saveSharedPreference(String name, String information) {
        sharedHash = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedHash.edit();
        ed.putString(name, information);
        ed.commit();
    }
}
