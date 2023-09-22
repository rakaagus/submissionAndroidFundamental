package com.dicoding.submissionandroidfundamental.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity

@Dao
interface UserGithubDao {
    @Query("SELECT * FROM users_github ORDER BY id ASC")
    fun getAllUserFav(): LiveData<List<UserGithubEntity>>

    @Query("SELECT EXISTS(SELECT * FROM users_github WHERE login = :login)")
    fun isUserFav(login: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserGithub(userGithub: UserGithubEntity)

    @Query("DELETE FROM users_github WHERE login = :login")
    suspend fun deleteUserGithub(login: String)
}