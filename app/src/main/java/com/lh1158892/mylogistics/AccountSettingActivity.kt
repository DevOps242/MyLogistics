package com.lh1158892.mylogistics

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lh1158892.mylogistics.databinding.ActivityAccountSettingBinding


class AccountSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Handle add address button
        binding.homeAddressAddButton.setOnClickListener {

            // Load the fragment page to create the new address.
            val intent = Intent(this, AddHomeAddressActivity::class.java)
            startActivity(intent)
        }

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
    }

}