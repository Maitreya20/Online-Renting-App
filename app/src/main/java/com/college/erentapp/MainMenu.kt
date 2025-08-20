package com.college.erentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.college.erentapp.databinding.ActivityMainMenuBinding
import com.college.erentapp.databinding.ActivityTenantSignupBinding
import com.college.owner.OwnerLogin
import com.college.tenant.TenantLogin

class MainMenu : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.cdOwner.setOnClickListener {
            val intent = Intent(this@MainMenu, OwnerLogin::class.java)
            startActivity(intent)
        }

        binding.cdTenant.setOnClickListener {
            val intent = Intent(this@MainMenu, TenantLogin::class.java)
            startActivity(intent)
        }
    }
}