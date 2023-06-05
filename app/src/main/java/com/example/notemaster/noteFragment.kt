package com.example.notemaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notemaster.databinding.FragmentNoteBinding
import com.example.notemaster.models.NoteRequest
import com.example.notemaster.models.NoteResponse
import com.example.notemaster.utils.NetworkResult
import com.google.gson.Gson


class noteFragment : Fragment() {


    private var _binding :FragmentNoteBinding? =null
     private val binding = _binding!!
    private  var note: NoteResponse? = null
    private val noteViewModel  by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setIntialData()

        bindObservers()
        bindHandlers()
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let { noteViewModel.deleteNote(it!!._id) }
        }
        binding.apply {
            btnSubmit.setOnClickListener {
                val title = txtTitle.text.toString()
                val description = txtDescription.text.toString()
                val noteRequest = NoteRequest(title, description)
                if (note == null) {
                    noteViewModel.createNote(noteRequest)
                } else {
                    noteViewModel.updateNote(note!!._id, noteRequest)
                }
            }
        }
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun setIntialData() {
        val jsonNote = arguments?.getString("note")
        if(jsonNote!=null){
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            binding.txtTitle.setText(note!!.title)
            binding.txtDescription.setText(note!!.description)
        }
        else{
            binding.addEditText.text = "Add Note"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}