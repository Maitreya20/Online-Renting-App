package com.college.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.college.erentapp.databinding.CustomVehicleRequestBookingListBinding
import com.college.model.VehicleRequestList
import com.college.util.Keys
import com.squareup.picasso.Picasso

class TenantVehicleRequestAdapter(var vehicleRequestList: List<VehicleRequestList>) :
    RecyclerView.Adapter<TenantVehicleRequestAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomVehicleRequestBookingListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomVehicleRequestBookingListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val vehicleRequest = vehicleRequestList[position]
            binding.tvName.text = "Booking Name:- " + vehicleRequest.uName
            binding.tvMyBookingVehicleName.text = "Product :- " + vehicleRequest.pName
            binding.tvMyBookingVehiclePrice.text = "₹ " + vehicleRequest.orAmount

            /*if (vehicleRequest.status.equals("Accepted")) {
                binding.layoutAccept.visibility=View.GONE
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.accept_status_background)
                )
            } else if (vehicleRequest.status.equals("Rejected")) {
                binding.layoutAccept.visibility=View.GONE
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.reject_status_background)
                )
            } else {
                binding.layoutAccept.visibility=View.VISIBLE
                binding.tvMyBookingVehicleStatus.setBackground(
                    binding.root.context.resources.getDrawable(R.drawable.pending_status_background)
                )
            }*/
            //binding.tvMyBookingVehicleStatus.text = vehicleRequest.status
            binding.tvMyBookingDateTime.text = "Booking Time :- " + vehicleRequest.orTime
            Picasso.get().load(Keys.PHOTO_PATH + vehicleRequest.pPhoto).into(binding.imgTenantVehicle)
            //binding.imgTenantVehicle.setImageResource(vehicleRequest.image)
            binding.imageViewUPhone.setOnClickListener {
                var intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:" + vehicleRequest.uPhone))
                binding.root.context.startActivity(intent)
            }

        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return vehicleRequestList.size
    }

}