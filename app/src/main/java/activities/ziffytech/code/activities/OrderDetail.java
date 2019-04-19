package activities.ziffytech.code.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import activities.ziffytech.code.R;
import activities.ziffytech.code.adapter.AdapterOrderlistshowing;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.config.ConstValue;
import activities.ziffytech.code.model.Order_Detail_Model;
import activities.ziffytech.code.model.Todays_order_model;
import activities.ziffytech.code.commanclass.MyUtility;

public class OrderDetail extends CommonActivity implements View.OnClickListener {
    TextView txtv_order_id,txtv_date,txtv_address,txtv_mobile,txtv_payment_mode,txtv_total_discount;
    TextView txtv_delivery_chrg,txtv_coupen_code,txtv_wallet_amt,txtv_amt_delivery;
    Todays_order_model model;
    Bundle bundle;
    String orderid,user_phone,user_map,toatl_amt_to_pass,del_id,OrderStatus;
    ArrayList<Order_Detail_Model> orderdetailArraylist;
    ImageView imgv_rating,imgv_submit,imgv_sign,imgv_cancel,imgv_map,imgv_call;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

        bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            orderid = bundle.getString("order_id");
            user_phone = bundle.getString("mobile_no");
            user_map = bundle.getString("address");
            OrderStatus = bundle.getString("OrderStatus");

            Toast.makeText(this, ""+OrderStatus, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
        SetupUI();

    }

    private void SetupUI()
    {
        setHeaderTitle("Order Details");
        allowBack();





        model = new Todays_order_model();
        txtv_order_id = (TextView)findViewById(R.id.txtv_order_id);
        txtv_date  = (TextView)findViewById(R.id.txtv_date);
        txtv_address  = (TextView)findViewById(R.id.txtv_address);
        txtv_mobile  = (TextView)findViewById(R.id.txtv_mobile);
        txtv_payment_mode  = (TextView)findViewById(R.id.txtv_payment_mode);
        txtv_total_discount  = (TextView)findViewById(R.id.txtv_total_discount);
        txtv_delivery_chrg =  (TextView)findViewById(R.id.txtv_delivery_chrg);
        txtv_coupen_code  = (TextView)findViewById(R.id.txtv_coupen_code);
        txtv_wallet_amt  = (TextView)findViewById(R.id.txtv_wallet_amt);
        txtv_amt_delivery  = (TextView)findViewById(R.id.txtv_amt_delivery);

        imgv_rating = (ImageView)findViewById(R.id.imgv_rating);
        imgv_rating.setOnClickListener(this);
        imgv_submit = (ImageView)findViewById(R.id.imgv_submit);
        imgv_submit.setOnClickListener(this);
        imgv_sign = (ImageView)findViewById(R.id.imgv_sign);
        imgv_sign.setOnClickListener(this);
        imgv_cancel = (ImageView)findViewById(R.id.imgv_cancel);
        imgv_cancel.setOnClickListener(this);
        imgv_map = (ImageView)findViewById(R.id.imgv_map);
        imgv_map.setOnClickListener(this);
        imgv_call = (ImageView)findViewById(R.id.imgv_call);
        imgv_call.setOnClickListener(this);

        SetUpMenu(OrderStatus);


        


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("order_id",orderid);
        //Log.e("Params",orderid);
        CustomRequestForString customRequestForString = new CustomRequestForString(Request.Method.POST, ApiParams.GET_ORDER_DETAIL_URL, params, this.createRequestSuccessListenerOrderDetail(), this.createRequestErrorListenerOrderDetail());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(customRequestForString);
        showPrgressBar();


         /*ImageView signature = (ImageView)findViewById(R.id.sig_imageview);
               bundle = getIntent().getExtras();
               if (bundle!=null) {
               if(bundle.containsKey("imagePath"))
                   {
                        String image_path= bundle.getString("imagePath");
                        bitmap = BitmapFactory.decodeFile(image_path);
                        signature.setImageBitmap(bitmap);

                       //Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                       //Toast.makeText(this, ""+getStringImage(bitmap), Toast.LENGTH_SHORT).show();
                    }
        }
        else
        {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }*/



    }

