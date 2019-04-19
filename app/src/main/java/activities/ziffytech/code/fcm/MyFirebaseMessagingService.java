package activities.ziffytech.code.fcm;

/**
 * Created by subhashsanghani on 12/21/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;
import activities.ziffytech.code.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = "MyFirebaseMsgService";
    public static final String broadCastReceiver = "ChatBroadCast";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]

    private NotificationUtils notificationUtils;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("NOTIFICATION",remoteMessage.toString());
        Log.e("notification",remoteMessage.getNotification().getBody());


       /* if (remoteMessage.getData().size() > 0) {

            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                //handleDataMessage(json);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }else{

            Log.e("notificationElse",remoteMessage.getNotification().getBody().toString());

            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("message", remoteMessage.getNotification().getBody());
            showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), resultIntent);
        }*/

        // Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        //resultIntent.putExtra("message", remoteMessage.getNotification().getBody());
        //showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), resultIntent);

    }





    private void showNotificationMessage(Context context, String title, String message, Intent intent)
    {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

}