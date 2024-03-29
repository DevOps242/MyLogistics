package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.lh1158892.mylogistics.Models.Parcel
import com.lh1158892.mylogistics.ViewModels.ParcelViewModel
import com.lh1158892.mylogistics.databinding.ActivityParcelDetailBinding
import java.text.DecimalFormat

class ParcelDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParcelDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcelDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val documentId = intent.getStringExtra("documentId")
        Log.i("DB_Response", documentId!!)

//        val db = FirebaseFirestore.getInstance().collection("parcels")
//        db.document(documentId.toString())
//            .get()
//            .addOnSuccessListener {
//                binding.parcelItemDescription.text =
//            }
//            .addOnFailureListener {
//
//            }

        var parcels : MutableList<Parcel> = ArrayList()
        val df = DecimalFormat("#.##")

        val viewModel : ParcelViewModel by viewModels()
        viewModel.getParcels().observe(this) {
            parcels.addAll(it)
            documentId?.let {
                for (parcel in parcels) {
                    if (parcel.id.equals(documentId)) {
                        binding.parcelItemDescription.text = parcel.description
                        binding.parcelItemTrackingNumber.text = "Tracking #: "+parcel.trackingId
                        binding.parcelItemWeight.text = "Weight: " +parcel.weight+" lbs"
                        binding.parcelItemVendor.text = "Vendor: " +parcel.vendor
                        binding.parcelItemLocation.text = "Location: "+parcel.location
                        binding.parcelItemWarehouse.text = "Warehouse #:" +parcel.warehouseNumber
                        binding.parcelItemFragile.text = "Fragile: " + parcel.fragile.toString()

                        binding.parcelItemPaymentWeight.text = "Weight: " +parcel.weight+" lbs"
                        binding.parcelItemPaymentInvoiceStatus.text = "Invoice: " +parcel.invoiceStatus
                        binding.parcelItemPaymentInsurance.text = "Insurance: $"+ df.format(setInsurance(parcel.fragile))
                        binding.parcelItemPaymentServiceFee.text = "Service Fee: $10.00"
                        binding.parcelItemPaymentSubtotal.text = "Subtotal: $" + df.format(calculateSubTotal(parcel.weight!!, parcel.fragile))
                        binding.parcelItemPaymentTax.text = "Tax: $" + df.format(calculateTax(calculateSubTotal(parcel.weight!!, parcel.fragile)))
                        binding.parcelItemPaymentTotal.text = "Total: $" + df.format(calculateTotal(calculateSubTotal(parcel.weight!!, parcel.fragile), calculateTax(calculateSubTotal(parcel.weight!!, parcel.fragile))))
                    }
                }
            }
        }

        /**
         * This will handle the navigation clicks.
         */

        // Update currentPage to the new activity
        var currentPage = this

        // Set the selected item based on the current page
        when (currentPage) {
            is ParcelDetailActivity -> binding.bottomNavigationView.selectedItemId = R.id.parcelActivity
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.mainActivity -> {
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

    private fun setInsurance(fragile: Boolean) : Double{
        if (fragile){
            return 5.75
        } else {
            return 0.0
        }
    }

    private fun calculateSubTotal(weight : Double, fragile: Boolean) : Double {
        var insurance = setInsurance(fragile)
        var weight = weight
        val serviceFee = 10.00
        var subTotal = 0.0

        if (weight <= 5.75){
            weight = weight * 1.85
        } else if(weight > 5.75 && weight <= 12.00){
            weight = weight * 1.25
        } else {
            weight = weight * .99
        }

        subTotal = weight + serviceFee + insurance!!

        return subTotal
    }

    private fun calculateTax(subTotal: Double) : Double {
        return 0.13 * subTotal
    }

    private fun calculateTotal(subTotal: Double, tax: Double) : Double {
        return (subTotal  + tax);
    }
}