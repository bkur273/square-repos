package com.assignment.freeletics.di

import com.assignment.freeletics.presentation.shared.state.MutableViewStateObserver
import com.assignment.freeletics.presentation.shared.state.ViewStateObserver
import com.assignment.freeletics.shared.coroutines.ErrorContextHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesMutableViewStateWatcher(): MutableViewStateObserver =
        MutableViewStateObserver.Impl()

    @Provides
    @ViewModelScoped
    fun providesViewStateWatcher(): ViewStateObserver =
        MutableViewStateObserver.Impl()

    @Provides
    @ViewModelScoped
    fun providesErrorContextHandler(
        mutableViewStateObserver: MutableViewStateObserver
    ): ErrorContextHandler = ErrorContextHandler.Impl(mutableViewStateObserver)

}