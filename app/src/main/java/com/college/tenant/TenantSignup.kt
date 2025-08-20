package com.college.tenant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityTenantSignupBinding
import com.college.owner.OwnerLogin
import com.college.util.*
import org.json.JSONObject

class TenantSignup : AppCompatActivity() {
    private lateinit var binding: ActivityTenantSignupBinding
    var tenant_name: String? = null
    var tenant_email: String? = null
    var tenant_phone: String? = null
    var tenant_password: String? = null
    var tenant_confirm_password: String? = null
    var tenant_address: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_tenant_signup)
        binding = ActivityTenantSignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.title = "Sign up"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

        binding.btnTenantSignup.setOnClickListener {
            tenant_name = binding.edtTenantName.text.toString().trim()
            tenant_email = binding.edtTenantEmail.text.toString().trim()
            tenant_password = binding.edtTenantPass.text.toString().trim()
            tenant_confirm_password = binding.edtTenantConfirmPass.text.toString().trim()
            tenant_phone = binding.edtTenantPhone.text.toString().trim()
            tenant_address = binding.edtTenantAddress.text.toString().trim()

            if(tenant_name.equals("") || tenant_email.equals("") || tenant_password.equals("") ||
                tenant_confirm_password.equals("") || tenant_phone.equals("") || tenant_address.equals("")){
                applicationContext.toast("Please fill all fields")
            }else if(!tenant_password.equals(tenant_confirm_password)){
                applicationContext.toast("Password and confirm password must be same")
            }else {
                tenantSignUp(tenant_name!!, tenant_email!!, tenant_password!!, tenant_phone!!, tenant_address!!
                )
            }
            /*val intent = Intent(this@TenantSignup, TenantLogin::class.java)
            startActivity(intent)
            finish()*/
        }

    }

    private fun tenantSignUp(tenantName: String, tenantEmail: String, tenantPassword: String, tenantPhone: String, tenantAddress: String) {
        val request : StringRequest = object : StringRequest(Request.Method.POST, Keys.Tenant.TENANT_REGISTER,
            Response.Listener { response ->
                applicationContext.log(response)
                val json = JSONObject(response)
                applicationContext.toast(json.optString("message"))
                if(json.optString("success").equals("1")){
                    finish()
                }
            },
        Response.ErrorListener {
            error ->
            error.printStackTrace()
            Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): MutableMap<String, String>? {
                var params : MutableMap<String, String> = HashMap()
                params.put("u_name", tenantName)
                params.put("u_email", tenantEmail)
                params.put("u_phone", tenantPhone)
                params.put("u_password", tenantPassword)
                params.put("u_address", tenantAddress)
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