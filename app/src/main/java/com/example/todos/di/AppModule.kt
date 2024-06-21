package com.example.todos.di

import android.content.Context
import androidx.room.Room
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.data.remote.RemoteApi
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.repositories.AuthRepository
import com.example.todos.data.repositories.Repository
import com.example.todos.data.local.TodoDao
import com.example.todos.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi() : RemoteApi {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "todoappdb.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase) : TodoDao {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase) : UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideMyRepository(remoteApi : RemoteApi, todoDao: TodoDao, userDao: UserDao) : Repository {
        return Repository(todoDao,userDao, remoteApi)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(userDao: UserDao) : AuthRepository {
        return AuthRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideUserId(@ApplicationContext context: Context) : Int{
        val sharedPreferenceHelper = SharedPreferenceHelper(context)
        return sharedPreferenceHelper.userId
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceHelper(@ApplicationContext context: Context) : SharedPreferenceHelper {
        return SharedPreferenceHelper(context)
    }

}