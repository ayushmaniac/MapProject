package com.ridecell.maps.di.component

import android.content.Context
import com.github.sisyphsu.dateparser.DateParser
import com.ridecell.maps.MapsApplication
import com.ridecell.maps.data.local.repo.ApplicationRepository
import com.ridecell.maps.data.local.repo.UserRepository
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
    fun getApplicationRepository() : ApplicationRepository
    fun getUserRepository() : UserRepository
    fun getDateParser() : DateParser



}