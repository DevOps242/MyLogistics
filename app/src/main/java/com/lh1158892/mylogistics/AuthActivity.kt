package com.lh1158892.mylogistics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.lh1158892.mylogistics.Models.Parcel
import com.lh1158892.mylogistics.Models.Recipient


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.logistic_logo)
            .build()
        signInLauncher.launch(signInIntent)
    }

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser

            val suiteNumber = getNextSuiteNumber()

            val db = FirebaseFirestore.getInstance().collection("recipients")
            db.document(user.uid)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        val intent = Intent(this, MainActivity::class.java)

                        intent.putExtra("user", user)
                        startActivity(intent)
                    } else {
                        addRecipient(user, suiteNumber)
                        createDummyParcels(user)

                        val intent = Intent(this, MainActivity::class.java)

                        intent.putExtra("user", user)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Log.w("DB_Issue", "User Login - ${it.localizedMessage}")
                }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun addRecipient(user: FirebaseUser, suiteNumber: Int) {
        val db = FirebaseFirestore.getInstance().collection("recipients")

        val displayName = user.displayName?.split(" ")
        val documentId = user.uid
        val documentEmail = user.email

        val documentFirstName = displayName?.get(0)
        val documentLastName = displayName?.get(displayName.size - 1)
        val documentCreated = Timestamp.now()

        val suiteNumber: Int = suiteNumber

        var recipient = Recipient(documentId, documentFirstName, documentLastName,suiteNumber, documentEmail, documentCreated );

        db.document(documentId).set(recipient)
            .addOnSuccessListener {
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                exception -> Log.w("DB_Issue", exception.localizedMessage)
                Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show()
            }
    }

    private fun createDummyParcels(user:FirebaseUser){
        val db = FirebaseFirestore.getInstance().collection("parcels")

        var userId = user.uid

        // Create some parcels.
        var parcelOne = Parcel(null, null, "Macbook Pro 2019", true, "Pending", "4354SDASGVAEWQ121", "21155", "Apple", "Warehouse", 8.64, "Pending", null );
        var parcelTwo = Parcel(null, null, "Complex Maths Book", false, "Pending", "43543543ABHGEWQ121", "25175", "O'Reilly", "Warehouse", 1.15, "Pending", null );
        var parcelThree = Parcel(null, null, "Coffee Mug", true, "Pending", "854FSADCAA2100", "25175", "Amazon", "In Transit", 1.64, "Pending", null );
        var parcelFour = Parcel(null, null, "Nike Sock Pack", false, "Pending", "112143450078AFD", "21155", "Nike", "Warehouse", .94, "Pending", null );

        var parcelArray = ArrayList<Parcel>()

        parcelArray.add(parcelOne)
        parcelArray.add(parcelTwo)
        parcelArray.add(parcelThree)
        parcelArray.add(parcelFour)

        // Add each to database.
        for (parcelItem in parcelArray){
            parcelItem.id = db.document().id
            parcelItem.recipientId = userId
            db.document(parcelItem.id!!).set(parcelItem)
                .addOnSuccessListener {
                    return@addOnSuccessListener
                }
                .addOnFailureListener {
                    exception -> Log.w("DB_Issue", exception.localizedMessage)
                    Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun getNextSuiteNumber(): Int {

        var highestSuiteNumber = 0;

        // Get all documents in the recipients collection
        val db = FirebaseFirestore.getInstance()
        val querySnapshot = db.collection("recipients")
            .get()
            .addOnSuccessListener {
                documents ->
                for(document in documents) {
                    val suiteNumber = document.getLong("suite")?.toInt() ?: 0

                    // Update the highest suite number if this document's suite number is higher
                    if (suiteNumber > highestSuiteNumber){
                        highestSuiteNumber = suiteNumber
                    }
                }
                return@addOnSuccessListener
            }

        return highestSuiteNumber + 1;
    }
}