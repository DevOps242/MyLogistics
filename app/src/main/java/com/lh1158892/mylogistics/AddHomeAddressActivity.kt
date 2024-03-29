package com.lh1158892.mylogistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.lh1158892.mylogistics.Models.Address
import com.lh1158892.mylogistics.Models.Recipient
import com.lh1158892.mylogistics.databinding.ActivityAddHomeAddressBinding

class AddHomeAddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddHomeAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelHomeAddressButton.setOnClickListener {
            // return back to the Account Page.
            clearAndReturn();
        }

        binding.saveHomeAddressButton.setOnClickListener {

            // Todo: add error handles
            // Get data from the users.
            var streetAddress = binding.addHomeAddressStreetAddressEditText.text.toString();
            var streetAddressTwo = binding.addHomeAddressStreetAddressTwoEditText.text.toString();
            var city = binding.addHomeAddressCityEditText.text.toString();
            var state = binding.addHomeAddressStateEditText.text.toString();
            var country = binding.addHomeAddressCountrySpinner.selectedItem.toString();
            var postalCode = binding.addHomeAddressPostalEditText.text.toString();

            // Get the user uid from logged in state
            val userId = FirebaseAuth.getInstance().currentUser.uid

            // Access the Fire Store
            var db = FirebaseFirestore.getInstance().collection("addresses")
            var addressId = db.document().id

            // Create the address object
            var address = Address(addressId, userId, streetAddress, streetAddressTwo, city, state, country, postalCode)

            // Store the address in the Database
            db.document(addressId).set(address)
                .addOnFailureListener { // Let the user know that an error occurred.
                        exception ->
                    Log.w("DB_Issue", exception.localizedMessage)
                    Toast.makeText(
                        this,
                        "An error occurred, please try again later!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            // return back to the Account Page.
            clearAndReturn();
        }
    }

    private fun clearAndReturn(){
        // clear fields
        binding.addHomeAddressStreetAddressEditText.text.clear();
        binding.addHomeAddressStreetAddressTwoEditText.text.clear();
        binding.addHomeAddressCityEditText.text.clear();
        binding.addHomeAddressStateEditText.text.clear();
        binding.addHomeAddressCountrySpinner.setSelection(0);
        binding.addHomeAddressPostalEditText.text.clear();

        // return back to the Account Page.
       finish()
    }



}