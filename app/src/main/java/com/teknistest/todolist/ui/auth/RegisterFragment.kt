package com.teknistest.todolist.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teknistest.todolist.R
import com.teknistest.todolist.data.model.RegisterRequest
import com.teknistest.todolist.data.remote.ApiClient
import com.teknistest.todolist.data.remote.ApiService
import com.teknistest.todolist.databinding.FragmentRegisterBinding
import com.teknistest.todolist.repository.AuthRepository
import com.teknistest.todolist.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel : AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiClient.getApiService<ApiService>()
        viewmodel = AuthViewModel(AuthRepository(apiService))
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewmodel.register(RegisterRequest(email, username, password))
        }
        observeRegister()

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeRegister() {
        lifecycleScope.launch {
            viewmodel.registerRes.collect {
                if (it?.statusCode == 2000) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else if (it?.statusCode == 400) {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}