package com.ridecell.maps.di.component

import com.ridecell.maps.di.module.FragmentModule
import com.ridecell.maps.di.scope.FragmentScope
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)interface FragmentComponent {
}