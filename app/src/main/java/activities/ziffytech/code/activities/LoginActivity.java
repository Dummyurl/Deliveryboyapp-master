package activities.ziffytech.code.activities;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nineoldandroids.animation.ObjectAnimator;

import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.prefs.Preferences;


public class LoginActivity extends CommonActivity implements View.OnClickListener {
    private static final int REQ_CODE = 9001;
    EditText editEmail, editPassword;
    RelativeLayout layout;
    ImageView logo;

    private ObjectAnimator lftToRgt,rgtToLft,objectanimator;
    private ImageView imageView;
    private float halfW;

    private AnimatorSet animatorSet;//required to set the sequence

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        setUpViews();


    }

    private void setUpViews() {



        /*Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        final int width = point.x; // screen width
        halfW = width/2.0f; // half the width or to any value required,global to class
        anim();*/


        getSupportActionBar().hide();
        //hideKeyboard(layout, LoginActivity.this);
        setHeaderTitle(getString(R.string.login_now));

        editEmail = (EditText) findViewById(R.id.edt_login_username);
        editPassword = (EditText) findViewById(R.id.edt_login_password);
        RelativeLayout button = (RelativeLayout) findViewById(R.id.rel_signin_btn);

        TextView forgot = (TextView) findViewById(R.id.txtv_forgot_password);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });



        logo = (ImageView)findViewById(R.id.logo);
        objectanimator = ObjectAnimator.ofFloat(logo,"x",700);
        objectanimator.setDuration(3000);
        objectanimator.start();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if (!email.equals("")) {

                    if (isValidEmail(email)) {
                        if (!password.equals("")) {

                            login(email, password);

                        } else {

                            editPassword.setError("Password Required");
                        }

                    } else {
                        editEmail.setError("Please enter valid email");
                    }

                } else {
                    editEmail.setError("Email Required");

                }


                //login();
            }
        });


    }

    void anim(){
        // translationX to move object along x axis
        // next values are position value
        lftToRgt = ObjectAnimator.ofFloat(logo,"translationX",0f,halfW )
                .setDuration(700); // to animate left to right
        rgtToLft = ObjectAnimator.ofFloat( logo,"translationX",halfW,0f )
                .setDuration(700); // to animate right to left
        animatorSet = new AnimatorSet();
        //animatorSet.play(lftToRgt).before( rgtToLft ); // manage sequence
        //animatorSet.playTogether(lftToRgt,rgtToLft);
        //animatorSet.play(lftToRgt);
        animatorSet.start(); // play the animation
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {



        }
    }




    public void login(String email, String password) {

        showPrgressBar();
        HashMap<String, String> params = new HashMap<>();
        params.put("useremail", email);
        params.put("password", password);

        VJsonRequest vJsonRequest = new VJsonRequest(this, ApiParams.LOGIN_URL, params,
                new VJsonRequest.VJsonResponce()
                {
                    @Override
                    public void VResponce(String responce)
                    {

                        Log.e("RESPONSE LOGIN", responce);

                        hideProgressBar();

                        JSONObject userdata = null;
                        try {
                            userdata = new JSONObject(responce);
                            if (userdata.getInt("status") == 1) {

                                JSONObject data = userdata.getJSONArray("data").getJSONObject(0);

                                common.setSession(ApiParams.COMMON_KEY, data.getString("delivery_boy_id"));
                                common.setSession(ApiParams.USER_EMAIL, data.getString("email_id"));
                                common.setSession(ApiParams.USER_FULLNAME, data.getString("boy_name"));

                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                            } else {
                                MyUtility.showAlertMessage(LoginActivity.this, "Invalid Credentials");
                            }

                        } catch (JSONException e) {
                            hideProgressBar();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void VError(String responce) {

                        hideProgressBar();
                   //     Log.e("ERROR", responce);
                    }


                });

    }


    private Response.ErrorListener createRequestErrorListenerFB() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("##", "##" + error.toString());
                hideProgressBar();
            }
        };
    }


}
