package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Official
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives : LiveData<List<Representative>>
        get() = _representatives
    private val _response = MutableLiveData<RepresentativeResponse>()
    private val _address = MutableLiveData<Address>()
    val address : LiveData<Address>
        get() = _address
    //val showLoading = MutableLiveData<Boolean>()

   /*
    suspend fun getRepresentatives2() {
        viewModelScope.launch {
            val getRepresentativesDeferred =  async { _address.value?.toFormattedString()?.let { CivicsApi.retrofitService.getRepresentatives(it) }}
            val (offices, officials) = getRepresentativesDeferred.await()
            _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

        }

    */

    fun getRepresentatives(){
        //showLoading.value = true
        _representatives.value = listOf()
        viewModelScope.launch {
            _address.value?.toFormattedString()?.let {
                Log.d("FLUX","ADDRESS VM "+it)
            }
            _response.value = _address.value?.toFormattedString()?.let { CivicsApi.retrofitService.getRepresentatives(it) }

            for (office in _response.value?.offices!!){
                Log.d("FLUX","OFFICE "+office)
                for (representativeList in office.getRepresentatives(_response.value!!.officials)) {
                    Log.d("FLUX","OFFICIAL "+representativeList.official.name)
                    _representatives.value = _representatives.value?.plus(representativeList)
                }
            }
           // showLoading.postValue(false)
            Log.d("FLUX","Representative size "+_representatives.value?.size)
        }

    }

    fun setAddress(address: Address){
        _address.value = address
    }

    fun formatAndSetAddress(line1: String, line2: String, city: String, state: String, zip: String){
        val address = Address(line1,line2,city,state,zip)
        _address.value = address
    }


    //TODO: Establish live data for representatives and address

    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
