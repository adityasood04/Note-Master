package com.example.notemaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.notemaster.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class registerFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}