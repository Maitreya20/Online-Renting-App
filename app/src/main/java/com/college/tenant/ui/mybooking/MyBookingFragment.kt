package com.college.tenant.ui.mybooking

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
import com.college.adapter.MyBookingAdapter
import com.college.erentapp.R
import com.college.erentapp.databinding.FragmentMyBookingBinding
import com.college.model.MyBookingDetails
import com.college.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

class MyBookingFragment : Fragment() {

    private var _binding: FragmentMyBookingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var myBookingDetails = ArrayList<MyBookingDetails>()
    private lateinit var myBookingAdapter: MyBookingAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)

        AppController.initialize(activity)
        SharedPreference.initialize(activity)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.recyclerMyBooking.setLayoutManager(layoutManager)
        binding.recyclerMyBooking.setHasFixedSize(true)

        getUserBookings()

        /*myBookingDetails.add(MyBookingDetails(1, R.drawable.car_icon,"Maruti","$ 1000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(2, R.drawable.bike_icon,"Hero hunda","$ 5000","31-01-2023 11:00 AM","Rejected"))
        myBookingDetails.add(MyBookingDetails(3, R.drawable.car_icon,"Nano","$ 1500","30-01-2023 10:00 AM","Accepted"))
        myBookingDetails.add(MyBookingDetails(4, R.drawable.house_icon,"General Modern House","$ 200000","30-01-2023 10:00 AM","Accepted"))
        myBookingDetails.add(MyBookingDetails(5, R.drawable.car_icon,"Tata motar","$ 10000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(6, R.drawable.scooter_icon,"activa","$ 50000","30-01-2023 10:00 AM","Rejected"))
        myBookingDetails.add(MyBookingDetails(7, R.drawable.house_icon,"House","$ 100000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(8, R.drawable.car_icon,"Audi","$ 10000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(9, R.drawable.car_icon,"Maruti","$ 1000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(10, R.drawable.bike_icon,"Hero hunda","$ 5000","30-01-2023 10:00 AM","Rejected"))
        myBookingDetails.add(MyBookingDetails(11, R.drawable.car_icon,"Nano","$ 1500","30-01-2023 10:00 AM","Accepted"))
        myBookingDetails.add(MyBookingDetails(12, R.drawable.house_icon,"House 2","$ 200000","30-01-2023 10:00 AM","Accepted"))
        myBookingDetails.add(MyBookingDetails(13, R.drawable.car_icon,"Tata motar","$ 10000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(14, R.drawable.scooter_icon,"Activa","$ 50000","30-01-2023 10:00 AM","Rejected"))
        myBookingDetails.add(MyBookingDetails(15, R.drawable.house_icon,"House","$ 100000","30-01-2023 10:00 AM","Pending"))
        myBookingDetails.add(MyBookingDetails(16, R.drawable.car_icon,"Audi","$ 10000","30-01-2023 10:00 AM","Pending"))*/



        return binding.root
    }

    private fun getUserBookings() {
        var request : StringRequest = object : StringRequest(Method.POST, Keys.Tenant.GET_TENANT_BOOKINGS, Response.Listener {
            response ->
            activity!!.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                val data = json.optString("data")
                val listType : Type = object : TypeToken<List<MyBookingDetails?>?>() {}.type
                myBookingDetails = Gson().fromJson(data.toString(), listType)
                myBookingAdapter = MyBookingAdapter(myBookingDetails)
                binding.recyclerMyBooking.adapter = myBookingAdapter
            }
            activity!!.toast(json.optString("message"))
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(activity, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("u_id", SharedPreference.get("u_id"))
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