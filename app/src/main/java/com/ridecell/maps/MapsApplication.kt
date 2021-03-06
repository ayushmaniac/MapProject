package com.ridecell.maps

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.ridecell.maps.di.component.ApplicationComponent
import com.ridecell.maps.di.component.DaggerApplicationComponent
import com.ridecell.maps.di.module.ApplicationModule

class MapsApplication : Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        getDependencies()
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}