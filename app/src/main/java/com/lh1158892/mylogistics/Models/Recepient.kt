package com.lh1158892.mylogistics.Models

import com.google.firebase.Timestamp

class Recepient(
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var suite: Int,
    var addresses: ArrayList<Address>? = null,
    var email: String? = null,
    var createdOn: Timestamp

        )