package activities.ziffytech.code.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.config.ConstValue;

public class PaymentMode_selection extends CommonActivity implements View.OnClickListener {
    Bundle bundle;
    String orderid,amt,boyid;
    RadioGroup radio_payment;
    TextView txtv_submit_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode_selection);

        setupUI();


    }

    private void setupUI()
    {
        bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            orderid = bundle.getString("order_id");
            amt = bundle.getString("total");
            boyid = bundle.getString("boyid");
        }
        else
        {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
        setHeaderTitle("Payment Method");
        allowBack();

        txtv_submit_payment = (TextView)findViewById(R.id.txtv_submit_payment);
        txtv_submit_payment.setOnClickListener(this);
        radio_payment = (RadioGroup)findViewById(R.id.radio_payment);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.txtv_submit_payment)
        {
            int selectedId = radio_payment.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String reason = (String) radioButton.getText();
            //Toast.makeText(CancelRequest.this, reason, Toast.LENGTH_SHORT).show();
            CallApi(reason);
        }

    }

    private void CallApi(String reason)
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("payment_mode", reason);
        params.put("delivery_boy_id",boyid);
        params.put("order_id",orderid);
        params.put("amount",amt);
        params.put("payment_status","Success");
        params.put("reason","");
        params.put("delivery_status",ConstValue.payment_accepted);

        Log.e("PARAMS", params.toString());
        CustomRequestForString customRequestForString = new CustomRequestForString(Request.Method.POST, ApiParams.Payment_URL, params, this.createRequestSuccessListenerPaystatus(), this.createRequestErrorListenerPaystatus());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(customRequestForString);
        showPrgressBar();

    }

    private Response.Listener<String> createRequestSuccessListenerPaystatus()
    {
        return new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                hideProgressBar();

                Log.e("RESPONSE",response);
                JSONObject userdata = null;
                try {
                    userdata = new JSONObject(response);

                    if(userdata.getInt("status")==1)
                    {
                        Intent i = new Intent(PaymentMode_selection.this,MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(PaymentMode_selection.this, "Failed Submit Status..Try Again", Toast.LENGTH_SHORT).show();
                    }

                }catch(JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    MyUtility.showAlertMessage(PaymentMode_selection.this, MyUtility.DATA_ERROR);
                }

            }
        };
    }

    private Response.ErrorListener createRequestErrorListenerPaystatus(){
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
