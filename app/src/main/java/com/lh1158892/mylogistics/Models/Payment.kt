package com.lh1158892.mylogistics.Models

import com.google.type.DateTime
import java.text.DecimalFormat

class Payment (
    var id : String? = null,
    var suite : Recipient,
    var parcel : Parcel,
    var invoiceRef : String? = null,
    var invoiceStatus : String? = "Pending",
    var amount : DecimalFormat,
    var referenceId : String? = null,
    var apiGatewaySession : String? = null,
    var createdOn : DateTime

        )