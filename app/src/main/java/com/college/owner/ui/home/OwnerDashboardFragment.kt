package com.college.owner.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.college.erentapp.databinding.FragmentOwnerDashboardBinding
import com.college.owner.OwnerVehicleList

class OwnerDashboardFragment : Fragment() {

    private var _binding: FragmentOwnerDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOwnerDashboardBinding.inflate(inflater, container, false)
        binding.cdCar.setOnClickListener {
            val intent = Intent(activity, OwnerVehicleList::class.java)
            intent.putExtra("p_type", "1")
            intent.putExtra("p_type_name", "Car List")
            startActivity(intent)
        }
        binding.cdBike.setOnClickListener {
            val intent = Intent(activity, OwnerVehicleList::class.java)
            intent.putExtra("p_type", "2")
            intent.putExtra("p_type_name", "Bike List")
            startActivity(intent)
        }
        binding.cdScooty.setOnClickListener {
            val intent = Intent(activity, OwnerVehicleList::class.java)
            intent.putExtra("p_type", "3")
            intent.putExtra("p_type_name", "Scooty List")
            startActivity(intent)
        }
        binding.cdHouse.setOnClickListener {
            val intent = Intent(activity, OwnerVehicleList::class.java)
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