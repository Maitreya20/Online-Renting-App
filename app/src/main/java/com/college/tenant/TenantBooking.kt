package com.college.tenant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityTenantBookingBinding
import com.college.erentapp.databinding.ActivityTenantSignupBinding
import com.college.model.VehicleListDetails
import com.college.util.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tenant_booking.*
import org.json.JSONObject

class TenantBooking : AppCompatActivity() {
    private lateinit var binding: ActivityTenantBookingBinding

    var product_details = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tenant_booking)
        binding = ActivityTenantBookingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.title = "Send Booking Request"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        val myIntent = intent
        product_details = myIntent.getStringExtra("product_details")!!
        val product = Gson().fromJson(product_details, VehicleListDetails::class.java)
        binding.txtVehicleName.text = product.pName
        binding.txtAge.text = "₹ " + product.pRent
        Picasso.get().load(Keys.PHOTO_PATH + product.pPhoto).into(binding.imgVehicle)
        binding.txtUName.text = SharedPreference.get("u_name")
        binding.txtUPhone.text = SharedPreference.get("u_phone")
        binding.txtUEmail.text = SharedPreference.get("u_email")


        binding.btnBuyCar.setOnClickListener {
            val b_name = edt_card_holder_name.text.toString().trim()
            val b_card = edt_card_no.text.toString().trim()
            val b_month = spinneMonth.selectedItem.toString()
            val b_year = spinnerYear.selectedItem.toString()
            val b_cvv = edt_cvv.text.toString().trim()
            val p_id = product.pId!!
            val p_rent = product.pRent!!

            if(b_card.isNullOrEmpty())
                applicationContext.toast("Enter Card No")
            else if(b_name.isNullOrEmpty())
                applicationContext.toast("Enter Name")
            else if(b_month.equals("Month", true))
                applicationContext.toast("Select Month")
            else if(b_year.equals("Year", true))
                applicationContext.toast("Select Year")
            else if(b_cvv.isNullOrEmpty())
                applicationContext.toast("Enter CVV")
            else
                bookProduct(p_id, p_rent, b_name, b_card, b_month, b_year, b_cvv)
        }

    }

    private fun bookProduct(pId: String, pRent:String, bName: String, bCard: String, bMonth: String, bYear: String, bCvv: String) {
        var request: StringRequest = object : StringRequest(Method.POST, Keys.Tenant.TENANT_BOOK_PRODUCT, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1"))
                finish()
            applicationContext.toast(json.optString("message"))
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("u_id", SharedPreference.get("u_id"))
                params.put("p_id", pId)
                params.put("or_amount", pRent)
                params.put("or_card_no", bCard)
                params.put("or_card_month", bMonth)
                params.put("or_card_year", bYear)
                params.put("or_card_cvv", bCvv)
                params.put("or_card_name", bName)
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