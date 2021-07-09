package com.ridecell.maps.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
    }
}