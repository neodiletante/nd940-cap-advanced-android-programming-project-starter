package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.network.models.Election

interface ElectionsDataSource {
    suspend fun saveElection(election: Election)
    suspend fun getElections() : List<Election>
    suspend fun getElectionById(id: Int): Election?
    suspend fun deleteElectionById(id: Int)
    suspend fun deleteAllElections()
}