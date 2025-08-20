package com.college.util;

public class Keys {

    public static String HOME_PATH = "https://myfinalyearproject.in/2023/eRent/eRent/";
    //public static String HOME_PATH = "http://192.168.0.157/cproject/2023/ERent/eRent/";
    public static String TENANT_PATH = HOME_PATH + "user/";
    public static String OWNER_PATH = HOME_PATH + "owner/";
    public static String PHOTO_PATH = HOME_PATH + "photos/";

    public static class Tenant{
        public static String TENANT_REGISTER = TENANT_PATH + "user_register.php";
        public static String TENANT_LOGIN = TENANT_PATH + "user_login.php";
        public static String TENANT_PRODUCT_LIST = TENANT_PATH + "get_product_list.php";
        public static String TENANT_BOOK_PRODUCT = TENANT_PATH + "book_product.php";
        public static String GET_TENANT_BOOKINGS = TENANT_PATH + "get_user_bookings.php";
    }

    public static class Owner{
        public static String OWNER_REGISTER = OWNER_PATH + "owner_register.php";
        public static String OWNER_LOGIN = OWNER_PATH + "owner_login.php";
        public static String OWNER_ADD_PRODUCT = OWNER_PATH + "add_product.php";
        public static String OWNER_PRODUCT_LIST = OWNER_PATH + "get_owner_product_list.php";
        public static String OWNER_NEW_REQUEST = OWNER_PATH + "get_owner_orders.php";
        public static String UPDATE_PRODUCT_STATUS = OWNER_PATH + "update_product_status.php";
    }

}
