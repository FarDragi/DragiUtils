package com.fardragi.nyaruko.services

import com.fardragi.nyaruko.database.query
import com.fardragi.nyaruko.exceptions.NotFoundException
import com.fardragi.nyaruko.models.User
import java.util.UUID

class UserService(private val groupService: GroupService) {
    suspend fun getOrCreateUser(id: UUID, name: String): User {
        var isNew = false
        val user = query {
            var user = User.findById(id)

            if (user == null) {
                user = User.new(id) {
                    this.name = name
                }
                isNew = true
            }

            user
        }

        if (isNew) {
            groupService.registerUserInDefaultGroup(user)
        }

        return user
    }

    suspend fun setPassword(id: UUID, password: String) {
        query {
            User.findByIdAndUpdate(id) { user ->
                user.updatePassword(password)
            } ?: throw NotFoundException(User::class.simpleName, id.toString())
        }
    }

    suspend fun checkPassword(id: UUID, password: String): Boolean {
        return query {
            val user = User.findById(id) ?: throw NotFoundException(User::class.simpleName, id.toString())

            user.checkPassword(password)
        }
    }

    suspend fun getById(id: UUID): User {
        return query {
            User.findById(id) ?: throw NotFoundException(User::class.simpleName, id.toString())
        }
    }
}
