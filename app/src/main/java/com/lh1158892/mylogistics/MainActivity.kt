package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lh1158892.mylogistics.Adapters.ParcelAdapter
import com.lh1158892.mylogistics.ViewModels.ParcelViewModel
import com.lh1158892.mylogistics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var currentActivity = 0;


        val viewModel : ParcelViewModel by viewModels()
        viewModel.getParcels().observe(this, {
            binding.parcelRecyclerViewMain.adapter = ParcelAdapter(this,it)
        })

        // Parcel View All Button
        binding.viewAllParcelButton.setOnClickListener {
            val intent = Intent(this, ParcelActivity::class.java)
            startActivity(intent)
        }

        // Deliveries View All Button
        binding.viewAllDeliveriesButton.setOnClickListener {
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }

        // Handle Navigation Actions for the Activities
        var navigationHandler = binding.bottomNavigationView
        navigationHandler.touchables.forEachIndexed { index, view ->
            run {
                if (index != currentActivity) {
                    view.clearFocus()
                    view.findFocus()
                }
                view.setOnClickListener {
                    when (index) {
                        0 -> {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(this, ParcelActivity::class.java)
                            startActivity(intent)
                        }
                        2 -> {
                            val intent = Intent(this, DeliveryActivity::class.java)
                            startActivity(intent)
                        }
                        3 -> {
                            val intent = Intent(this, AccountSettingActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }



    }
}