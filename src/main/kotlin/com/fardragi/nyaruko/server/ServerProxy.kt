package com.fardragi.nyaruko.server

import com.fardragi.nyaruko.appModule
import com.fardragi.nyaruko.auth.AuthModule
import com.fardragi.nyaruko.core.CoreModule
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

open class ServerProxy {
    private lateinit var app: KoinApplication

    fun onPreInit(event: FMLPreInitializationEvent) {
        app = startKoin {
            modules(appModule)
        }
    }

    fun onInit(event: FMLInitializationEvent) {
        val coreModule = app.koin.get<CoreModule>()
        val authModule = app.koin.get<AuthModule>()

        coreModule.start()
        authModule.start()
    }

    fun onPostInit(event: FMLPostInitializationEvent) {}
    fun onServerStarting(event: FMLServerStartingEvent) {}
}
