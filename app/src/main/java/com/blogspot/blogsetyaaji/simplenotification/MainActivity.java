package com.blogspot.blogsetyaaji.simplenotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // deklarasi kode request
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    // deklarasi tombol
    Button btnkirim;
    // deklarasi edittext
    EditText txtjudul, txtpesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inisialisasi komponen
        btnkirim = (Button) findViewById(R.id.btnkirim);
        txtjudul = (EditText) findViewById(R.id.txtJudul);
        txtpesan = (EditText) findViewById(R.id.txtpesan);

        // aksi letika tombol diklik
        btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuat komponen Inten
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // memanggil method untuk menampilkan NOTIFICATION_ID
                // dengan mengirimkan data yang dikirim dari komponen EditText
                tampilNotifikasi(txtjudul.getText().toString()
                        , txtpesan.getText().toString(), intent);
            }
        });
    }

    private void tampilNotifikasi(String s, String s1, Intent intent) {
        // membuat komponen pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this
                , NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) MainActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // membuat komponen
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification;
        notification = builder
                .setChannel(NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{100, 200, 100, 200})
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(s)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(MainActivity.this.getResources()
                        , R.mipmap.ic_launcher))
                .setContentText(s1)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
