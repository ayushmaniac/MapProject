package com.ridecell.maps.di.component

import com.ridecell.maps.di.module.FragmentModule
import com.ridecell.maps.di.scope.FragmentScope
import com.ridecell.maps.ui.fragments.home.HomeFragment
import com.ridecell.maps.ui.fragments.profile.ProfileFragment
import com.ridecell.maps.ui.login.fragments.login.LoginFragment
import com.ridecell.maps.ui.login.fragments.registration.RegistrationFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)interface FragmentComponent {

    fun inject(loginFragment: LoginFragment)
    fun inject(registrationFragment: RegistrationFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(profileFragment: ProfileFragment)
}