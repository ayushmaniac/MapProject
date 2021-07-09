package com.ridecell.maps.ui.login.ui

import android.os.Bundle
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity

class LoginRegistrationActivity : BaseActivity<LoginRegistrationViewModel>(){

    override fun provideLayoutId(): Int = R.layout.activity_login_registration

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
    }
}