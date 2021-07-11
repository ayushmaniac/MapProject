package com.ridecell.maps.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ridecell.maps.data.local.repo.ApplicationRepository
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.ui.base.BaseActivity
import com.ridecell.maps.ui.forgotpassword.ForgotPasswordViewModel
import com.ridecell.maps.ui.login.ui.LoginRegistrationViewModel
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

    @Provides
    fun provideLoginRegistrationViewModel(
        applicationRepository: ApplicationRepository
    ): LoginRegistrationViewModel = ViewModelProvider(
        baseActivity, ViewModelProviderFactory(LoginRegistrationViewModel::class) {
            LoginRegistrationViewModel()
        }).get(LoginRegistrationViewModel::class.java)

    @Provides
    fun provideForgotPasswordViewModel(
        userRepository: UserRepository
    ): ForgotPasswordViewModel = ViewModelProvider(
        baseActivity, ViewModelProviderFactory(ForgotPasswordViewModel::class) {
            ForgotPasswordViewModel(userRepository)
        }).get(ForgotPasswordViewModel::class.java)
}