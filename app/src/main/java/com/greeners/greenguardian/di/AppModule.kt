package com.greeners.greenguardian.di

import org.koin.dsl.module


fun appModule() = module {

    includes(
        viewModelModule,
        NetworkModule,
        repositoryModule,
    )

}
