package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.lh1158892.mylogistics.databinding.ActivityDeliveryBinding


class DeliveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * This will handle the navigation clicks.
         */
        // Update currentPage to the new activity
        var currentPage = this

        // Set the selected item based on the current page
        when (currentPage) {
            is DeliveryActivity -> binding.bottomNavigationView.selectedItemId = R.id.deliveryActivity
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.mainActivity -> {
                    Log.i("Test_Button", "This button was clicked main activity")
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    true // Return true to indicate that the event has been handled
                }
                R.id.parcelActivity -> {
                    startActivity(Intent(applicationContext, ParcelActivity::class.java))
                    true // Return true to indicate that the event has been handled
                }
                R.id.deliveryActivity -> {
                    startActivity(Intent(applicationContext, DeliveryActivity::class.java))
                    true // Return true to indicate that the event has been handled
                }
                R.id.accountSettingActivity -> {
                    startActivity(Intent(applicationContext, AccountSettingActivity::class.java))
                    true // Return true to indicate that the event has been handled
                }
                else -> false // Return false for any other items
            }
        }


    }

}

