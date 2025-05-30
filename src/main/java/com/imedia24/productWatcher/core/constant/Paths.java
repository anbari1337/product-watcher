package com.imedia24.productWatcher.core.constant;

public class Paths {
    public static final String API_BASE_URL = "/api/v1";

    public static class Auth {
        private static final String PREFIX = API_BASE_URL + "/auth";
        public static final String REGISTER = PREFIX + "/register";
        public static final String LOGIN = PREFIX + "/login";
    }

    public static class Product{
        private static final String PREFIX = API_BASE_URL + "/products";
        public static final String ANALYZE_PRICING = PREFIX + "/analyze-pricing";
    }
}

