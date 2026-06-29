package org.mohsen.reviewtask.data.source.remote

import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single { provideKtorClient() }
}
