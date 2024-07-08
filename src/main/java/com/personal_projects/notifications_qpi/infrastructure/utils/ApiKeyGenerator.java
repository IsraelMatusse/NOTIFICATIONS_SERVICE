package com.personal_projects.notifications_qpi.infrastructure.utils;

import java.security.SecureRandom;
import java.time.Instant;

public class ApiKeyGenerator {



    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int KEY_LENGTH = 16;
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateApiKey() {
        StringBuilder apiKey = new StringBuilder(KEY_LENGTH);

        for (int i = 0; i < KEY_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(ALLOWED_CHARACTERS.length());
            apiKey.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        String timestamp = Long.toHexString(Instant.now().toEpochMilli()).toUpperCase();
        return apiKey + timestamp;
    }


}
