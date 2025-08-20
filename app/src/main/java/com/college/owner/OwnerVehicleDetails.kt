package com.college.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.erentapp.databinding.ActivityOwnerVehicleDetailsBinding
import com.college.model.VehicleListDetails
import com.college.util.AppController
import com.college.util.Keys
import com.college.util.SharedPreference
import com.college.util.log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONObject

class OwnerVehicleDetails : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerVehicleDetailsBinding

    var product_details = ""
    lateinit var product : VehicleListDetails


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerVehicleDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        val myIntent = intent
        product_details = myIntent.getStringExtra("product_details")!!

        product = Gson().fromJson(product_details, VehicleListDetails::class.java)

        binding.textViewPName.text = product.pName
        binding.tvVehiclePrice.text = "₹ " + product.pRent
        binding.txtTenantRegister.text = product.pDescription
        Picasso.get().load(Keys.PHOTO_PATH + product.pPhoto).into(binding.imageViewPPhoto)

        binding.backbtn.setOnClickListener {
            finish()
        }

        showVehicleStatus(product.pStatus!!)

        binding.btnChangeStatus.setOnClickListener {
            if(product.pStatus!!.equals("1"))
                updateProductStatus(product.pId!!, "0")
            else
                updateProductStatus(product.pId!!, "1")
        }

    }

    private fun updateProductStatus(pId: String, status: String) {
        var request : StringRequest = object : StringRequest(Method.POST, Keys.Owner.UPDATE_PRODUCT_STATUS, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                product.pStatus = status
                showVehicleStatus(status)
            }
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("p_id", pId)
                params.put("p_status", status)
                return params
            }
        }

        AppController.getInstance().add(request)
    }

    private fun showVehicleStatus(pStatus: String) {
        if(pStatus.equals("1"))
            binding.txtVehicleStatus.text = "Status :- Available"
        else
            binding.txtVehicleStatus.text = "Status :- Unavailable"
    }
}