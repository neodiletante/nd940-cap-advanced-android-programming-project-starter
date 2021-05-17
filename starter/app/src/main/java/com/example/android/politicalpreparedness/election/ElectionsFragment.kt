package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.utils.ElectionsLocalRepository

class ElectionsFragment: Fragment() {

    private lateinit var viewModel: ElectionsViewModel
    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_election,
                container,
                false
        )

        return binding.root




        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        val adapter = ElectionListAdapter(ElectionListener {
                Log.d("FLUX", "click listener "+ it)
                val action = ElectionsFragmentDirections
                        .actionElectionsFragmentToVoterInfoFragment(it, Division("","",""))
                findNavController().navigate(action)
            })

        val savedAdapter = ElectionListAdapter(ElectionListener {
            Log.d("FLUX", "saved click listener "+ it)
            val action = ElectionsFragmentDirections
                    .actionElectionsFragmentToVoterInfoFragment(it, Division("","",""))
            findNavController().navigate(action)
        })

        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.upcomingElections.layoutManager = layoutManager
        binding.upcomingElections.adapter = adapter

        val savedLayoutManager = LinearLayoutManager(this.context)
        savedLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.savedElections.layoutManager = savedLayoutManager
        binding.savedElections.adapter = savedAdapter

        activity?.let {
            val database = ElectionDatabase.getInstance(it)
            val electionDao = database.electionDao
            val localDataSource: ElectionsDataSource = ElectionsLocalRepository(electionDao)
            viewModel = ElectionsViewModel(localDataSource)
            binding.viewModel = viewModel

            viewModel.loadRemoteElections()
            viewModel.loadSavedElections()

            //viewModel.loadRemoteElections()
        }

        viewModel.savedElections.observe(viewLifecycleOwner, Observer {
            (binding.savedElections.adapter as ElectionListAdapter).notifyDataSetChanged()
            Log.d("FLUX","notify data changed")
            Log.d("FLUX", viewModel.savedElections.value?.size.toString())
        })

        viewModel.showError.observe(viewLifecycleOwner, Observer {
            viewModel.erroeShown()
            Toast.makeText(activity,R.string.get_elections_error,Toast.LENGTH_SHORT).show()
        })


    }

    //TODO: Refresh adapters when fragment loads

}