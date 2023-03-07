package com.lh1158892.mylogistics.Models

import com.google.type.DateTime

class Delivery (
    var id : String? = null,
    var status : String? = "In Progress",
    var parcel : Parcel,
    var address: Address,
    var deliveryDate: DateTime,
    var createdOn: DateTime
        )