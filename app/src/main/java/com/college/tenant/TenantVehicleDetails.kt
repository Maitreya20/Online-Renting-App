package com.college.tenant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityTenantLoginBinding
import com.college.erentapp.databinding.ActivityTenantVehicleDetailsBinding
import com.college.model.VehicleListDetails
import com.college.util.Keys
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tenant_vehicle_details.*

class TenantVehicleDetails : AppCompatActivity() {

    private lateinit var binding: ActivityTenantVehicleDetailsBinding

    var product_details = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tenant_vehicle_details)
        binding = ActivityTenantVehicleDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backbtn.setOnClickListener {
            finish()
        }

        val myIntent = intent
        product_details = myIntent.getStringExtra("product_details")!!

        val product = Gson().fromJson(product_details, VehicleListDetails::class.java)
        binding.textViewPName.text = product.pName
        binding.tvVehiclePrice.text = "₹ " + product.pRent
        binding.txtTenantRegister.text = product.pDescription
        Picasso.get().load(Keys.PHOTO_PATH + product.pPhoto).into(binding.imageViewPPhoto)

        binding.btnBookingRequest.setOnClickListener {
            val intent = Intent(this@TenantVehicleDetails, TenantBooking::class.java)
            intent.putExtra("product_details", product_details)
            startActivity(intent)
        }

    }
}