    private void SetUpMenu(String OrderStatus)
    {
        if(OrderStatus != null && OrderStatus.equalsIgnoreCase("Delivered"))
        {
            imgv_cancel.setVisibility(View.GONE);
            imgv_sign.setVisibility(View.GONE);
            imgv_submit.setVisibility(View.GONE);
            imgv_rating.setVisibility(View.VISIBLE);
            imgv_map.setVisibility(View.VISIBLE);
            imgv_call.setVisibility(View.VISIBLE);
            imgv_rating.setBackgroundResource(R.drawable.ic_rating);
            imgv_map.setBackgroundResource(R.drawable.ic_explore_tool);
            imgv_call.setBackgroundResource(R.drawable.ic_call);
            int width = 80;
            int height = 80;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            parms.setMargins(10,5,15,2);

            imgv_rating.setLayoutParams(parms);
            imgv_map.setLayoutParams(parms);
            imgv_call.setLayoutParams(parms);
            imgv_cancel.setLayoutParams(parms);
            imgv_sign.setLayoutParams(parms);
            imgv_submit.setLayoutParams(parms);
        }
            else if(OrderStatus != null && OrderStatus.equalsIgnoreCase("Cancel"))
            {
                imgv_cancel.setVisibility(View.GONE);
                imgv_sign.setVisibility(View.GONE);
                imgv_submit.setVisibility(View.GONE);
                imgv_rating.setVisibility(View.VISIBLE);
                imgv_map.setVisibility(View.VISIBLE);
                imgv_call.setVisibility(View.VISIBLE);
                imgv_rating.setBackgroundResource(R.drawable.ic_rating);
                imgv_map.setBackgroundResource(R.drawable.ic_explore_tool);
                imgv_call.setBackgroundResource(R.drawable.ic_call);

                int width = 80;
                int height = 80;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                parms.setMargins(10,5,15,2);

                imgv_rating.setLayoutParams(parms);
                imgv_map.setLayoutParams(parms);
                imgv_call.setLayoutParams(parms);
                imgv_cancel.setLayoutParams(parms);
                imgv_sign.setLayoutParams(parms);
                imgv_submit.setLayoutParams(parms);

            }
                else if(OrderStatus != null && OrderStatus.equalsIgnoreCase("completed"))
                {
                    imgv_cancel.setVisibility(View.GONE);
                    imgv_sign.setVisibility(View.GONE);
                    imgv_submit.setVisibility(View.GONE);
                    imgv_rating.setVisibility(View.VISIBLE);
                    imgv_map.setVisibility(View.VISIBLE);
                    imgv_call.setVisibility(View.VISIBLE);
                    imgv_rating.setBackgroundResource(R.drawable.ic_rating);
                    imgv_map.setBackgroundResource(R.drawable.ic_explore_tool);
                    imgv_call.setBackgroundResource(R.drawable.ic_call);

                    int width = 80;
                    int height = 80;
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                    parms.setMargins(10,5,15,2);

                    imgv_rating.setLayoutParams(parms);
                    imgv_map.setLayoutParams(parms);
                    imgv_call.setLayoutParams(parms);
                    imgv_cancel.setLayoutParams(parms);
                    imgv_sign.setLayoutParams(parms);
                    imgv_submit.setLayoutParams(parms);

                }

        else
        {
            imgv_cancel.setVisibility(View.VISIBLE);
            imgv_sign.setVisibility(View.VISIBLE);
            imgv_submit.setVisibility(View.VISIBLE);

            imgv_rating.setVisibility(View.VISIBLE);
            imgv_map.setVisibility(View.VISIBLE);
            imgv_call.setVisibility(View.VISIBLE);
            imgv_rating.setBackgroundResource(R.drawable.ic_rating);
            imgv_map.setBackgroundResource(R.drawable.ic_explore_tool);
            imgv_call.setBackgroundResource(R.drawable.ic_call);
            imgv_cancel.setBackgroundResource(R.drawable.ic_remove);
            imgv_sign.setBackgroundResource(R.drawable.ic_pen);
            imgv_submit.setBackgroundResource(R.drawable.ic_correct);

            int width = 80;
            int height = 80;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            parms.setMargins(10,5,15,2);

            imgv_rating.setLayoutParams(parms);
            imgv_map.setLayoutParams(parms);
            imgv_call.setLayoutParams(parms);
            imgv_cancel.setLayoutParams(parms);
            imgv_sign.setLayoutParams(parms);
            imgv_submit.setLayoutParams(parms);

        }


    }

