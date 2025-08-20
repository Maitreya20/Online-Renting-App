package com.college.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.college.erentapp.R
import com.college.erentapp.databinding.CustomFilterDetailListBinding

import com.college.erentapp.databinding.CustomVehicleDetailListBinding
import com.college.model.FilterPriceDetails
import com.college.model.MyBookingDetails
import com.college.model.VehicleListDetails
import com.college.owner.OwnerSignup
import com.college.tenant.TenantVehicleDetails

class PriceFilterListAdapter(var filterPriceList: List<FilterPriceDetails>) :
    RecyclerView.Adapter<PriceFilterListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomFilterDetailListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomFilterDetailListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val filterListDetails = filterPriceList[position]
            binding.checkboxVehiclePrice.text = filterListDetails.price
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return filterPriceList.size
    }

}