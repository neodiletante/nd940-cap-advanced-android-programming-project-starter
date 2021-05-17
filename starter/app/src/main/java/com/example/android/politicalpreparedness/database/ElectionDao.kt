package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {
    @Query("select * from election_table")
    suspend fun getElections(): List<Election>

    @Query("select * from election_table where id = :electionId")
    suspend fun getElectionById(electionId: Int): Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(election: Election)

    @Query("delete from election_table where id = :electionId")
    suspend fun deleteElection(electionId: Int)

    @Query ("delete from election_table")
    suspend fun deleteAllElections()

}