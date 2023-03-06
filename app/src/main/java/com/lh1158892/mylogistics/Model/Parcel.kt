package com.lh1158892.mylogistics.Model

import java.text.DecimalFormat

class Parcel (
    var id : String? = null,
    var suite : Recepient,
    var description : String? = null,
    var fragile : Boolean = false,
    var status : String = "Pending",
    var trackingId : String? = null,
    var warehouseNumber : String? = null,
    var vendor : String? = null,
    var location : String? = null,
    var weight : DecimalFormat? = null,
        )