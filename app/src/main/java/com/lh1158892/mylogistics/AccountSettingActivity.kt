package com.lh1158892.mylogistics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lh1158892.mylogistics.Adapters.HomeAddressAdapter
import com.lh1158892.mylogistics.Models.Recipient
import com.lh1158892.mylogistics.ViewModels.HomeAddressViewModel
import com.lh1158892.mylogistics.databinding.ActivityAccountSettingBinding
import kotlinx.coroutines.awaitAll


class AccountSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var user = FirebaseAuth.getInstance().currentUser

        var db = FirebaseFirestore.getInstance().collection("recipients")
        var documentId = user.uid


        db.document(documentId)
            .get()
            .addOnSuccessListener {
                binding.internationalSuiteNumber.text = "Suite: " + it.get("suite").toString()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error Loading User Data, please try again later.", Toast.LENGTH_LONG).show()
            }


        // Connect the RecyclerView, Adapter and ViewModel
        val viewModel: HomeAddressViewModel by viewModels()
        viewModel.getAddresses().observe(this) {
            binding.homeAddressRecyclerView.adapter = HomeAddressAdapter(this, it)
        }

//        if (homeAddressCount <= 0) {
//            Toast.makeText(this, "There are no Home Address to show", Toast.LENGTH_LONG)
//                .show()
//        }

        // Handle add address button
        binding.homeAddressAddButton.setOnClickListener {
//            if (homeAddressCount >= 2){
//                Toast.makeText(this, "You are only allowed to have two home address at a time", Toast.LENGTH_LONG).show()
//                return@setOnClickListener;
//            } else {
                // Load the fragment page to create the new address.
                val intent = Intent(this, AddHomeAddressActivity::class.java)
                startActivity(intent)
//            }
        }

        /**
         * This will handle the navigation clicks.
         */
        // Update currentPage to the new activity
        var currentPage = this

        // Set the selected item based on the current page
        when (currentPage) {
            is AccountSettingActivity -> binding.bottomNavigationView.selectedItemId = R.id.accountSettingActivity
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