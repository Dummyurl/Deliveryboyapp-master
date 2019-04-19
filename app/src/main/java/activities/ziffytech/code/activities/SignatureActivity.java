package activities.ziffytech.code.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import activities.ziffytech.code.commanclass.VolleyMultipartRequest;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;
import activities.ziffytech.code.config.ConstValue;


public class SignatureActivity extends CommonActivity {

    Button mClear, mGetSign, mCancel;
    File file;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;
    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/Ziffytech/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";
    Bundle bundle;
    String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        setHeaderTitle("Patient Signature");
        allowBack();

        bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            orderid = bundle.getString("order_id");
        }
        else
        {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }

        mContent = (LinearLayout) findViewById(R.id.canvasLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mClear = (Button) findViewById(R.id.clear);
        mGetSign = (Button) findViewById(R.id.getsign);
        //mGetSign.setEnabled(false);
        mCancel = (Button) findViewById(R.id.cancel);

        view = mContent;
        mGetSign.setOnClickListener(onButtonClick);
        mClear.setOnClickListener(onButtonClick);
        mCancel.setOnClickListener(onButtonClick);

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == mClear) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            } else if (v == mGetSign) {
                Log.v("log_tag", "Panel Saved");
               // if (Build.VERSION.SDK_INT >= 23) {
               //     isStoragePermissionGranted();
               // } else {
                    view.setDrawingCacheEnabled(true);
                    mSignature.save(view, StoredPath);
                    Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                    // Calling the same class
                    recreate();
               // }
            } else if(v == mCancel){
                Log.v("log_tag", "Panel Canceled");
                // Calling the BillDetailsActivity
                Intent intent = new Intent(SignatureActivity.this, OrderDetail.class);
                //intent.putExtra("Orderid",orderidid);
                startActivity(intent);
                finish();
            }
        }
    };


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            view.setDrawingCacheEnabled(true);
            mSignature.save(view, StoredPath);
            Toast.makeText(getApplicationContext(), "Successfully Saved To SD Card", Toast.LENGTH_SHORT).show();
            // Calling the same class
            recreate();
        }
        else
        {
            Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
        }
    }

    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);

                uploadBitmap(bitmap);

                /*Intent intent = new Intent(SignatureActivity.this, OrderDetail.class);
                intent.putExtra("imagePath", StoredPath);
                startActivity(intent);
                finish();*/

                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
            mGetSign.setEnabled(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }


        private void uploadBitmap(final Bitmap bitmap)
        {


            if(bitmap==null){
                MyUtility.showToast("Select image to upload",this);
                return;
            }

            // showPrgressBar();
            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiParams.UPLOAD_SIGNATURE,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response)
                        {
                            Log.e("RESPONSE", String.valueOf(response));

                                String resultResponse = new String(response.data);
                                try {
                                    JSONObject result = new JSONObject(resultResponse);
                                    String status = result.getString("status");
                                    String message = result.getString("message");


                                if(status.equals("1"))
                                {
                                    //showSuccessDialog();
                                    Intent i = new Intent(SignatureActivity.this,MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                                else
                                {
                                   Toast.makeText(SignatureActivity.this, "Failed Submit Status..Try Again", Toast.LENGTH_SHORT).show();
                                 }
                            } catch (JSONException e) {

                                Toast.makeText(getApplicationContext(),"Failed to Submit form.", Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // hideProgressBar();
                            Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", orderid);
                    params.put("delivery_status", ConstValue.completed);
                    Log.e("PARAMS", params.toString());
                    return params;
                }

                /*
                * Here we are passing image by renaming it with a unique name
                * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("user_signature", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                    Log.e("SIGNATURE",getFileDataFromDrawable(bitmap).toString());

                    return params;
                }
            };

            Volley.newRequestQueue(this).add(volleyMultipartRequest);
        }

    private void showSuccessDialog() {

        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setMessage("Your signature has been added");
        ad.setCancelable(false);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();

            }
        });
        ad.create().show();

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



}
