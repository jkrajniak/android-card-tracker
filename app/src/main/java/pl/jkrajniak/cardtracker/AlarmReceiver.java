package pl.jkrajniak.cardtracker;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pl.jkrajniak.cardtracker.model.CardRespository;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d("card", "onReceive: BOOT_COMPLETED");
                NotificationScheduler.setReminder(context, AlarmReceiver.class, 9, 0);
                return;
            }
        }
        //Trigger the notification
        CardRespository cardRespository = new CardRespository((Application) context.getApplicationContext());
        cardRespository.showNotifications(context);
    }
}
