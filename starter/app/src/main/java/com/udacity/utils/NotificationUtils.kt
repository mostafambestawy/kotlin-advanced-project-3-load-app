package com.udacity.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.MainActivity
import com.udacity.R


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context,downloadedFileName:String,succeeded:Boolean) {


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


    /** add open details activity action **/
    
    val detailActivityIntent = Intent(applicationContext, DetailActivity::class.java)
    detailActivityIntent.putExtra("downloadedFileName",downloadedFileName)
    detailActivityIntent.putExtra("succeeded", succeeded)
    
    val detailActivityPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(applicationContext, 1, detailActivityIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    

    /** get an instance of NotificationCompat.Builder **/
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        /** use the new 'download' notification channel **/
        applicationContext.getString(R.string.download_notification_channel_id)
    )
        /** set title, text and icon to builder **/
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext.getString(R.string.download_notification_title))
        .setContentText(messageBody)
        /** set content intent **/
        .setContentIntent(contentPendingIntent)
        /** add style to builder **/
        .setStyle(bigPicStyle)
        .setLargeIcon(logoImage)
        /** add details action **/
        .addAction(
            R.drawable.ic_launcher_foreground,
            applicationContext.getString(R.string.details),
            detailActivityPendingIntent
        )
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