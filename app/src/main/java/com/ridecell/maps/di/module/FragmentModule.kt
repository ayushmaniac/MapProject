package com.ridecell.maps.di.module

import com.ridecell.maps.ui.base.BaseFragment
import dagger.Module

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {
}