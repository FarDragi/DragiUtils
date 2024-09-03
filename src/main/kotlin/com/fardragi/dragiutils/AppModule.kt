package com.fardragi.dragiutils

import com.fardragi.dragiutils.auth.AuthModule
import com.fardragi.dragiutils.config.Config
import com.fardragi.dragiutils.config.DatabaseConfig
import com.fardragi.dragiutils.database.Database
import com.fardragi.dragiutils.services.UserService
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val appModule = module {
    single { Config() } withOptions {
        createdAtStart()
    }
    single { DatabaseConfig(get()) }
    single { Database(get()) }
    single { AuthModule() }
    scope<AuthModule> {
        scoped { UserService(get()) }
    }
}
