package activities.ziffytech.code.fcm;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import activities.ziffytech.code.activities.LoginActivity;
import activities.ziffytech.code.activities.MainActivity;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;

import java.util.HashMap;


public class MyFirebaseRegister {
    Activity _context;
    CommonClass common;
    public MyFirebaseRegister(Activity context) {
        this._context = context;
        common = new CommonClass(_context);

    }

    public void RegisterUser(String user_id){

        String token = FirebaseInstanceId.getInstance().getToken();
        //FirebaseMessaging.getInstance().subscribeToTopic("doctappo");
        //    Log.e("FCM:",token);
        String str = android.os.Build.MODEL;
        HashMap<String,String> params = new HashMap<>();
        params.put("delivery_boy_id",user_id);
        params.put("fcm_token",token);
        params.put("mobile_model",str);

        VJsonRequest vJsonRequest = new VJsonRequest(_context, ApiParams.FCM_INSERT_URL,params,
                new VJsonRequest.VJsonResponce(){
                    @Override
                    public void VResponce(String responce)
                    {
                        Log.e("RESPONSE LOGIN", responce);

                        JSONObject userdata = null;
                        try {
                            userdata = new JSONObject(responce);
                            if (userdata.getInt("status") == 1) {

                                //Toast.makeText(_context, "Token uploaded", Toast.LENGTH_SHORT).show();

                            } else
                            {
                                //Toast.makeText(_context, "Token Not uploaded", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void VError(String responce) {
//                     Log.e("ERROR",responce);
                    }
                });
    }


}
