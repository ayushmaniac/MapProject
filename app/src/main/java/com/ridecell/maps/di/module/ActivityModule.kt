package com.ridecell.maps.di.module

import com.ridecell.maps.ui.base.BaseActivity
import dagger.Module


@Module
class ActivityModule(private val baseActivity: BaseActivity<*>) {
}