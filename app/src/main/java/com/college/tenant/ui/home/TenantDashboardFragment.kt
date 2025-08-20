package com.college.tenant.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.college.erentapp.databinding.FragmentOwnerDashboardBinding
import com.college.erentapp.databinding.FragmentTenantDashboardBinding
import com.college.tenant.TenantDashboard
import com.college.tenant.TenantVehicleList

class TenantDashboardFragment : Fragment() {

    private var _binding: FragmentTenantDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTenantDashboardBinding.inflate(inflater, container, false)
        binding.cdTenantCar.setOnClickListener {
            val intent = Intent(activity, TenantVehicleList::class.java)
            intent.putExtra("p_type", "1")
            intent.putExtra("p_type_name", "Car List")
            startActivity(intent)
        }
        binding.cdTenantBike.setOnClickListener {
            val intent = Intent(activity, TenantVehicleList::class.java)
            intent.putExtra("p_type", "2")
            intent.putExtra("p_type_name", "Bike List")
            startActivity(intent)
        }
        binding.cdTenantScooty.setOnClickListener {
            val intent = Intent(activity, TenantVehicleList::class.java)
            intent.putExtra("p_type", "3")
            intent.putExtra("p_type_name", "Scooty List")
            startActivity(intent)
        }
        binding.cdTenantHouse.setOnClickListener {
            val intent = Intent(activity, TenantVehicleList::class.java)
            intent.putExtra("p_type", "4")
            intent.putExtra("p_type_name", "Home List")
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}