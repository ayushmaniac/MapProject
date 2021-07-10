package com.ridecell.maps.ui.main

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.Navigation

class MainActivity : BaseActivity<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)
    }
}