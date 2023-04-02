package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.lh1158892.mylogistics.Adapters.ParcelAdapter
import com.lh1158892.mylogistics.Models.Parcel
import com.lh1158892.mylogistics.ViewModels.ParcelViewModel
import com.lh1158892.mylogistics.databinding.ActivityParcelBinding

class ParcelActivity : AppCompatActivity(), ParcelAdapter.ParcelItemListener {
    private lateinit var binding: ActivityParcelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcelBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //This will be used to connect the RecyclerView, adapter and viewModel together
        val viewModel : ParcelViewModel by viewModels()
        viewModel.getParcels().observe(this, {
            binding.parcelRecyclerView.adapter = ParcelAdapter(this,it, this)
        })


        /**
         * This will handle the navigation clicks.
         */

        // Update currentPage to the new activity
        var currentPage = this

        // Set the selected item based on the current page
        when (currentPage) {
            is ParcelActivity -> binding.bottomNavigationView.selectedItemId = R.id.parcelActivity
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



    override fun parcelSelected(parcel: Parcel) {
        Log.i("Parcel_Selected", "$parcel")
        var intent = Intent(this, ParcelDetailActivity::class.java)
        intent.putExtra("documentId", parcel.id)
        startActivity(intent)
    }
}