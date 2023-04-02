package com.lh1158892.mylogistics

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

        var user = FirebaseAuth.getInstance().currentUser

        val viewModel : ParcelViewModel by viewModels()
        viewModel.getParcels().observe(this) {
            parcels.addAll(it)
            parcels?.let {
                for (parcel in parcels) {
                    if ((parcel.location == "In transit") || (parcel.location == "Warehouse")) {
                        binding.parcelRecyclerViewMain.adapter = ParcelAdapter(this, it, this)
                    }
                }
            }
        }

        // Set welcome message
        var name = user.displayName.split(" ")[0]

        if (!name.isNullOrBlank()){
            binding.mainPageWelcome.text = "Welcome $name"
        }

        // Tracking Parcel Search
        binding.trackParcelButton.setOnClickListener {
            // get the tracking id text from user.
            var trackingIdField = binding.trackParcelEditText.text
            var trackingId = trackingIdField.toString()

            if (trackingId.isNullOrBlank()){
                Toast.makeText(this, "Tracking Number Input must not be blank", Toast.LENGTH_LONG).show()
            } else {

                Log.i("DB_Response", "your tracking id: $trackingId")
                // Perform a search
                val db = FirebaseFirestore.getInstance().collection("parcels")
                    .whereEqualTo("recipientId", user.uid)
                    .whereEqualTo("trackingId", trackingId)
                    .get()
                    .addOnSuccessListener {
                        if (it.isEmpty) {
                            // Doesn't Exists
                            Toast.makeText(this, "There is no parcel with this tracking number", Toast.LENGTH_LONG).show()

                            // Removes the keyboard.
                            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(binding.mainActivity.windowToken, 0)
                        } else {
                            // Parcel Exists
                            val documentSnapshot = it.documents[0]
                            var parcelId = documentSnapshot.id

                            val intent = Intent(this, ParcelDetailActivity::class.java)
                            intent.putExtra("documentId", parcelId)
                            startActivity(intent)
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


        /**
         * This will handle the navigation clicks.
         */

        // Update currentPage to the new activity
        var currentPage = this

        // Set the selected item based on the current page
        when (currentPage) {
            is MainActivity -> binding.bottomNavigationView.selectedItemId = R.id.mainActivity
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