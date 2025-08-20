package com.college.tenant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.adapter.OwnerVehicleListAdapter
import com.college.adapter.PriceFilterListAdapter
import com.college.adapter.TenantVehicleListAdapter
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityTenantVehicleListBinding
import com.college.model.FilterPriceDetails
import com.college.model.VehicleListDetails
import com.college.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

class TenantVehicleList : AppCompatActivity() {
    private lateinit var binding: ActivityTenantVehicleListBinding
    var vehicleListDetails = ArrayList<VehicleListDetails>()
    val filterList=ArrayList<FilterPriceDetails>()
    private lateinit var tenantVehicleListAdapter: TenantVehicleListAdapter
    private lateinit var filterListAdapter:PriceFilterListAdapter

    var p_type = ""
    var p_type_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tenant_vehicle_list)
        binding = ActivityTenantVehicleListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        val myIntent = intent
        p_type = myIntent.getStringExtra("p_type")!!
        p_type_name = myIntent.getStringExtra("p_type_name")!!

        supportActionBar!!.title = p_type_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerTenantVehicleList.setLayoutManager(layoutManager)
        binding.recyclerTenantVehicleList.setHasFixedSize(true)

        getProductList(p_type)

        tenantVehicleListAdapter = TenantVehicleListAdapter(vehicleListDetails)
        binding.recyclerTenantVehicleList.adapter = tenantVehicleListAdapter


        filterList.add(FilterPriceDetails(1, "$ 1000"))
        filterList.add(FilterPriceDetails(2, "$ 2000"))
        filterList.add(FilterPriceDetails(3, "$ 10000"))
        filterList.add(FilterPriceDetails(4, "$ 10000"))
        filterList.add(FilterPriceDetails(5, "$ 40000"))
        filterList.add(FilterPriceDetails(6, "$ 50000"))
        filterList.add(FilterPriceDetails(7, "$ 60000"))
        filterList.add(FilterPriceDetails(8, "$ 800000"))
        filterList.add(FilterPriceDetails(9, "$ 90000"))
        filterList.add(FilterPriceDetails(10, "$ 1000"))
    }

    private fun getProductList(pType: String) {
        val request : StringRequest = object : StringRequest(Method.POST, Keys.Tenant.TENANT_PRODUCT_LIST, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                val data = json.optJSONArray("data")
                val listType : Type = object : TypeToken<List<VehicleListDetails?>?>() {}.type
                vehicleListDetails = Gson().fromJson(data.toString(), listType)
                tenantVehicleListAdapter = TenantVehicleListAdapter(vehicleListDetails)
                binding.recyclerTenantVehicleList.adapter = tenantVehicleListAdapter
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
                return params
            }
        }

        AppController.getInstance().add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.filter, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_filter->{
                val builder = AlertDialog.Builder(this@TenantVehicleList)
                val customLayout: View =
                    LayoutInflater.from(applicationContext).inflate(R.layout.custom_filter_layout, null)
                builder.setView(customLayout)
                val dialog = builder.create()
                var btn_submit: Button
                var recyclerView_price_filter:RecyclerView
                btn_submit=customLayout.findViewById(R.id.btn_submit)
                recyclerView_price_filter=customLayout.findViewById(R.id.recycler_price_filter)


                val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                recyclerView_price_filter.layoutManager=layoutManager
                recyclerView_price_filter.setHasFixedSize(true)
                filterListAdapter = PriceFilterListAdapter(filterList)
                recyclerView_price_filter.adapter = filterListAdapter
                recyclerView_price_filter.addItemDecoration(
                    DividerItemDecoration(
                        baseContext,
                        layoutManager.orientation
                    )
                )


                btn_submit.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}