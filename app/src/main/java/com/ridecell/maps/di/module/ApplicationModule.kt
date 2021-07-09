package com.ridecell.maps.di.module

import android.content.Context
import android.content.SharedPreferences
import com.ridecell.maps.MapsApplication
import com.ridecell.maps.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: MapsApplication) {

    @Provides
    fun provideContext() : Context = application

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)


    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = application.getSharedPreferences("ridecell_map_prefs", Context.MODE_PRIVATE)

}