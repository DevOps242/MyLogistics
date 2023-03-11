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
import com.lh1158892.mylogistics.Models.Recepient


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
//            .setLogo(R.drawable.time_tracker_logo)
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

            // Check if user email is already added in the Recipients collection in DB
            Log.i("Data-Dump", user.toString());

            // Fire function to add the user.
            if (!checkRecipientExist(user)){
                addRecipient(user)
            }
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("user", user)
            startActivity(intent)

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun checkRecipientExist(user: FirebaseUser): Boolean {
        val db = FirebaseFirestore.getInstance().collection("recipients")

        var documentId = user.uid

        var result: Boolean = false

        db.document(documentId).get()
            .addOnSuccessListener {
                result = true;
            }
        return result;
    }
    private fun addRecipient(user: FirebaseUser) {
        val db = FirebaseFirestore.getInstance().collection("recipients")

        var documentId = user.uid
        var documentEmail = user.email
        var documentCreated = Timestamp.now()

        val suiteNumber: Int = getNextSuiteNumber()

        var recipient = Recepient(documentId, null, null,suiteNumber, null, documentEmail, documentCreated );

        db.document(documentId).set(recipient)
            .addOnSuccessListener {
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                exception -> Log.w("DB_Issue", exception.localizedMessage)
                Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show()
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
                    val suiteNumber = document.getString("suite")?.toInt() ?: 0

                    // Update the highest suite number if this document's suite number is higher
                    if (suiteNumber > highestSuiteNumber){
                        highestSuiteNumber = suiteNumber
                    }
                }
                highestSuiteNumber += 1
            }

        return highestSuiteNumber;
    }
}