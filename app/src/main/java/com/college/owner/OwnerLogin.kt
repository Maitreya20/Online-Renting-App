package com.college.owner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityOwnerLoginBinding
import com.college.erentapp.databinding.ActivityTenantSignupBinding
import com.college.util.*
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONObject

class OwnerLogin : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerLoginBinding
    var owner_phone: String? = null
    var owner_password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_owner_login)
        binding = ActivityOwnerLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        binding.txtOwnerRegister.setOnClickListener {
            val intent = Intent(this@OwnerLogin, OwnerSignup::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOwnerLogin.setOnClickListener {
            owner_phone = binding.edtOwnerLoginPhone.text.toString().trim()
            owner_password = binding.edtOwnerLoginPassword.text.toString().trim()

            if(owner_phone.equals("") || owner_password.equals(""))
                applicationContext.toast("Please fill all fields")
            else if(checkPermissionStatus())
                ownerLogin(owner_phone!!, owner_password!!)

            /*val intent = Intent(this@OwnerLogin, OwnerDashboard::class.java)
            startActivity(intent)
            finish()*/
        }

    }

    private fun checkPermissionStatus(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
                return false
            }else
                return true
        }else
            return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                applicationContext.toast("Permission accepted. Click on login now.")
            else
                applicationContext.toast("Permission required to use the application.")
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun ownerLogin(ownerPhone: String, ownerPassword: String) {
        val request : StringRequest = object : StringRequest(Method.POST, Keys.Owner.OWNER_LOGIN, Response.Listener {
            response ->
            applicationContext.log(response)

            val json = JSONObject(response)
            if(json.optString("success").equals("1")){
                val data = json.optJSONObject("data")
                SharedPreference.save("o_id", data.optString("o_id"))
                SharedPreference.save("o_name", data.optString("o_name"))
                SharedPreference.save("o_phone", data.optString("o_phone"))
                SharedPreference.save("o_email", data.optString("o_email"))
                SharedPreference.save("o_address", data.optString("o_address"))

                startActivity(Intent(this, OwnerDashboard::class.java))
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
                params.put("o_phone", ownerPhone)
                params.put("o_password", ownerPassword)
                return params
            }
        }

        AppController.getInstance().add(request)
    }
}