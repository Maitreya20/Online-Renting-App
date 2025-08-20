package com.college.tenant

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import com.college.erentapp.R
import com.college.util.SharedPreference
import com.college.util.toast
import kotlinx.android.synthetic.main.activity_tenant_otp.*

class TenantOtpActivity : AppCompatActivity() {
    val otp = (100000..999999).random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_otp)

        sendOtp()

        btn_tenant_submit.setOnClickListener {
            val uOtp = edt_tenant_otp.text.toString().trim()
            if(uOtp.isNullOrEmpty())
                applicationContext.toast("Please enter OTP")
            else
                verifyOtp(uOtp)
        }

    }

    private fun sendOtp() {
        val msg = "Your OTP to login ERent Application is : $otp"
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(SharedPreference.get("u_phone"), null, msg, null, null)
    }

    private fun verifyOtp(uOtp: String) {

        if(otp.toString().equals(uOtp)){
            startActivity(Intent(this, TenantDashboard::class.java))
            finish()
        }else
            applicationContext.toast("Please enter correct OTP")
    }
}