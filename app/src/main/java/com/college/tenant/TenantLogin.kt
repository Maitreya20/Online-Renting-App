package com.college.tenant

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
import com.college.erentapp.databinding.ActivityTenantLoginBinding
import com.college.erentapp.databinding.ActivityTenantSignupBinding
import com.college.owner.OwnerLogin
import com.college.util.*
import org.json.JSONObject

class TenantLogin : AppCompatActivity() {
    private lateinit var binding: ActivityTenantLoginBinding
    var tenant_phone: String? = null
    var tenant_password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_tenant_login)

        binding = ActivityTenantLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        binding.txtTenantRegister.setOnClickListener {
            val intent = Intent(this@TenantLogin, TenantSignup::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnTenantLogin.setOnClickListener {
            val u_phone = binding.edtTenantLoginPhone.text.toString().trim()
            val u_password = binding.edtTenantLoginPassword.text.toString().trim()

            if(u_phone.equals("") || u_password.equals(""))
                applicationContext.toast("Please fill all fields")
            else if(checkOtpPermission())
                tenantLogin(u_phone, u_password)
            else
                applicationContext.toast("Please accept permission")

            /*val intent = Intent(this@TenantLogin, TenantDashboard::class.java)
            startActivity(intent)
            finish()*/

        }
    }

    private fun checkOtpPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 123)
                return false
            }else{
                return true
            }
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

    private fun tenantLogin(uPhone: String, uPassword: String) {
        val request : StringRequest = object : StringRequest(Method.POST, Keys.Tenant.TENANT_LOGIN, Response.Listener {
            response ->
            applicationContext.log(response)
            val json = JSONObject(response)

            if(json.optString("success").equals("1")){
                val data = json.optJSONObject("data")
                SharedPreference.save("u_id", data.optString("u_id"))
                SharedPreference.save("u_name", data.optString("u_name"))
                SharedPreference.save("u_email", data.optString("u_email"))
                SharedPreference.save("u_phone", data.optString("u_phone"))
                SharedPreference.save("u_address", data.optString("u_address"))

                startActivity(Intent(this, TenantOtpActivity::class.java))
                //startActivity(Intent(this, TenantDashboard::class.java))
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
                params.put("u_phone", uPhone)
                params.put("u_password", uPassword)
                return params
            }
        }

        AppController.getInstance().add(request)
    }
}