package com.lh1158892.mylogistics.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lh1158892.mylogistics.Models.Parcel

class ParcelViewModel : ViewModel() {
    private var parcels = MutableLiveData<List<Parcel>>()

    // Connect to the Firestore DB and update the parcels with all the parcels that are assigned to the
    // logged in User

    init{
        // Get the user uid
        val userId = Firebase.auth.currentUser.uid

        // Connect to the database
        val db = FirebaseFirestore.getInstance().collection("parcels")
            .whereEqualTo("recipientId", userId)
            .addSnapshotListener{ documents, exception ->
                if(exception != null){
                    Log.w("DB_Response","Listen failed ${exception.code}")
                    return@addSnapshotListener
                }
                documents?.let { documents
                    val parcelsList = ArrayList<Parcel>()

                    for (document in documents){
                        Log.i("DB_Response","${document.data}")
                        val parcel = document.toObject(Parcel::class.java)
                        parcelsList.add(parcel)
                    }
                    parcels.value = parcelsList
                }
            }
    }

    // returns the list of parcels for the logged in user.
    fun getParcels() : LiveData<List<Parcel>>
    {
        return parcels
    }
}