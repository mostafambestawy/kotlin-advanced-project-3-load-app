package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.udacity.databinding.ContentDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var detailActivityViewModel: DetailActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        detailActivityViewModel = ViewModelProvider(this)[DetailActivityViewModel::class.java]

        val binding:ContentDetailBinding = ContentDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.detailsActivityViewModel = detailActivityViewModel


        detailActivityViewModel.updateFileName(intent.getStringExtra("downloadedFileName")!!)


        if(intent.getBooleanExtra("succeeded",false))
        {
            detailActivityViewModel.updateStatusString(getString(R.string.success))
        }
        else detailActivityViewModel.updateStatusString(getString(R.string.failed))

        binding.okButton.setOnClickListener{
            finish()
        }
        setContentView(binding.root)

    }

}
