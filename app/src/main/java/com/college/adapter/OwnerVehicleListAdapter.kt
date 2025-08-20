package com.college.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.college.erentapp.R

import com.college.erentapp.databinding.CustomVehicleDetailListBinding
import com.college.model.MyBookingDetails
import com.college.model.VehicleListDetails
import com.college.owner.OwnerSignup
import com.college.owner.OwnerVehicleDetails
import com.college.tenant.TenantVehicleDetails
import com.college.util.Keys
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class OwnerVehicleListAdapter(var vehicleList: List<VehicleListDetails>) :
    RecyclerView.Adapter<OwnerVehicleListAdapter.ViewHolder>() {

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
            val vehicleListDetails = vehicleList[position]
            //binding.btnUnavailable.visibility=View.VISIBLE
            if (vehicleListDetails.pStatus.equals("1")) {
                binding.tvMyBookingVehicleStatus.text = "Available"
                binding.btnUnavailable.text="Available"
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.accept_status_background)
                )
            } else if (vehicleListDetails.pStatus.equals("0")) {
                //binding.btnUnavailable.text="Unavailable"
                binding.tvMyBookingVehicleStatus.text = "Unavailable"
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.reject_status_background)
                )
            }

            binding.tvMyBookingDateTime.visibility= View.GONE
            binding.tvMyBookingVehicleName.text = vehicleListDetails.pName
            binding.tvMyBookingVehiclePrice.text = "₹ " + vehicleListDetails.pRent
            //binding.tvMyBookingVehicleStatus.text = vehicleListDetails.pStatus
            Picasso.get().load(Keys.PHOTO_PATH + vehicleListDetails.pPhoto).into(binding.imgTenantVehicle)
            //binding.imgTenantVehicle.setImageResource(vehicleListDetails.image)
            binding.cdVehicleList.setOnClickListener {
                val intent = Intent(binding.root.context, OwnerVehicleDetails::class.java)
                intent.putExtra("product_details", Gson().toJson(vehicleListDetails).toString())
                binding.root.context.startActivity(intent)
            }

        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return vehicleList.size
    }

}