package com.android.example.androidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.androidproject.database.MemoriesDatabase
import com.android.example.androidproject.database.MemoryEntity
import com.android.example.androidproject.databinding.FragmentGalleryBinding
import com.android.example.androidproject.memories.MemoriesViewModel
import com.android.example.androidproject.memories.MemoriesViewModelFactory
import com.android.example.androidproject.memories.MemoryEntityAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GalleryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GalleryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var memoriesList : MutableList<MemoryEntity> = ArrayList()
    private lateinit var adapter: MemoryEntityAdapter
    private lateinit var memoriesViewModel: MemoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGalleryBinding>(
            inflater,
            R.layout.fragment_gallery, container, false
        )

        adapter = MemoryEntityAdapter(memoriesList)
        binding.memoriesList.adapter = adapter

        val application = requireNotNull(this.activity).application
        val dataSource = MemoriesDatabase.getInstance(application).memoriesDatabaseDao
        val viewModelFactory = MemoriesViewModelFactory(dataSource, application)
        memoriesViewModel = ViewModelProvider(this, viewModelFactory)[MemoriesViewModel::class.java]
        memoriesViewModel.memories.observe(viewLifecycleOwner, Observer { item ->
            updateRecyclerView(item, "")
        })

        binding.searchMemories.doOnTextChanged { text, start, before, count ->
            memoriesViewModel.memories.value?.let { updateRecyclerView(it, text.toString()) }
        }

        binding.setLifecycleOwner(this)
        binding.memoriesViewModel = memoriesViewModel
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateRecyclerView(memories: List<MemoryEntity>, searchQuery: String) {
        memoriesList.clear()
        memoriesList.addAll(memories.filter{ it.description.lowercase().contains(searchQuery.lowercase()) })
        adapter.notifyDataSetChanged()
    }
}