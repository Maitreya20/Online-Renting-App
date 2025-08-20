package com.college.tenant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.college.erentapp.R
import com.college.erentapp.databinding.ActivityTenantDashboardBinding
import com.college.util.AppController
import com.college.util.SharedPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tenant_dashboard.view.*
import kotlinx.android.synthetic.main.nav_header_tenant_dashboard.view.*


class TenantDashboard : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTenantDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTenantDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarTenantDashboard.toolbar)

        AppController.initialize(applicationContext)
        SharedPreference.initialize(applicationContext)

     /*   binding.appBarTenantDashboard.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_tenant_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_tenant_dashboard, R.id.nav_my_booking
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*binding.drawerLayout.nav_view.getHeaderView(0).tv_owner_name.text = SharedPreference.get("u_name")
        binding.drawerLayout.nav_view.getHeaderView(0).tv_owner_email.text = SharedPreference.get("u_email")*/

        binding.drawerLayout.nav_view.getHeaderView(0).tv_tenant_name.text = SharedPreference.get("u_name")
        binding.drawerLayout.nav_view.getHeaderView(0).tv_tenant_email.text = SharedPreference.get("u_email")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.tenant_dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_tenant_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_tenant_logout -> {
                val intent = Intent(this@TenantDashboard, TenantLogin::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}