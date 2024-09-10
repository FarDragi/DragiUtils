package com.fardragi.nyaruko.permission

import com.fardragi.nyaruko.shared.IModule
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope

class PermissionModule() : KoinScopeComponent, IModule {
    override val scope by lazy { createScope(this) }

    override fun start() {
        TODO("Not yet implemented")
    }
}
