package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


        // Handle Navigation Actions for the Activities
        var navigationHandler = binding.bottomNavigationView
        navigationHandler.touchables.forEachIndexed { index, view ->
            run {
                view.setOnClickListener {
                    when (index) {
                        0 -> {
                            val intent = Intent(this, MainActivity::class.java)
//                            intent.putExtra("user",user)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(this, ParcelActivity::class.java)
//                            intent.putExtra("user",user)
                            startActivity(intent)
                        }
                        2 -> {
                            val intent = Intent(this, DeliveryActivity::class.java)
//                            intent.putExtra("user",user)
                            startActivity(intent)
                        }
                        3 -> {
                            val intent = Intent(this, AccountSettingActivity::class.java)
//                            intent.putExtra("user",user)
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