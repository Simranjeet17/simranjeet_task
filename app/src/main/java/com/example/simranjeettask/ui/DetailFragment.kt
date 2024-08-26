package com.example.simranjeettask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.simranjeettask.R
import com.example.simranjeettask.databinding.FragmentDetailBinding
import com.example.simranjeettask.model.Restaurant
import com.example.simranjeettask.utils.CommonFunctions
import com.example.simranjeettask.webservices.Keys

class DetailFragment : Fragment(),View.OnClickListener {
    lateinit var binding:FragmentDetailBinding
    private lateinit var restaurant: Restaurant
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            restaurant = it.getParcelable(Keys.RESTAURANT_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(restaurant.image_url).into(binding.ivdetail)
        binding.tvRestaurantName.text = restaurant.name
        binding.tvAddress.text = restaurant.address
        binding.tvPhone.text = restaurant.phone_number
        binding.ivBack.setOnClickListener(this)

        when(restaurant.id){
            1->binding.tvContinental.text= getString(R.string.american)
            2->binding.tvContinental.text= getString(R.string.italian)
            3->binding.tvContinental.text= getString(R.string.asian)
            4->binding.tvContinental.text= getString(R.string.mexican)
        }


    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->{
                CommonFunctions.replaceFragments(requireActivity(),DashboardFragment())
            }
        }
    }
}