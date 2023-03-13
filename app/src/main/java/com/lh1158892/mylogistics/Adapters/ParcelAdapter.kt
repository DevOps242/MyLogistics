package com.lh1158892.mylogistics.Adapters

import android.content.Context
import com.lh1158892.mylogistics.Models.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lh1158892.mylogistics.Models.Parcel
import com.lh1158892.mylogistics.R

class ParcelAdapter (val context: Context,
                     val parcels : List<Parcel>,
                     val itemListener: ParcelItemListener) : RecyclerView.Adapter<ParcelAdapter.ParcelViewHolder>(){


    inner class ParcelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val parcelDescriptionTextView = itemView.findViewById<TextView>(R.id.parcelItemDescription)
        val parcelTrackingNumberTextView = itemView.findViewById<TextView>(R.id.parcelItemTrackingNumber)
        val parcelVendorTextView = itemView.findViewById<TextView>(R.id.parcelItemVendor)
        val parcelWeightTextView = itemView.findViewById<TextView>(R.id.parcelItemWeight)
        val parcelLocationValueTextView = itemView.findViewById<TextView>(R.id.parcelItemLocationValue)
        val parcelButton = itemView.findViewById<TextView>(R.id.parcelItemButton)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParcelViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.parcel_item, parent, false)
        return ParcelViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ParcelViewHolder, position: Int) {
        val parcel = parcels[position]
        with (viewHolder){
            parcelDescriptionTextView.text = parcel.description
            parcelTrackingNumberTextView.text = "Tracking #: " + parcel.trackingId
            parcelVendorTextView.text = "Vendor: " + parcel.vendor
            parcelWeightTextView.text = "Weight: " + parcel.weight.toString() + " lbs"
            parcelLocationValueTextView.text = parcel.location
            parcelButton.setOnClickListener {
                itemListener.parcelSelected(parcel)
            }
        }
    }

    override fun getItemCount(): Int {
        return parcels.size
    }

    interface ParcelItemListener{
        fun parcelSelected(parcel: Parcel)
    }

}