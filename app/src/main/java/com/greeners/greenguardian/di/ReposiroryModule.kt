package com.greeners.greenguardian.di


import com.greeners.greenguardian.data.remote.GreenGuardianRepositoryImpl
import com.greeners.greenguardian.domain.GreenGuardianRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::GreenGuardianRepositoryImpl) { bind<GreenGuardianRepository>() }
}
