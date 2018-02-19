package com.example.usman.terminalapplication;
/**
 * Created by Usman on 6/6/2017.
 */
public class AndyConstants {
    // web service url constants
    public class ServiceType {
        public static final String BASE_URL = "http://tollapi.000webhostapp.com/";
        public static final String LOGIN = BASE_URL + "op-login.php";
        public static final String REGISTER =  BASE_URL + "op-register.php";

    }
    // webservice key constants
    public class Params {

        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String ADDRESS = "address";
        public static final String PHONE = "phone";
        public static final String CNIC = "cnic";
        public static final String PLAZAID = "plazaid";
        public static final String BOOTHID = "boothid";
        public static final String ACCOUNT = "account";
    }
}