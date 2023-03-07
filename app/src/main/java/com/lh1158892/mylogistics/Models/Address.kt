package com.lh1158892.mylogistics.Models

class Address (
    var id : String? = null,
    var houseNumber : Int,
    var streetName : String? = null,
    var city : String? = null,
    var province : String? = null,
    var county : String? = null,
    var postalCode : String? = null,
    var recipient : Recepient
        )