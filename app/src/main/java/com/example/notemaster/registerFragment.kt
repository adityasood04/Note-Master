package com.example.notemaster

import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notemaster.databinding.FragmentRegisterBinding
import com.example.notemaster.models.UserRequest
import com.example.notemaster.utils.NetworkResult
import com.example.notemaster.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class registerFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        if (tokenManager.getToken() != null) {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getUserRequest()
                authViewModel.registerUser(userRequest)
            } else {
                showValidationErrors(validationResult.second)
            }

        }
        bindObservers()
    }

    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                txtEmail.text.toString(),
                txtPassword.text.toString(),
                txtUsername.text.toString()
            )
        }
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }


    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }

                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val userName = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, userName, password, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}