package com.ridecell.maps.ui.fragments.home

import android.view.View
import com.ridecell.maps.R
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel>() {

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun setupView(view: View) {
    }
}