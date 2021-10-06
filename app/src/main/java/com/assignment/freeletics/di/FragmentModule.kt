package com.assignment.freeletics.di

import com.assignment.freeletics.presentation.shared.image.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    @FragmentScoped
    fun providesGlideImageLoader(): ImageLoader = ImageLoader.GlideImageLoader()

}