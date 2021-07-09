package com.ridecell.maps.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity
import com.ridecell.maps.ui.login.ui.LoginRegistrationActivity
import com.ridecell.maps.ui.main.MainActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    companion object{
        private const val SPLASH_DISPLAY_LENGTH = 3000
    }


    override fun provideLayoutId(): Int = R.layout.activity_splash

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        initSplash()
    }

    private fun initSplash() {
        Handler().postDelayed({
            checkIfUserIsLoggedIn()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    private fun checkIfUserIsLoggedIn() {
        if(viewModel.checkIfUserIsLoggedIn()){
            moveToHome()
        }
        else
        {
            moveToRegistration()
        }
    }

    private fun moveToRegistration() {
        val intent = Intent(this, LoginRegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}