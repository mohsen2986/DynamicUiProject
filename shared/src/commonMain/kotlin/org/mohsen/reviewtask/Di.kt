package org.mohsen.reviewtask

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.mohsen.reviewtask.data.repository.UiRepositoryImpl
import org.mohsen.reviewtask.data.source.remote.dataSources.FakeKtorRemoteDataSource
import org.mohsen.reviewtask.data.source.remote.dataSources.KtorRemoteDataSource
import org.mohsen.reviewtask.data.source.remote.dataSources.RemoteDataSource
import org.mohsen.reviewtask.data.source.remote.networkModule
import org.mohsen.reviewtask.data.source.remote.provideKtorClient
import org.mohsen.reviewtask.domain.repository.UiRepository
import org.mohsen.reviewtask.domain.usecase.GetUiComponentUseCase
import org.mohsen.reviewtask.presentation.screens.dynamicui.DynamicUiViewModel

val appModule = module {
    single { provideKtorClient() }
    // we can use the fake RemoteDataSource or use the mock server with real data source
//    single<RemoteDataSource> { KtorRemoteDataSource(get()) }
    single<RemoteDataSource> { FakeKtorRemoteDataSource() }
    single<UiRepository> { UiRepositoryImpl(get()) }
    factory { GetUiComponentUseCase(get(), Dispatchers.IO) }
    viewModelOf(::DynamicUiViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule , networkModule)
    }

// For iOS
fun initKoin() = initKoin {}
