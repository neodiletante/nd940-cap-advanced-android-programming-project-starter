package com.example.android.politicalpreparedness.election

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(val localDataSource: ElectionsDataSource): ViewModel() {

    private val _dataLoaded =  MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean>
        get() = _dataLoaded

    private val _elections = MutableLiveData<List<Election>>()
    val elections: LiveData<List<Election>>
        get() = _elections

    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    val showLoading = MutableLiveData<Boolean>()

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError


    fun loadRemoteElections(){
       showLoading.value = true
        viewModelScope.launch {
            _elections.value = CivicsApi.retrofitService.getElectionResponse().elections
            showLoading.postValue(false)
            _dataLoaded.value = true
            if (_elections.value == null){
                Log.d("FLUX", "Error")
               _showError.value = true
            }else {
                Log.d("FLUX", "Elections size " + _elections.value!!.size)
            }
        }

    }

    fun loadSavedElections(){
        viewModelScope.launch {
            _savedElections.value = localDataSource.getElections()
            _dataLoaded.value = true
  //          Log.d("FLUX","saved size " +_savedElections.value?.size)
//            Log.d("FLUX","election " +_savedElections.value?.get(0)?.name)
        }
    }

    fun erroeShown(){
        _showError.value = false
    }

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info




}