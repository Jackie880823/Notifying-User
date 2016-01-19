/*
 * Copyright 2016 The Open Source Project of Jackie Zhu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jackie.notifyingUser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Sets an ID for the notification, so it can be updated
//        int notifyID = 0;
//
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack
//        stackBuilder.addParentStack(ResultActivity.class);
//        // Adds the Intent to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        // Gets a PendingIntent containing the entire back stack
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent resultIntent = new Intent(this, PingService.class);
        resultIntent.setAction(CommonConstants.ACTION_PING);
        resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE, "test ping");
        PendingIntent resultPendingIntent = PendingIntent.getService(this, 0, resultIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Set the small icon to use in the notification layouts.
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // Set the title (first row) of the notification, in a standard notification.
        builder.setContentTitle(getString(R.string.snooze));
        // Set the text (second row) of the notification, in a standard notification.
        builder.setContentText(getString(R.string.done_snoozing));

        builder.setContentIntent(resultPendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get an instance of the NotificationManger service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        notificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }
}
