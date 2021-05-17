package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.utils.ElectionsLocalRepository

class VoterInfoFragment : Fragment() {

    private lateinit var viewModel: VoterInfoViewModel
    private lateinit var binding: FragmentVoterInfoBinding

    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_voter_info,
                container,
                false
        )

        return binding.root

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        activity?.let {
            val database = ElectionDatabase.getInstance(it)
            val electionDao = database.electionDao
            val localDataSource: ElectionsDataSource = ElectionsLocalRepository(electionDao)
            viewModel = VoterInfoViewModel(it,electionDao)
            viewModel.loadVoterInfo(args.argElectionId.toString())
            //viewModel.verifyLocalElection(args.argElectionId.toString())
            binding.viewModel = viewModel

            Log.d("FLUX",args.argElectionId.toString())
            Log.d("FLUX","Fragment "+ viewModel._voterInfo.value?.state?.get(0)?.name.toString())
            viewModel._voterInfo.observe(viewLifecycleOwner, Observer {
                Log.d("FLUX","Fragment 2 "+ it.state?.get(0)?.name.toString())
                binding.voterInfo = viewModel._voterInfo.value
            })
        }
    }

    //TODO: Create method to load URL intents

}