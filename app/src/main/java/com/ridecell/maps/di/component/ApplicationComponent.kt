package com.ridecell.maps.di.component

import android.content.Context
import com.ridecell.maps.MapsApplication
import com.ridecell.maps.di.module.ApplicationModule
import com.ridecell.maps.utils.network.NetworkHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MapsApplication)
    fun getContext() : Context
    fun getNetworkHelper(): NetworkHelper



}