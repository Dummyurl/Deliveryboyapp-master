package activities.ziffytech.code.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import activities.ziffytech.code.adapter.AdapterOrderlistshowing;
import activities.ziffytech.code.fcm.MyFirebaseRegister;
import activities.ziffytech.code.R;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.model.Todays_order_model;


public class MainActivity extends CommonActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    Bundle bundle;
    Bitmap bitmap;
    ImageView signature;
    ArrayList<Todays_order_model> testArraylist;
    AdapterOrderlistshowing adapterOrderlistshowing;
    RecyclerView recyclerview_todays_order;
    LinearLayoutManager layoutManager;
    private BottomNavigationView mBtmView;
    private int mMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        setHeaderTitle();
        SetupUI();

    }

    private void SetupUI()
    {
        FirebaseApp.initializeApp(this);
        String token= FirebaseInstanceId.getInstance().getToken();
        //Log.e("TOKEN",token);

        if (!common.getSessionBool("fcm_registered") && common.is_user_login()){
            FirebaseApp.initializeApp(MainActivity.this);
            MyFirebaseRegister fireReg = new MyFirebaseRegister(this);
            fireReg.RegisterUser(common.get_user_id());
        }


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("delivery_boy_id", common.get_user_id());
        CustomRequestForString customRequestForString = new CustomRequestForString(Request.Method.POST, ApiParams.GET_TODAY_ORDER_URL, params, this.createRequestSuccessListenerOrderList(), this.createRequestErrorListenerOrderList());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(customRequestForString);
        showPrgressBar();

        recyclerview_todays_order = (RecyclerView)findViewById(R.id.recyclerview_todays_order);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_todays_order.setHasFixedSize(true);
        recyclerview_todays_order.setLayoutManager(layoutManager);



        mBtmView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBtmView.setOnNavigationItemSelectedListener(this);
        mBtmView.getMenu().findItem(R.id.nav_home).setChecked(true);


    }

   /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this,Settings.class));
                return true;

            case R.id.nav_share:
                 shareApp();
                return true;

            case R.id.nav_logout:
                 common.logOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void shareApp()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi friends i am using ." + " http://play.google.com/store/apps/details?id=" + getPackageName() + " APP");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    private Response.Listener<String> createRequestSuccessListenerOrderList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("RESPONSE",response);
                JSONObject userdata = null;
                try {

                    userdata = new JSONObject(response);

                    if(userdata.getInt("status")==1)
                    {
                        JSONArray jsonArray = userdata.getJSONArray("data");

                        testArraylist = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);

                            Todays_order_model model = new Todays_order_model();
                            model.setAddress(obj.getString("address"));
                            model.setDelivery_date(obj.getString("delivery_date"));
                            model.setOrder_id(obj.getString("order_id"));
                            model.setDepature_time(obj.getString("depature_time"));
                            model.setPatient_name(obj.getString("patient_name"));
                            model.setMobile_number(obj.getString("mobile_number"));
                            model.setDelivery_status(obj.getString("order_status"));
                            model.setTotal_amt(obj.getString("total_amt"));
                            model.setDelivery_boy_id(obj.getString("delivery_boy_id"));
                            testArraylist.add(model);
                            Log.e("ARRAYLIST", "" + testArraylist);
                            hideProgressBar();

                        }

                        adapterOrderlistshowing = new AdapterOrderlistshowing(MainActivity.this, testArraylist);
                        recyclerview_todays_order.setAdapter(adapterOrderlistshowing);
                        hideProgressBar();
                    }

                    else {
                        hideProgressBar();
                        Toast.makeText(MainActivity.this, "Admin Not Assigned Any Order To You Today.", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e)
                {
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }

        };

    }


    private Response.ErrorListener createRequestErrorListenerOrderList()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError){
                    MyUtility.showAlertMessage(MainActivity.this,"Server is busy.Please try again");
                }
                Log.i("##", "##" + error.toString());
                hideProgressBar();
            }
        };
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        mMenuId = item.getItemId();
        for (int i = 0; i < mBtmView.getMenu().size(); i++) {
            MenuItem menuItem = mBtmView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }

        switch (item.getItemId())
        {
            case R.id.nav_home:
                return true;

            case R.id.nav_share:
                shareApp();
                return true;

            case R.id.nav_logout:
                startActivity(new Intent(MainActivity.this,Settings.class));
                //common.logOut();
                return true;

        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
