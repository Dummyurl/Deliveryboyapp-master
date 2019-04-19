package activities.ziffytech.code.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.model.Order_Detail_Model;
import activities.ziffytech.code.model.PersonalDetail_Model;

public class PersonalDetail extends CommonActivity
{
    ArrayList<PersonalDetail_Model> personaldetailArraylist,personaldetailArraylist1;
    TextView txtv_name,txtv_email,txtv_contact_name,txtv_contact,txtv_dob,txtv_blood_group,
             txtv_txtPhone,txtv_emer_contact,txtv_aadhar_no,txtv_location;
    ImageView thumbnail_image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        SetupUI();
    }

    private void SetupUI()
    {
        setHeaderTitle("Personal Detail");
        allowBack();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("delivery_boy_id",common.get_user_id());
        CustomRequestForString customRequestForString = new CustomRequestForString(Request.Method.POST, ApiParams.MY_PROFILE_URL, params, this.createRequestSuccessListenerPersonalDetail(), this.createRequestErrorListenerPersonalDetail());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(customRequestForString);
        showPrgressBar();


        txtv_email= (TextView)findViewById(R.id.txtv_email);
        txtv_contact_name= (TextView)findViewById(R.id.txtv_contact_name);
        txtv_contact= (TextView)findViewById(R.id.txtv_contact);
        txtv_dob= (TextView)findViewById(R.id.txtv_dob);
        txtv_blood_group = (TextView)findViewById(R.id.txtv_blood_group);
        txtv_location= (TextView)findViewById(R.id.txtv_location);
        thumbnail_image1 = (ImageView)findViewById(R.id.thumbnail_image1);



    }


    private Response.Listener<String> createRequestSuccessListenerPersonalDetail()
    {
        return new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE",response);
                JSONObject userdata = null;
                try {

                    userdata = new JSONObject(response);

                    if(userdata.getInt("status")==1)
                    {
                        JSONArray jsonArray = userdata.getJSONArray("personal_data");

                        personaldetailArraylist = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++)
                        {

                            JSONObject obj = jsonArray.getJSONObject(i);

                            PersonalDetail_Model model = new PersonalDetail_Model();
                            model.setBoy_experience(obj.getString("address"));
                            model.setDelivery_boy_id(obj.getString("delivery_boy_id"));
                            model.setBoy_name(obj.getString("boy_name"));
                            model.setContact_no(obj.getString("contact_no"));
                            model.setEmail_id(obj.getString("email_id"));
                            model.setPincode(obj.getString("pincode"));
                            model.setAddress(obj.getString("address"));
                            model.setBoy_photo(obj.getString("boy_photo"));
                            model.setBoy_experience(obj.getString("boy_experience"));
                            model.setFcm_token(obj.getString("fcm_token"));


                            txtv_email.setText("Email : "+obj.getString("email_id"));
                            txtv_contact_name.setText("Name : "+obj.getString("boy_name"));
                            txtv_contact.setText("Mob.No. : "+obj.getString("contact_no"));
                            txtv_dob.setText("");
                            txtv_blood_group.setText("");
                            txtv_location.setText("Address : "+obj.getString("address")+"-"+obj.getString("pincode"));

                            Picasso.with(PersonalDetail.this).load(obj.getString("boy_photo")).into(thumbnail_image1);

                            personaldetailArraylist.add(model);
                            Log.e("ARRAYLIST", "" + personaldetailArraylist);
                            hideProgressBar();

                        }


                        hideProgressBar();
                    }

                    else if(userdata.getInt("status")==1)
                    {
                        JSONArray jsonArray1 = userdata.getJSONArray("schedule_data");

                        personaldetailArraylist1 = new ArrayList<>();

                        for (int i = 0; i < jsonArray1.length(); i++)
                        {

                            JSONObject obj1 = jsonArray1.getJSONObject(i);

                            PersonalDetail_Model model1 = new PersonalDetail_Model();
                            model1.setWorking_days(obj1.getString("working_days"));
                            model1.setMorning_time_start(obj1.getString("morning_time_start"));
                            model1.setMorning_time_end(obj1.getString("morning_time_end"));
                            model1.setMorning_tokens(obj1.getString("morning_tokens"));
                            model1.setAfternoon_time_start(obj1.getString("afternoon_time_start"));
                            model1.setAfternoon_time_end(obj1.getString("afternoon_time_end"));
                            model1.setAfternoon_tokens(obj1.getString("afternoon_tokens"));
                            model1.setEvening_time_start(obj1.getString("evening_time_start"));
                            model1.setEvening_time_end(obj1.getString("evening_time_end"));
                            model1.setEvening_tokens(obj1.getString("evening_tokens"));


                            personaldetailArraylist1.add(model1);
                            Log.e("ARRAYLIST", "" + personaldetailArraylist);
                            hideProgressBar();

                        }


                        hideProgressBar();
                    }

                    else {
                        hideProgressBar();
                        Toast.makeText(PersonalDetail.this, "Admin Not Assigned Any Order To You Today.", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e)
                {
                    Toast.makeText(PersonalDetail.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }

        };

    }


    private Response.ErrorListener createRequestErrorListenerPersonalDetail()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError){
                    MyUtility.showAlertMessage(PersonalDetail.this,"Server is busy.Please try again");
                }
                Log.i("##", "##" + error.toString());
                hideProgressBar();
            }
        };
    }
}
