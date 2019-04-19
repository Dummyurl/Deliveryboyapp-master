package activities.ziffytech.code.activities;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.firebase.messaging.FirebaseMessaging;

import activities.ziffytech.code.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class SplashActivity extends CommonActivity implements BaseSliderView.OnSliderClickListener {
    public static final int  PERMISSION_REQUEST_CODE= 123;
    SliderLayout mDemoSlider;
    private static final int REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        //FirebaseMessaging.getInstance().setAutoInitEnabled(true);

       /* LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
          //  Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
            //jump_to_nextActivity();*/
            permisson();
           //getHash();

      /* }

        else
        {
            showGPSDisabledAlertToUser();
        }*/

    }

   /* private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device, You have to enable it to start the App !!")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, REQUEST_CODE);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }*/

    public void getHash()
    {

        Log.e("HASH","true");

    /*    MessageDigest md = null;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e)
        {
                 e.printStackTrace();
        }
        Log.e("SecretKey = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
*/


        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.ziffytech", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("#####"+"KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("####"+"KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("####"+"KeyHash:", e.toString());
        }


    }


    private void goTomain()
    {

        ImageView splashlogo = (ImageView)findViewById(R.id.splashlogo);
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(splashlogo,"x",320);
        rotationAnimator.setDuration(1000);
        rotationAnimator.start();
        Thread background = new Thread()
        {
            public void run()
            {
                try {

                    sleep(1* 2000);
                    Gotofunctinality();

                } catch (Exception e) {
                    //Toast.makeText(Splash.this,""+e,Toast.LENGTH_SHORT).show();

                }
            }
        };
        background.start();

    }


    private void Gotofunctinality()
    {


        Intent intent = null;
        if(super.common.is_user_login()){
            intent = new Intent(SplashActivity.this,MainActivity.class);
        }else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        if (intent!=null){
            startActivity(intent);
            finish();
        }
    }


    public  void permisson()
    {
        if (!checkPermission())
        {
            requestPermission();
        } else {
            goTomain();
        }
    }


    private boolean checkPermission()
    {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CALENDAR);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CALENDAR);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int result9 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CONTACTS);



        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED &&
                result4== PackageManager.PERMISSION_GRANTED &&
                result5 == PackageManager.PERMISSION_GRANTED&&
                result6 == PackageManager.PERMISSION_GRANTED&&
                result7 == PackageManager.PERMISSION_GRANTED &&
                result8 == PackageManager.PERMISSION_GRANTED &&
                result9 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]
                {
                ACCESS_FINE_LOCATION,
                CAMERA,
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE ,
                READ_CALENDAR,
                WRITE_CALENDAR,
                RECEIVE_SMS,
                READ_SMS,
                SEND_SMS,
                WRITE_CONTACTS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0)
                {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean calRead = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean calWrite = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean receiveSMS = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean readSMS = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean sendSMS = grantResults[8] == PackageManager.PERMISSION_GRANTED;
                    boolean writeContacts = grantResults[9] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted && cameraAccepted && readAccepted && writeAccepted && calRead && calWrite && receiveSMS && readSMS && sendSMS && writeContacts)
                    {

                        goTomain();

                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            showMessageOKCancel();
                        }
                    }
                }
                else
                {
                    myUpdateOperation();
                }


                break;
        }
    }

    private void myUpdateOperation()
    {

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

    private void showMessageOKCancel()
    {
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setMessage("You need to allow access to all the permissions. If permissions not showing go to your app setting and allow permission");
        ad.setCancelable(false);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
                permisson();
            }
        });

        AlertDialog alertDialog=ad.create();
        alertDialog.show();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
