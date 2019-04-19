package activities.ziffytech.code.config;

/*
 * Created by  on 10/18/16.
 * All end points for api is given here....
 *
 * Please find api code in  application/controllers/api.php  file here.. all function releated end points is given..
 * /index.php/api/login   mean  public function login() is for login.
 */

// 9527239199

public class ApiParams
{

    // https://www.ziffytech.com/ZiffyDummy/admin//index.php/Api/my_appointments
//https://www.ziffytech.com/ZiffyDummy/admin/index.php/Api/login

    public static String PARM_RESPONCE = "responce";
    public static String PARM_DATA = "data";
    public static String PARM_ERROR = "error";
    public static String API_CONTROLLER = "ziffyboy";

    public  static String PREF_NAME = "hairkut.pref";
    public static String PREF_CATEGORY = "pref_category";
    public static String COMMON_KEY = "user_id";
    //public static String PRICE_CART = "price_cart";
    public static String PREF_ERROR = "error_stack";
    public static String USER_DATA = "user_data";

    public static String USER_SALUTATION = "salutation";
    public static String USER_FULLNAME = "user_fullname";
    public static String USER_LOCATION = "location";
    public static String USER_EMAIL = "user_email";
    public static String USER_PHONE = "user_phone";
    public static String USER_DOB = "dob";
    public static String USER_BLOOD_GROUP = "blood_group";
    public static String USER_GENDER = "gender";
    public static String USER_MARITAL_STATUS = "marital_status";
    public static String USER_HEIGHT = "height";
    public static String USER_WEIGHT = "weight";
    public static String USER_EMERGENCY_CONTACT = "emergency_contact";
    public static String PINCODE = "pin_code";
    public static String ADDRESS = "Address";
    public static String CITY = "User_city";
    public static String Time = "time";
    public static String SOCIAL_ID = "Social_id";
    public static String SOCIAL_TYPE = "Social_Type";
    public static String IMAGE = "Imager";
    public static String Name = "Name";
    public static String CONTACT = "contact";
    public static String BIRTHDAY = "birthday";
    public static String WEIGHT = "WEIGHT";
    public static String HEIGHT = "HEIGHT";
    public static String GENDER = "GENDER";
    public static String MARITAL_STATUS = "MARITAL_STATUS";
    public static String EMERGENCY_CONTACT = "EMERGENCY_CONTACT";
    public static String LOCALITY = "MARITAL_STATUS";
    public static String BLOOD_GROUP = "BLOOD_GROUP";
    public static String ALERGIES = "ALERGIES";
    public static String SURGERY = "SURGERY";
    public static String C_MEDICATION = "C_MEDICATION";
    public static String P_MEDICATION = "P_MEDICATION";
    public static String INJURIES = "INJURIES";
    public static String CHRONIC = "CHRONIC";
    public static String CURRENT_CITY = "city";
    public static String USER_JSON_DATA = "user_data";
    public static String ZIFFY_WALLET_AMT="ziffy_wallet_amt";
    public static String CHAT_WITH = "chat_with";
    public static String GET_PHARMACY = "CHRONIC";
    public static String PHARMA_NOTIFICATION = "";
    public static String LAB_NOTIFICATION = "";

    public static String LOGIN_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/signin";
    public static String FORGOT_PASSWORD_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/forgot_password";
    public static String GET_TODAY_ORDER_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/get_all_order";
    public static String ACCEPT_ORDER_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/change_order_status";
    public static String FCM_INSERT_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/register_fcm";
    public static String GET_ORDER_DETAIL_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/order_details";
    public static String MY_PROFILE_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/my_profile";
    public static String CANCEL_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/cancal_order";
    public static String Payment_URL = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/payment";
    public static String UPLOAD_SIGNATURE = ConstValue.BASE_PHARMACY+API_CONTROLLER+"/submit_order";


    /*https://www.ziffytech.com/admin/Api/get_emergency_number";*/
}
//https://www.ziffytech.com/ZiffyDummy/admin/Api/get_emergency_number