package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.sql.DataSource

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
class ElectionsViewModelFactory(

        val localDataSource: ElectionsDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ElectionsViewModel(localDataSource) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}