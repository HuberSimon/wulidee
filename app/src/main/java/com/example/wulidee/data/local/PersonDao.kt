package com.example.wulidee.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Query("SELECT * FROM persons ORDER BY id DESC")
    fun getAllPersons(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE mainPerson = 1 LIMIT 1")
    fun getMainPerson(): Flow<Person?>

    @Query("SELECT name FROM persons WHERE mainPerson = 1 LIMIT 1")
    fun getMainPersonName(): Flow<String?>

    @Query("SELECT COUNT(*) FROM persons")
    fun getPersonCount(): Flow<Int>

    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Int): Person?
}

