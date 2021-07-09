package com.ridecell.maps.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ridecell.maps.data.ApplicationRepository
import com.ridecell.maps.ui.base.BaseActivity
import com.ridecell.maps.ui.main.MainViewModel
import com.ridecell.maps.ui.splash.SplashViewModel
import com.ridecell.maps.utils.viewmodel.ViewModelProviderFactory
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val baseActivity: BaseActivity<*>) {

    @Provides
    fun provideContext(
    ) : Context = baseActivity

    @Provides
    fun provideSplashViewModel(
        applicationRepository: ApplicationRepository
    ): SplashViewModel = ViewModelProvider(
        baseActivity, ViewModelProviderFactory(SplashViewModel::class) {
            SplashViewModel(applicationRepository)
        }).get(SplashViewModel::class.java)

    @Provides
    fun provideMainViewModel(
        applicationRepository: ApplicationRepository
    ): MainViewModel = ViewModelProvider(
        baseActivity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel()
        }).get(MainViewModel::class.java)
}