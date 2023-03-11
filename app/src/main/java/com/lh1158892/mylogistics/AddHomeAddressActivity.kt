package com.lh1158892.mylogistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lh1158892.mylogistics.databinding.ActivityAddHomeAddressBinding

class AddHomeAddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddHomeAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}