package com.example.notificationtesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

import static com.example.notificationtesting.App.CHANNEL_1_ID;
import static com.example.notificationtesting.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
    }

    //channel 1 with sound
    public void sendOnChannel1(View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.kerux);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                //title of the notification
                .setContentTitle(title)
                //message of the notification
                .setContentText(message)
                //Icon
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(getString(R.string.long_text))
                    .setBigContentTitle("Big Content Title")
                    .setSummaryText("Summary Text"))
                //importance level for notification channel
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //when phone is in do not disturb mode
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.RED)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);

    }

    //channel 2 when in do not disturb mode
    public void sendOnChannel2(View v){

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        String title1 = "Title 1";
        String message1 = "Message 1";
        String title2 = "Title 2";
        String message2 = "Message 2";

        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_one)
                //title of the notification
                .setContentTitle(title1)
                //message of the notification
                .setContentText(message1)
                //importance level for notification channel
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .build();

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_one)
                //title of the notification
                .setContentTitle(title2)
                //message of the notification
                .setContentText(message2)
                //importance level for notification channel
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .build();

        Notification summaryNotification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setStyle(new NotificationCompat.InboxStyle()
                    .addLine(title2 + " " + message2)
                    .addLine(title1 + " " + message1)
                    .setBigContentTitle("2 new messages")
                    .setSummaryText("user@example.com"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                .setGroupSummary(true)
                .build();

        SystemClock.sleep(2000);
        notificationManager.notify(2, notification1);
        SystemClock.sleep(2000);
        notificationManager.notify(3, notification2);
        SystemClock.sleep(2000);
        notificationManager.notify(4, summaryNotification);
    }
}