package com.ridecell.maps.di.module

import android.content.Context
import android.content.SharedPreferences
import com.github.sisyphsu.dateparser.DateParser
import com.ridecell.maps.BuildConfig
import com.ridecell.maps.MapsApplication
import com.ridecell.maps.utils.network.MapsService
import com.ridecell.maps.utils.network.NetworkHelper
import com.ridecell.maps.utils.network.Networking
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: MapsApplication) {

    @Provides
    fun provideContext() : Context = application

    @Provides
    @Singleton
    fun provideDateParser() : DateParser = DateParser.newBuilder().build()

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)


    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = application.getSharedPreferences("ridecell_map_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideNetworkService(): MapsService =
        Networking.create(
            BuildConfig.BASE_URL)

}