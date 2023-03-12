package com.lh1158892.mylogistics.Adapters

import android.content.Context
import com.lh1158892.mylogistics.Models.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lh1158892.mylogistics.R

class HomeAddressAdapter (val context: Context,
                          val addresses : List<Address>,
                          ) : RecyclerView.Adapter<HomeAddressAdapter.AddressViewHolder>(){
//    val itemListener: HomeAddressItemListener

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val streetAddressTextView = itemView.findViewById<TextView>(R.id.localStreetAddress)
        val streetAddressTwoTextView = itemView.findViewById<TextView>(R.id.localStreetAddressTwo)
        val cityTextView = itemView.findViewById<TextView>(R.id.localCity)
        val stateTextView = itemView.findViewById<TextView>(R.id.localState)
        val countryTextView = itemView.findViewById<TextView>(R.id.localCountry)
        val postalCodeTextView = itemView.findViewById<TextView>(R.id.localPostalCode)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
       val inflator = LayoutInflater.from(parent.context)
       val view = inflator.inflate(R.layout.home_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AddressViewHolder, position: Int) {
        val address = addresses[position]
        with (viewHolder){
            streetAddressTextView.text = address.streetAddress
            streetAddressTwoTextView.text = address.streetAddressTwo
            cityTextView.text = address.city
            stateTextView.text = address.state
            countryTextView.text = address.county
            postalCodeTextView.text = address.postalCode
        }
    }

    override fun getItemCount(): Int {
        return addresses.size
    }




}