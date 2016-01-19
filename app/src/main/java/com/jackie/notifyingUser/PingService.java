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

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class PingService extends IntentService {
    private static final String TAG = "PingService";
    private NotificationManager mNotificationManager;

    public PingService() {
        super("PingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String action = intent.getAction();

        // This section handles the 3 possible actions:
        // ping, snooze and dismiss.
        if (CommonConstants.ACTION_PING.equals(action)) {
            // The reminder message the user set.
            mNotificationManager.cancel(CommonConstants.NOTIFICATION_ID);
            String msg = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
            issueNotification(msg);
        } else if (CommonConstants.ACTION_SNOOZE.equals(action)) {
            mNotificationManager.cancel(CommonConstants.NOTIFICATION_ID);
            Log.d(TAG, "onHandleIntent: " + getString(R.string.snoozing));

            // Sets a snooze-specific "done snoozing" message.
            issueNotification(getString(R.string.done_snoozing));
        } else if (CommonConstants.ACTION_DISMISS.equals(action)) {
            mNotificationManager.cancel(CommonConstants.NOTIFICATION_ID);
        }
    }

    private void issueNotification(String msg) {
        // Sets up the Snooze and Dismiss action buttons that will appear in the
        // expanded view of the notification.
        Intent dismissIntent = new Intent(this, PingService.class);
        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, PingService.class);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setContentTitle(getString(R.string.notification));
        builder.setContentText(getString(R.string.ping));
        builder.setDefaults(Notification.DEFAULT_ALL); // requires VIBRATE permission;

        /*
         * Sets the big view "big text" style and supplies the text (the user's reminder message)
         * that will be displayed in the detail area of the expanded notification.
         * these calls are ignored by the support library for pre-5.1 devices.
         */
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
        builder.addAction(R.drawable.ic_stat_dismiss, getString(R.string.dismiss), piDismiss);
        builder.addAction(R.drawable.ic_stat_snooze, getString(R.string.snooze), piSnooze);

        /*
         * Clicking the notification itself displays ResultActivity, which provides UI for snoozing
         * or dismissing the notification.
         * This is available through either the normal view or big view.
         */
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE, msg);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Because clicking the notification launches a new ("special") activity,
        // there's no need to create an artificial back stack.
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // this sets the pending intent that should be fired when the user clicks the
        // notification. Clicking the notification launches a new activity.
        builder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }

}
