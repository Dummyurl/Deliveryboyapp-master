package activities.ziffytech.code.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;
import activities.ziffytech.code.activities.MainActivity;


/**
 * Created by subhashsanghani on 5/8/17.
 */

public class NotifyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher, "Notify Alarm strart", System.currentTimeMillis());
        Intent myIntent = new Intent(this , MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, myIntent, 0);

       // mNM.notify(NOTIFICATION, notification);
        super.onCreate();
    }
}
