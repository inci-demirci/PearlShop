package com.example.pearlshop.di

import android.content.Context
import androidx.room.Room
import com.example.pearlshop.data.datasource.Datasources
import com.example.pearlshop.data.local.FavoritesDao
import com.example.pearlshop.data.local.FavoritesDatabase
import com.example.pearlshop.data.repos.ProductRepository
import com.example.pearlshop.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideDatasource(api: ApiService) = Datasources(api)

    @Provides
    fun provideRepository(ds: Datasources) = ProductRepository(ds)

    @Provides
    fun provideFavoritesDatabase(@ApplicationContext context: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            "favorites"
        ).build()
    }

    @Provides
    fun provideFavoritesDao(db: FavoritesDatabase): FavoritesDao = db.favoriteDao()
}
