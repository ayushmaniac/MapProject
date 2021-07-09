package com.ridecell.maps.di.component

import com.ridecell.maps.di.module.ActivityModule
import com.ridecell.maps.di.scope.ActivityScope
import com.ridecell.maps.ui.login.ui.LoginRegistrationActivity
import com.ridecell.maps.ui.main.MainActivity
import com.ridecell.maps.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(loginRegistrationActivity: LoginRegistrationActivity)
}