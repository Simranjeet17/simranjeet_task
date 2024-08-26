package com.example.simranjeettask.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.collection.ArrayMap
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.simranjeettask.R
import com.example.simranjeettask.databinding.FragmentLoginBinding
import com.example.simranjeettask.utils.CommonFunctions
import com.example.simranjeettask.utils.CommonFunctions.showToast
import com.example.simranjeettask.viewmodel.LoginViewModel
import com.example.simranjeettask.webservices.Keys
import com.example.simranjeettask.webservices.Status
import kotlinx.coroutines.launch

class LoginFragment : Fragment(),View.OnClickListener {
    lateinit var binding: FragmentLoginBinding
    private  lateinit var viewModel: LoginViewModel
    private var maps = ArrayMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        initListener()

        initObserver()
        setupBackpress()

    }
    fun initObserver() {
        lifecycleScope.launch {
            viewModel!!.getLogin.collect{
                when(it.status){
                    Status.LOADING->{
                    }
                    Status.SUCCESS->{
                        if(it.data!!.success == true){
                            showToast(requireContext(),it.data.message.toString())
                            CommonFunctions.replaceFragments(requireActivity(),DashboardFragment())

                        }
                        else{
                            if(it.data.success ==false){
                                showToast(requireContext(),it.data.message.toString())
                            }
                            else{
                                showToast(requireContext(),"Something went wrong!")
                            }

                        }
                    }
                    else -> {
                        if(Status.NONE!=it.status){
                            if(it.status.equals("false")){
                                showToast(requireContext(),it.data!!.message.toString())
                            }
                            if (it.message?.contains("html") == true) {

                                showToast(requireContext(),"Something went wrong!")
                            }

                            if (it.message == "HTTP 401 Unauthorized"){
                                showToast(requireContext(),"Something went wrong!")
                            }else{
//                                Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                }
            }
        }
    }

    fun initListener() {
        binding.tvLogin.setOnClickListener(this)
        binding.txtSign.setOnClickListener(this)
    }

    private fun setupBackpress() {
        val dispatcher = requireActivity().onBackPressedDispatcher
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    finishAffinity(requireActivity())

                }
            }

        dispatcher.addCallback(requireActivity(), callback)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txtSign->{
                CommonFunctions.replaceFragments(requireActivity(),SignUpFragment())

            }
            R.id.tvLogin->{
                callApi()
            }

        }
    }

    fun callApi(){
        if(binding.email.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter email address")
        }
        if(binding.password.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter password")
        }
        else{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString()
            //TODO
            if (email.isNullOrEmpty() || password.isNotEmpty()){
                maps.clear()
                maps[Keys.EMAIL] = email
                maps[Keys.PASSWORD] = password
                maps[Keys.DEVICE_TOKEN] = Keys.DEVICE_TOKEN_VALUE
                maps[Keys.DEVICE_TYPE] = Keys.DEVICE_TYPE_VALUE
                viewModel!!.getlogin(maps)
            }
            else{
                showToast(requireContext(),"Something went wrong!")

            }

        }

    }
}


