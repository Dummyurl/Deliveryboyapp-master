package activities.ziffytech.code.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by admn on 15/11/2017.
 */

public class ForgotPassword extends CommonActivity {
    EditText editPhone;
    private static final int MY_SOCKET_TIMEOUT_MS = 2000;


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        setHeaderTitle("Forgot Password");
        allowBack();
        editPhone = (EditText) findViewById(R.id.edt_forgotmobile);
        LinearLayout button = (LinearLayout) findViewById(R.id.lin_new_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent=new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(intent);*/
                forgetPassword();
            }
        });
    }

    private void forgetPassword() {
        String phone = editPhone.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Phone number required");
            focusView = editPhone;
            cancel = true;
        }

        if (phone.length() < 10) {
            editPhone.setError(getString(R.string.valid_required_phone));
            focusView = editPhone;
            focusView.requestFocus();
            return;
        }

        if (!MyUtility.isConnected(this)) {

            MyUtility.showAlertMessage(this, MyUtility.INTERNET_ERROR);
            return;
        }


        showPrgressBar();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", phone);
        Log.e("PARAMS", params.toString());
        CustomRequestForString customRequestForString = new CustomRequestForString(Request.Method.POST, ApiParams.FORGOT_PASSWORD_URL, params, this.createRequestSuccessListenerPassword(), this.createRequestErrorListenerPassword());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(customRequestForString);
        customRequestForString.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private Response.Listener<String> createRequestSuccessListenerPassword() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressBar();

                Log.e("RESPONSE",response);
                JSONObject userdata = null;
                try {
                    userdata = new JSONObject(response);

                    if(userdata.getInt("status")==1) {
                        //MyUtility.showAlertMessage(ForgotPassword.this,userdata.getString("message"));
                        ForgotPassword.showAlertMessage(ForgotPassword.this,"We have sent SMS on your registered mobile number.\n please check your password in SMS. ");
                    }
                    else if(userdata.getInt("status")==0){
                        Toast.makeText(ForgotPassword.this, "Error In Sending SMS..Try Again Later", Toast.LENGTH_SHORT).show();
                    }else{
                        ForgotPassword.showAlertMessage(ForgotPassword.this,"Provided Mobile Number Not Registered With Us.");
                    }
                }catch(JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    MyUtility.showAlertMessage(ForgotPassword.this, MyUtility.DATA_ERROR);
                }

            }
        };
    }

    private Response.ErrorListener createRequestErrorListenerPassword() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Log.i("##", "##" + error.toString());
                //App.showAlert("Something Went Wrong, Please Try again",MultiTestSearchActivity.this);

            }
        };
    }


    public static void showAlertMessage(final Context context, String msg){

        AlertDialog.Builder ad=new AlertDialog.Builder(context);
        ad.setMessage(msg);

        ad.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                dialogInterface.dismiss();
                ((Activity)context).finish();
            }
        });
        AlertDialog dialog=ad.create();
        dialog.show();
    }


}
