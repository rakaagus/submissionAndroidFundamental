package com.dicoding.submissionandroidfundamental.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity

@Database(entities = [UserGithubEntity::class], version = 1, exportSchema = false)
abstract class UserGithubDatabase: RoomDatabase() {
    abstract fun userGithubDao(): UserGithubDao

    companion object{
        @Volatile
        private var INSTANCE: UserGithubDatabase? = null
        fun getInstance(context: Context): UserGithubDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserGithubDatabase::class.java, "UserGithub.db"
                ).build()
            }
    }
}