package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.udacity.databinding.ContentMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val binding: ContentMainBinding = ContentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainActivityViewModel = mainActivityViewModel

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        /*binding.customButton.setOnClickListener(View.OnClickListener {
            if(!(binding.url1RadioButton.isChecked&&binding.url2RadioButton.isChecked&&binding.url3RadioButton.isChecked))
            {
                Toast.makeText(this,getString(R.string.choose_one_repo),Toast.LENGTH_LONG).show()
            }
            else {
                download()
            }
        })*/
        custom_button.setOnClickListener {
            if (mainActivityViewModel.selectedUrlNo == null) {

                Toast.makeText(this, getString(R.string.choose_one_repo), Toast.LENGTH_LONG).show()

            } else {
                custom_button.buttonState = ButtonState.Loading
                download()
            }
        }

        url1RadioButton.setOnCheckedChangeListener { _, isChecked ->
            mainActivityViewModel.setUrl1(
                isChecked
            )
        }
        url2RadioButton.setOnCheckedChangeListener { _, isChecked ->
            mainActivityViewModel.setUrl2(
                isChecked
            )
        }
        url3RadioButton.setOnCheckedChangeListener { _, isChecked ->
            mainActivityViewModel.setUrl3(
                isChecked
            )
        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val intentAction:String = intent?.action!!
            if(intentAction.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                custom_button.buttonState = ButtonState.Completed
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                pushNotification(id!!)
            }
        }
    }

    private fun pushNotification(id: Long) {

    }

    private fun download() {

        val request =
            DownloadManager.Request(Uri.parse(getSelectedURL()))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request) // enqueue puts the download request in the queue.
    }
    private fun getSelectedURL():String{
        return when(mainActivityViewModel.selectedUrlNo){
            1 -> getString(R.string.url1)
            2 -> getString(R.string.url2)
            3 -> getString(R.string.url3)
            else -> ""
        }
    }

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

}
