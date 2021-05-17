package com.example.android.politicalpreparedness.utils

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.election.ElectionsDataSource
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsLocalRepository(
        private val electionsDao: ElectionDao
) : ElectionsDataSource {

    override suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO){
            electionsDao.saveElection(election)
        }
    }

    override suspend fun getElections(): List<Election> {
        return electionsDao.getElections()
    }

    override suspend fun getElectionById(id: Int): Election? {
        return electionsDao.getElectionById(id)
    }

    override suspend fun deleteElectionById(id: Int) {
        withContext(Dispatchers.IO){
            electionsDao.deleteElection(id)
        }
    }

    override suspend fun deleteAllElections() {
        withContext(Dispatchers.IO){
            electionsDao.deleteAllElections()
        }
    }
}