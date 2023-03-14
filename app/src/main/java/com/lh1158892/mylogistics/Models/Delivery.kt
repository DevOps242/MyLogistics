package com.lh1158892.mylogistics.Models

import com.google.firebase.Timestamp

class Delivery (
    var id : String? = null,
    var status : String? = "In Progress",
    var parcelId : String? = null,
    var addressId: String? = null,
    var recipientId : String? = null,
    var deliveryDate: Timestamp,
    var createdOn: Timestamp
        )