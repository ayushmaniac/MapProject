package com.ridecell.maps.ui.splash

import android.os.Bundle
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity

class SplashActivity : BaseActivity<SplashViewModel>() {


    override fun provideLayoutId(): Int = R.layout.activity_splash

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}