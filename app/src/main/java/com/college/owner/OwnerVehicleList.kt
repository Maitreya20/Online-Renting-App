package com.college.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.adapter.OwnerVehicleListAdapter
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityOwnerVehicleListBinding

import com.college.model.VehicleListDetails
import com.college.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

class OwnerVehicleList : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerVehicleListBinding
    var vehicleListDetails = ArrayList<VehicleListDetails>()
    private lateinit var ownerVehicleListAdapter: OwnerVehicleListAdapter

    var p_type = ""
    var p_type_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_owner_vehicle_list_details)

        binding = ActivityOwnerVehicleListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        val myIntent = intent
        p_type = myIntent.getStringExtra("p_type")!!
        p_type_name = myIntent.getStringExtra("p_type_name")!!

        supportActionBar!!.title = p_type_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerOwnerVehicleList.setLayoutManager(layoutManager)
        binding.recyclerOwnerVehicleList.setHasFixedSize(true)

        getProductList(p_type)
        /*vehicleListDetails.add(VehicleListDetails(1, R.drawable.car_icon,"Maruti","$ 1000","Available"))
        vehicleListDetails.add(VehicleListDetails(2, R.drawable.bike_icon,"Hero hunda","$ 5000","Unavailable"))
        vehicleListDetails.add(VehicleListDetails(3, R.drawable.car_icon,"Nano","$ 1500","Available"))
        vehicleListDetails.add(VehicleListDetails(4, R.drawable.house_icon,"General Modern House","$ 200000","Available"))
        vehicleListDetails.add(VehicleListDetails(5, R.drawable.car_icon,"Tata motar","$ 10000","Available"))
        vehicleListDetails.add(VehicleListDetails(6, R.drawable.scooter_icon,"activa","$ 50000","Unavailable"))
        vehicleListDetails.add(VehicleListDetails(7, R.drawable.house_icon,"House","$ 100000","Available"))
        vehicleListDetails.add(VehicleListDetails(8, R.drawable.car_icon,"Audi","$ 10000","Available"))
        vehicleListDetails.add(VehicleListDetails(9, R.drawable.car_icon,"Maruti","$ 1000","Available"))
        vehicleListDetails.add(VehicleListDetails(10, R.drawable.bike_icon,"Hero hunda","$ 5000","Unavailable"))
        vehicleListDetails.add(VehicleListDetails(11, R.drawable.car_icon,"Nano","$ 1500","Available"))
        vehicleListDetails.add(VehicleListDetails(12, R.drawable.house_icon,"House 2","$ 200000","Available"))
        vehicleListDetails.add(VehicleListDetails(13, R.drawable.car_icon,"Tata motar","$ 10000","Available"))
        vehicleListDetails.add(VehicleListDetails(14, R.drawable.scooter_icon,"Activa","$ 50000","Unavailable"))
        vehicleListDetails.add(VehicleListDetails(15, R.drawable.house_icon,"House","$ 100000","Available"))
        vehicleListDetails.add(VehicleListDetails(16, R.drawable.car_icon,"Audi","$ 10000","Available"))*/



    }

    private fun getProductList(pType: String) {
        val request : StringRequest = object : StringRequest(Method.POST, Keys.Owner.OWNER_PRODUCT_LIST, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                val data = json.optJSONArray("data")
                val listType : Type = object : TypeToken<List<VehicleListDetails?>?>() {}.type
                vehicleListDetails = Gson().fromJson(data.toString(), listType)
                ownerVehicleListAdapter = OwnerVehicleListAdapter(vehicleListDetails)
                binding.recyclerOwnerVehicleList.adapter = ownerVehicleListAdapter
            }
            applicationContext.toast(json.optString("message"))
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("p_type", pType)
                params.put("o_id", SharedPreference.get("o_id"))
                return params
            }
        }

        AppController.getInstance().add(request)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}