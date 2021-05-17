package com.example.android.politicalpreparedness.election

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch

class VoterInfoViewModel(val context: Context, private val dataSource: ElectionDao) : ViewModel() {

    val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val _isElectionSaved = MutableLiveData<Boolean>()

    fun goToVotingLocations(){
        val intent = Intent(ACTION_VIEW, Uri.parse(
                _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
        )).apply {
            // The URL should either launch directly in a non-browser app (if it's
            // the default), or in the disambiguation dialog.
            addCategory(CATEGORY_BROWSABLE)
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
        }
        startActivity(context,intent,null)
    }

    fun goToBallotInformation(){
        val intent = Intent(ACTION_VIEW, Uri.parse(
                _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
        )).apply {
            // The URL should either launch directly in a non-browser app (if it's
            // the default), or in the disambiguation dialog.
            addCategory(CATEGORY_BROWSABLE)
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
        }
        startActivity(context,intent,null)
    }

    fun loadVoterInfo(electionId: String){
        viewModelScope.launch {
            _voterInfo.value = CivicsApi.retrofitService.getVoterInfoResponse(electionId,"1263%20Pacific%20Ave.%20Kansas%20City%20KS")
            Log.d("FLUX","State" + _voterInfo.value?.state?.get(0)?.name)
            Log.d("FLUX","State" + _voterInfo.value?.election?.name)
            verifyLocalElection(_voterInfo.value?.election?.id.toString())
        }
    }

    fun saveRemoveElection(){
        viewModelScope.launch {
            verifyLocalElection(_voterInfo.value?.election?.id.toString())
            if (_isElectionSaved.value!!){
                _voterInfo.value?.election?.let {
                    dataSource.deleteElection(it.id)
                    verifyLocalElection(it.id.toString())
                }
            }else{
                _voterInfo.value?.election?.let {
                    dataSource.saveElection(it)
                    verifyLocalElection(it.id.toString())
                }
            }
        }
    }

    fun verifyLocalElection(electionId: String){
        viewModelScope.launch {
            val election = dataSource.getElectionById(electionId.toInt())
            _isElectionSaved.value = election != null
        }
    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}