    private Response.Listener<String> createRequestSuccessListenerOrderDetail() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("RESPONSE",response);
                JSONObject userdata = null;
                try {

                    userdata = new JSONObject(response);

                    if(userdata.getInt("status")==1)
                    {
                        JSONArray jsonArray = userdata.getJSONArray("info");

                        orderdetailArraylist = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);

                            Order_Detail_Model model = new Order_Detail_Model();


                            model.setPrescription_id(obj.getString("prescription_id"));
                            model.setOrder_id(obj.getString("order_id"));
                            model.setAddress(obj.getString("address"));
                            model.setDelivery_boy_id(obj.getString("delivery_boy_id"));
                            del_id = obj.getString("delivery_boy_id").toString();
                            model.setDepature_time(obj.getString("depature_time"));
                            model.setDelivery_date(obj.getString("delivery_date"));
                            model.setDelivery_status(obj.getString("delivery_status"));

                            model.setReason(obj.getString("reason"));
                            model.setUser_signature(obj.getString("user_signature"));

                            orderdetailArraylist.add(model);
                            Log.e("ARRAYLIST", "" + orderdetailArraylist);
                            hideProgressBar();
                            txtv_order_id.setText(obj.getString("order_id"));
                            txtv_date.setText(obj.getString("delivery_date"));
                            txtv_address.setText(obj.getString("address"));
                            txtv_mobile.setText("Not Given");
                            txtv_payment_mode.setText("COD");
                            txtv_total_discount.setText("0.0 "+ ConstValue.CURRENCY);
                            txtv_delivery_chrg.setText("0.0 "+ ConstValue.CURRENCY);
                            txtv_coupen_code.setText("Not Applicable");
                            txtv_wallet_amt.setText("0.0 "+ ConstValue.CURRENCY);


                        }


                        if(userdata.getInt("status")==1)
                        {
                            JSONArray jsonArray1 = userdata.getJSONArray("prescription");
                            orderdetailArraylist = new ArrayList<>();

                            for (int i = 0; i < jsonArray1.length(); i++) {

                                JSONObject obj = jsonArray1.getJSONObject(i);

                                Order_Detail_Model model1 = new Order_Detail_Model();
                                model1.setMedicine_name(obj.getString("medicine_name"));
                                model1.setStrength(obj.getString("strength"));
                                model1.setQuantity(obj.getString("quantity"));
                                model1.setSelling_price(obj.getString("selling_price"));
                                model1.setAmount(obj.getString("amount"));
                                txtv_amt_delivery.setText(obj.getString("amount")+" "+ConstValue.CURRENCY);
                                toatl_amt_to_pass = obj.getString("amount").toString();
                            }
                        }


                        hideProgressBar();
                    }

                    else {
                        hideProgressBar();
                        Toast.makeText(OrderDetail.this, "Admin Not Assigned Any Order To You Today.", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e)
                {
                    Toast.makeText(OrderDetail.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }

        };

    }




    private Response.ErrorListener createRequestErrorListenerOrderDetail()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError){
                    MyUtility.showAlertMessage(OrderDetail.this,"Server is busy.Please try again");
                }
                Log.i("##", "##" + error.toString());
                hideProgressBar();
            }
        };
    }


    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.imgv_rating)
        {
            Intent i = new Intent(OrderDetail.this,Feedback.class);
            startActivity(i);

        }
        if(v.getId()==R.id.imgv_submit)
        {
            Intent i = new Intent(OrderDetail.this,PaymentMode_selection.class);
            i.putExtra("order_id", orderid);
            i.putExtra("total",toatl_amt_to_pass);
            i.putExtra("boyid",del_id);
            startActivity(i);

        }
        if(v.getId()==R.id.imgv_sign)
        {
            Intent i = new Intent(OrderDetail.this,SignatureActivity.class);
            i.putExtra("order_id", orderid);
            startActivity(i);

        }
        if(v.getId()==R.id.imgv_cancel)
        {
            Intent i = new Intent(OrderDetail.this,CancelRequest.class);
            i.putExtra("order_id", orderid);
            startActivity(i);
        }
        if(v.getId()==R.id.imgv_map)
        {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        if(v.getId()==R.id.imgv_call)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",user_phone, null));
            startActivity(intent);
        }

    }
}
