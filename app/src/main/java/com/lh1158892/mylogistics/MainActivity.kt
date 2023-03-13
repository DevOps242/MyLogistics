package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lh1158892.mylogistics.Adapters.ParcelAdapter
import com.lh1158892.mylogistics.Models.Parcel
import com.lh1158892.mylogistics.ViewModels.ParcelViewModel
import com.lh1158892.mylogistics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ParcelAdapter.ParcelItemListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var currentActivity = 0;

        //create a list of Projects to populate the Project Spinner
        var parcels : MutableList<Parcel> = ArrayList()

        // This currently gets all.
//        val viewModel : ParcelViewModel by viewModels()
//        viewModel.getParcels().observe(this, {
//            binding.parcelRecyclerViewMain.adapter = ParcelAdapter(this,it)
//        })


        val viewModel : ParcelViewModel by viewModels()
        viewModel.getParcels().observe(this) {
            parcels.addAll(it)
            parcels?.let {
                for (parcel in parcels) {
                    if (parcel.location == "In transit" || parcel.location == "Warehouse") {
                        binding.parcelRecyclerViewMain.adapter = ParcelAdapter(this, it, this)
                    }
                }
            }

        }

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

    override fun parcelSelected(parcel: Parcel) {
            Log.i("Parcel_Selected", "$parcel")
            var intent = Intent(this, ParcelDetailActivity::class.java)
            intent.putExtra("documentId", parcel.id)
            startActivity(intent)
    }
}