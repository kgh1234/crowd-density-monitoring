package com.example.crowd;

public final class ApiConfig {

    private ApiConfig() {
        // Prevent instantiation
    }

    public static final String BASE_URL =
            "http://crowd.dothome.co.kr/";

    public static final String JOIN_URL =
            BASE_URL + "join.php";

    public static final String LOGIN_URL =
            BASE_URL + "login.php";

    public static final String VALIDATE_URL =
            BASE_URL + "uservalidate.php";
}