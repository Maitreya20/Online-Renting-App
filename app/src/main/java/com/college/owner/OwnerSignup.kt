package com.college.owner

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityOwnerSignupBinding
import com.college.util.*
import org.json.JSONObject

class OwnerSignup : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerSignupBinding
    var owner_name: String? = null
    var owner_email: String? = null
    var owner_phone: String? = null
    var owner_password: String? = null
    var owner_confirm_password: String? = null
    var owner_address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_owner_signup)
        binding = ActivityOwnerSignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.title = "Sign up"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        binding.btnOwnerSignup.setOnClickListener {
            owner_name = binding.edtOwnerName.text.toString().trim()
            owner_email = binding.edtOwnerEmail.text.toString().trim()
            owner_password = binding.edtOwnerPass.text.toString().trim()
            owner_confirm_password = binding.edtOwnerConfirmPass.text.toString().trim()
            owner_phone = binding.edtOwnerPhone.text.toString().trim()
            owner_address = binding.edtOwnerAddress.text.toString().trim()

            if(owner_name.equals("") || owner_email.equals("") || owner_password.equals("") ||
                    owner_confirm_password.equals("") || owner_address.equals("") || owner_phone.equals("")){
                applicationContext.toast("Please fill all fields")
            }else if(!owner_password.equals(owner_confirm_password))
                applicationContext.toast("Password & Confirm Password not matching")
            else
                ownerRegister(owner_name!!, owner_email!!, owner_phone!!, owner_password!!, owner_address!!)
        }

    }

    private fun ownerRegister(ownerName: String, ownerEmail: String, ownerPhone: String, ownerPassword: String, ownerAddress: String) {
        val request : StringRequest = object : StringRequest(Method.POST, Keys.Owner.OWNER_REGISTER, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                finish()
            }
            applicationContext.toast(json.optString("message"))
        }, Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("o_name", ownerName)
                params.put("o_email", ownerEmail)
                params.put("o_phone", ownerPhone)
                params.put("o_password", ownerPassword)
                params.put("o_address", ownerAddress)
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