package com.lh1158892.mylogistics.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lh1158892.mylogistics.Models.Address

class HomeAddressViewModel : ViewModel() {
    private var addresses = MutableLiveData<List<Address>>()

    // Connect to the Firestore DB and update the addresses with all the addresses that are assigned to the
    // logged in User

    init{
        // Get the user uid
        val userId = Firebase.auth.currentUser.uid

        // Connect to the database
        val db = FirebaseFirestore.getInstance().collection("addresses")
            .whereEqualTo("recipientId", userId)
            .addSnapshotListener{ documents, exception ->
                if(exception != null){
                    Log.w("DB_Response","Listen failed ${exception.code}")
                    return@addSnapshotListener
                }
                documents?.let { documents
                    val addressList = ArrayList<Address>()

                    for (document in documents){
                        Log.i("DB_Response","${document.data}")
                        val address = document.toObject(Address::class.java)
                        addressList.add(address)
                    }
                    addresses.value = addressList
                }
            }
    }

    // returns the list of address for the logged in user.
    fun getAddresses() : LiveData<List<Address>>
    {
        return addresses
    }
}