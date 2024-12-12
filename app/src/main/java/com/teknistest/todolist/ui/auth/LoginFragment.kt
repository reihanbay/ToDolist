package com.teknistest.todolist.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.teknistest.todolist.R
import com.teknistest.todolist.data.model.LoginRequest
import com.teknistest.todolist.data.remote.ApiClient
import com.teknistest.todolist.data.remote.ApiService
import com.teknistest.todolist.databinding.FragmentLoginBinding
import com.teknistest.todolist.repository.AuthRepository
import com.teknistest.todolist.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiClient.getApiService<ApiService>()
        viewmodel = AuthViewModel(AuthRepository(apiService))
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewmodel.login(LoginRequest(username, password))
        }

        observe()

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun observe() {
        lifecycleScope.launch {
            viewmodel.loginRes.collect {
                if (it?.statusCode == 2110) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_todoListFragment)
                } else if (it?.statusCode == 401) {
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