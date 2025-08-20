package com.college.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.college.erentapp.R
import com.college.erentapp.databinding.CustomVehicleDetailListBinding
import com.college.model.MyBookingDetails

class TenantBookingAdapter(var myBookingDetailList: List<MyBookingDetails>) :
    RecyclerView.Adapter<TenantBookingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomVehicleDetailListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomVehicleDetailListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val myBookingDetails = myBookingDetailList[position]
            binding.tvMyBookingDateTime.visibility=View.VISIBLE

            /*binding.tvMyBookingVehicleName.text = myBookingDetails.name
            binding.tvMyBookingVehiclePrice.text = myBookingDetails.price

            if (myBookingDetails.status.equals("Accepted")) {
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.accept_status_background)
                )
            } else if (myBookingDetails.status.equals("Rejected")) {
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.reject_status_background)
                )
            } else {
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.pending_status_background)
                )
            }
            binding.tvMyBookingVehicleStatus.text = myBookingDetails.status

            binding.tvMyBookingDateTime.text = myBookingDetails.dateTime
            binding.imgTenantVehicle.setImageResource(myBookingDetails.image)*/

        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return myBookingDetailList.size
    }

}