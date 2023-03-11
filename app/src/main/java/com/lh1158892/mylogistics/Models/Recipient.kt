package com.lh1158892.mylogistics.Models

import com.google.firebase.Timestamp

class Recipient (
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var suite: Int? = null,
    var addresses: ArrayList<String>? = null,
    var email: String? = null,
    var createdOn: Timestamp? = null


    ) {
    constructor() : this(
        id = null,
        firstName = null,
        lastName = null,
        suite = null,
        addresses = null,
        email = null,
        createdOn = null
    )
}

