package com.fardragi.nyaruko.database.tables

import com.fardragi.nyaruko.enums.PermissionLevel
import org.jetbrains.exposed.dao.id.IdTable

object Commands : IdTable<UInt>("commands") {
    override val id = uinteger("id").autoIncrement().entityId()
    val name = varchar("name", 255).uniqueIndex()
    val level = enumerationByName("level", 6, PermissionLevel::class)

    override val primaryKey = PrimaryKey(id)
}
