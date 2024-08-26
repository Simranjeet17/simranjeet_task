package com.example.simranjeettask.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demotask.adapter.MyAdapter
import com.example.demotask.adapter.MyAdapter1
import com.example.demotask.adapter.MyAdapter2
import com.example.demotask.adapter.MyAdapter3
import com.example.simranjeettask.databinding.FragmentDashboardBinding
import com.example.simranjeettask.model.Restaurant
import com.example.simranjeettask.model.categoryItem
import com.example.simranjeettask.utils.CommonFunctions
import com.example.simranjeettask.utils.CommonFunctions.showToast
import com.example.simranjeettask.viewmodel.LoginViewModel
import com.example.simranjeettask.webservices.Keys
import com.example.simranjeettask.webservices.Status
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(),MyAdapter.OnItemClicked,MyAdapter1.OnItemClicked,MyAdapter2.OnItemClicked,MyAdapter3.OnItemClicked{
    lateinit var binding: FragmentDashboardBinding
    private  lateinit var viewModel: LoginViewModel
    private lateinit var adapter: MyAdapter
    private lateinit var adapter1: MyAdapter1
    private lateinit var adapter2: MyAdapter2
    private lateinit var adapter3: MyAdapter3
    var categories: List<categoryItem> = emptyList()
    val americanRestaurants = ArrayList<Restaurant>()
    val italianRestaurants = ArrayList<Restaurant>()
    val asianRestaurants = ArrayList<Restaurant>()
    val mexicanRestaurants = ArrayList<Restaurant>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.getCategory()
        initObserver()
        binding.etQuote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            performSearch(s.toString())
                        }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


    }
    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.category.collect {
                when (it.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        if (!it.data.isNullOrEmpty()) {

                             categories = it.data
                            for (category in categories) {
                                category.restaurant?.let { restaurantList ->
                                    when (category.name) {
                                        "American" -> {
                                            americanRestaurants.clear()
                                            americanRestaurants.addAll(restaurantList)
                                            if(americanRestaurants.isNotEmpty()){
                                                adapter= MyAdapter(requireContext(),americanRestaurants,this@DashboardFragment)
                                                binding.rvMexican.adapter=adapter
                                            } else {
                                                showToast(requireContext(), "american list is null or empty")
                                            }

                                        }
                                        "Mexican" -> {
                                            mexicanRestaurants.clear()
                                            mexicanRestaurants.addAll(restaurantList)
                                            if(mexicanRestaurants.isNotEmpty()){
                                                adapter1= MyAdapter1(requireContext(),mexicanRestaurants,this@DashboardFragment)
                                                binding.rvMexican2.adapter=adapter1
                                            } else {
                                                showToast(requireContext(), "mexican list is null or empty")
                                            }

                                        }
                                        "Italian" -> {
                                            italianRestaurants.clear()
                                            italianRestaurants.addAll(restaurantList)
                                            if(italianRestaurants.isNotEmpty()){
                                                adapter2= MyAdapter2(requireContext(),italianRestaurants,this@DashboardFragment)
                                                binding.italian.adapter=adapter2
                                            } else {
                                                showToast(requireContext(), "Italian list is null or empty")
                                            }
                                        }
                                        "Asian" -> {
                                            asianRestaurants.clear()
                                            asianRestaurants.addAll(restaurantList)
                                            if(asianRestaurants.isNotEmpty()){
                                                adapter3= MyAdapter3(requireContext(),asianRestaurants,this@DashboardFragment)
                                                binding.rvAsian.adapter=adapter3
                                            } else {
                                                showToast(requireContext(), "Asian list is null or empty")
                                            }
                                        }
                                        else -> {}
                                    }
                                }
                            }

                            Log.d("DashboardFragment", "American Restaurants: $americanRestaurants")
                            Log.d("DashboardFragment", "Italian Restaurants: $italianRestaurants")
                            Log.d("DashboardFragment", "Asian Restaurants: $asianRestaurants")

                            // Show a toast with the first category name (for testing)
                            Toast.makeText(requireContext(), categories[0].name, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.ERROR -> {
                        it.message?.let { message ->
                            if (message.contains("html")) {
                                showToast(requireContext(), "Something went wrong")
                            } else if (message == "HTTP 401 Unauthorized") {
                                showToast(requireContext(), "Unauthorized access")
                            } else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else -> {
                        // Handle other states if necessary
                    }
                }
            }
        }
    }



    override fun onItemClick(position: Int, restaurant: Restaurant) {
        val detailFragment = DetailFragment()
        val bundle = Bundle().apply {
            putParcelable(Keys.RESTAURANT_KEY, restaurant)  // Use the key to retrieve it later
        }
        detailFragment.arguments = bundle
        CommonFunctions.replaceFragments(requireActivity(), detailFragment)
    }

    fun performSearch(text: String) {
        // Filter each list separately
        val filteredAmerican = if (text.isEmpty()) americanRestaurants else americanRestaurants.filter { restaurant ->
            restaurant.name.contains(text, ignoreCase = true) || restaurant.address.contains(text, ignoreCase = true)
        }

        val filteredItalian = if (text.isEmpty()) italianRestaurants else italianRestaurants.filter { restaurant ->
            restaurant.name.contains(text, ignoreCase = true) || restaurant.address.contains(text, ignoreCase = true)
        }

        val filteredAsian = if (text.isEmpty()) asianRestaurants else asianRestaurants.filter { restaurant ->
            restaurant.name.contains(text, ignoreCase = true) || restaurant.address.contains(text, ignoreCase = true)
        }

        val filteredMexican = if (text.isEmpty()) mexicanRestaurants else mexicanRestaurants.filter { restaurant ->
            restaurant.name.contains(text, ignoreCase = true) || restaurant.address.contains(text, ignoreCase = true)
        }

        if (this::adapter.isInitialized) {
            adapter.setData(filteredAmerican)
            adapter1.setData(filteredMexican)
            adapter2.setData(filteredItalian)
            adapter3.setData(filteredAsian)
        }
    }

    override fun onItemClick1(position: Int, restaurant: Restaurant) {
        val detailFragment = DetailFragment()
        val bundle = Bundle().apply {
            putParcelable(Keys.RESTAURANT_KEY, restaurant)  // Use the key to retrieve it later
        }
        detailFragment.arguments = bundle
        CommonFunctions.replaceFragments(requireActivity(), detailFragment)
    }

    override fun onItemClick2(position: Int, restaurant: Restaurant) {
        val detailFragment = DetailFragment()
        val bundle = Bundle().apply {
            putParcelable(Keys.RESTAURANT_KEY, restaurant)  // Use the key to retrieve it later
        }
        detailFragment.arguments = bundle
        CommonFunctions.replaceFragments(requireActivity(), detailFragment)
    }

    override fun onItemClick3(position: Int, restaurant: Restaurant) {
        val detailFragment = DetailFragment()
        val bundle = Bundle().apply {
            putParcelable(Keys.RESTAURANT_KEY, restaurant)  // Use the key to retrieve it later
        }
        detailFragment.arguments = bundle
        CommonFunctions.replaceFragments(requireActivity(), detailFragment)
    }


}