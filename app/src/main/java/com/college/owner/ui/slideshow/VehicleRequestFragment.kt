package com.college.owner.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.adapter.OwnerVehicleListAdapter
import com.college.adapter.TenantVehicleRequestAdapter
import com.college.erentapp.R
import com.college.erentapp.databinding.FragmentVehicleRequestBinding
import com.college.model.VehicleRequestList
import com.college.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type


class VehicleRequestFragment : Fragment() {

    private var _binding: FragmentVehicleRequestBinding? = null
    var vehicleRequestList = ArrayList<VehicleRequestList>()
    private lateinit var tenantVehicleRequestAdapter: TenantVehicleRequestAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleRequestBinding.inflate(inflater, container, false)

        AppController.initialize(activity)
        SharedPreference.initialize(activity)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.recyclerVehicleRequestList.setLayoutManager(layoutManager)
        binding.recyclerVehicleRequestList.setHasFixedSize(true)

        getOwnerProductRequest();

        /*vehicleRequestList.add(VehicleRequestList(1, R.drawable.car_icon,"Maruti","$ 1000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(2, R.drawable.bike_icon,"Hero hunda","$ 5000","31-01-2023 11:00 AM","Accepted","Raj Mehta"))
        vehicleRequestList.add(VehicleRequestList(3, R.drawable.car_icon,"Nano","$ 1500","30-01-2023 10:00 AM","Accepted","Aakash Mathew"))
        vehicleRequestList.add(VehicleRequestList(4, R.drawable.house_icon,"General Modern House","$ 200000","30-01-2023 10:00 AM","Rejected","Raj Mathew"))
        vehicleRequestList.add(VehicleRequestList(5, R.drawable.car_icon,"Tata motar","$ 10000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(6, R.drawable.scooter_icon,"activa","$ 50000","31-01-2023 11:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(7, R.drawable.house_icon,"House","$ 100000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(8, R.drawable.car_icon,"Audi","$ 10000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(9, R.drawable.car_icon,"Maruti","$ 1000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(10, R.drawable.bike_icon,"Hero hunda","$ 5000","31-01-2023 11:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(11, R.drawable.car_icon,"Nano","$ 1500","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(12, R.drawable.house_icon,"House 2","$ 200000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(13, R.drawable.car_icon,"Tata motar","$ 10000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(14, R.drawable.scooter_icon,"Activa","$ 50000","31-01-2023 11:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(15, R.drawable.house_icon,"House","$ 100000","30-01-2023 10:00 AM","Pending","John Mathew"))
        vehicleRequestList.add(VehicleRequestList(16, R.drawable.car_icon,"Audi","$ 10000","30-01-2023 10:00 AM","Pending","John Mathew"))*/

        return binding.root
    }

    private fun getOwnerProductRequest() {
        var request : StringRequest = object : StringRequest(Method.POST, Keys.Owner.OWNER_NEW_REQUEST, Response.Listener {
            response ->
            activity!!.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                val data = json.optString("data")
                val listType : Type = object : TypeToken<List<VehicleRequestList?>?>() {}.type
                vehicleRequestList = Gson().fromJson(data.toString(), listType)
                tenantVehicleRequestAdapter = TenantVehicleRequestAdapter(vehicleRequestList)
                binding.recyclerVehicleRequestList.adapter = tenantVehicleRequestAdapter
            }
            activity!!.toast(json.optString("message"))
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(activity, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("o_id", SharedPreference.get("o_id"))
                return params
            }
        }

        AppController.getInstance().add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}