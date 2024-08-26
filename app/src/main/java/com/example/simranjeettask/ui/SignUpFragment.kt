package com.example.simranjeettask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.collection.ArrayMap
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.simranjeettask.R
import com.example.simranjeettask.databinding.FragmentLoginBinding
import com.example.simranjeettask.databinding.FragmentSplashBinding
import com.example.simranjeettask.utils.CommonFunctions.showToast
import com.example.simranjeettask.viewmodel.LoginViewModel
import com.example.simranjeettask.webservices.Keys
import com.example.simranjeettask.webservices.Status
import kotlinx.coroutines.launch

class SignUpFragment : Fragment(),View.OnClickListener {
   lateinit var binding:FragmentSplashBinding
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
        binding=FragmentSplashBinding.inflate(layoutInflater)
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
            viewModel!!.getSignUp.collect{
                when(it.status){
                    Status.LOADING->{
                    }
                    Status.SUCCESS->{
                        if(it.data!!.success == true){
                            showToast(requireContext(),it.data.message.toString())
                            replaceFragments(LoginFragment())

                        }
                        else{
                            if(it.data.success ==false){
                                showToast(requireContext(),it.data.message.toString())
                            }
                            else{
                                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                    else -> {
                        if(Status.NONE!=it.status){
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
        binding.tvSignUp.setOnClickListener(this)
        binding.textlogin.setOnClickListener(this)
    }
    private fun replaceFragments(fragment: Fragment){
        parentFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fragment,"1").addToBackStack("hi").commit()
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
            R.id.textlogin->{
                replaceFragments(LoginFragment())

            }
            R.id.tvSignUp->{
                callApi()
            }

        }
    }

    fun callApi(){
        if(binding.fullname.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter name")
        }
        if(binding.etEmail.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter email")
        }
        if(binding.number.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter number")
        }
        if(binding.etPassword.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter Password")
        }
        if(binding.etConfirmPassword.text.isNullOrEmpty()){
            showToast(requireContext(),"Enter Confirm Password")
        }
        else{
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val number = binding.number.text.toString()
            val name = binding.fullname.text.toString()
            val ctpassword = binding.etConfirmPassword.text.toString()
            if(password!=ctpassword){
                showToast(requireContext(),"Password must be same")
            }
            else if(!binding.cbBox.isChecked){
                showToast(requireContext(),"Accept terms and conditions")
            }
            else{
                if (email.isNullOrEmpty() || password.isNotEmpty() ||number.isNotEmpty() || name.isNotEmpty()){
                    maps.clear()
                    maps[Keys.EMAIL] = email
                    maps[Keys.PASSWORD] = password
                    maps[Keys.PHONE_NUMBER] = number
                    maps[Keys.NAME] = name
                    maps[Keys.DEVICE_TOKEN] = Keys.DEVICE_TOKEN_VALUE
                    maps[Keys.DEVICE_TYPE] = Keys.DEVICE_TYPE_VALUE
                    viewModel!!.SignUp(maps)
                }
                else{
                    showToast(requireContext(),"Something went wrong!")
                }
            }
            //TODO


        }

    }
}