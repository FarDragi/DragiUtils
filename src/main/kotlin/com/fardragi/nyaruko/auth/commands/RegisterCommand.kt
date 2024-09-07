package com.fardragi.nyaruko.auth.commands

import com.fardragi.nyaruko.auth.messages.AlreadyRegisteredMessage
import com.fardragi.nyaruko.auth.messages.LoginMessage
import com.fardragi.nyaruko.enums.PermissionLevel
import com.fardragi.nyaruko.services.UserService
import com.fardragi.nyaruko.shared.commands.NyarukoCommandBase
import com.fardragi.nyaruko.shared.messages.FailCommandMessage
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText

class RegisterCommand(private val userService: UserService) : NyarukoCommandBase() {
    override fun getCommandName(): String {
        return "register"
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/register <password> <repeat password>"
    }

    override suspend fun processCommandPlayer(player: EntityPlayerMP, args: Array<out String>): ChatComponentText {
        if (args.isEmpty() || args.size < 2) {
            return FailCommandMessage.create()
        }

        val userId = player.uniqueID.toString()
        val password = args[0]
        val repeatPassword = args[1]

        val user = userService.getById(userId)

        if (user.isRegistered) {
            return AlreadyRegisteredMessage.create()
        }

        if (password != repeatPassword) {
            FailCommandMessage.create("Password not match")
        }

        userService.setPassword(userId, repeatPassword)

        return LoginMessage.create()
    }

    override fun getRequiredPermissionLevel(): Int {
        return PermissionLevel.True.level
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}
