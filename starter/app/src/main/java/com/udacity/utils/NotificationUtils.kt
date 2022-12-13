package com.udacity.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.udacity.MainActivity
import com.udacity.R


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


    /**  create intent **/
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val notificationId  = 1

    /**  create PendingIntent **/

    val contentPendingIntent: PendingIntent =
        PendingIntent.getActivity(
            applicationContext,
            notificationId,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )




    /**  add style **/
    val logoImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_launcher_foreground
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(logoImage)
        .bigLargeIcon(null)


    /** add snooze action **/
    /*
    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(applicationContext, REQUEST_CODE, snoozeIntent, FLAGS)
    */

    /** get an instance of NotificationCompat.Builder **/
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        /** use the new 'fcm' notification channel **/
        applicationContext.getString(R.string.download_notification_channel_id)
    )
        /** set title, text and icon to builder **/
        //TODO make small icon more general by passing icon parameter
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext.getString(R.string.download_notification_title))
        .setContentText(messageBody)
        /** set content intent **/
        .setContentIntent(contentPendingIntent)
        /** add style to builder **/
        .setStyle(bigPicStyle)
        .setLargeIcon(logoImage)
        /** add snooze action **/
        /*.addAction(
            R.drawable.ic_launcher_foreground,
            applicationContext.getString(R.string.snooze),
            snoozePendingIntent
        )*/
        /** set priority **/
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    /** call notify **/
    // Deliver the notification
    notify(notificationId, builder.build())
}

/** Cancel all notifications **/
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}