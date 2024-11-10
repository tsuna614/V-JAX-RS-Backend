package com.example.velocitybackend.utils;

public class GeneralUtil {
    static public String getMessage(String message) {
        return "{\"message\": \"" + message + "\"}";
    }

    static public String getError(String error) {
        return "{\"error\": \"" + error + "\"}";
    }
}
