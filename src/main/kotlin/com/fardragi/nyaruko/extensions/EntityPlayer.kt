package com.fardragi.nyaruko.extensions

import com.fardragi.nyaruko.utils.NyarukoTeleporter
import com.fardragi.nyaruko.viewmodels.PositionViewModel
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer

fun EntityPlayer.teleport(position: PositionViewModel) {
    val server = MinecraftServer.getServer()

    if (dimension == position.dimension) {
        setLocationAndAngles(
            position.positionX,
            position.positionY,
            position.positionZ,
            position.rotationYaw,
            position.rotationPitch
        )
        setPositionAndUpdate(
            position.positionX,
            position.positionY,
            position.positionZ,
        )
        return
    }

    if (isRiding) {
        dismountEntity(ridingEntity)
    }

    val newWorld = server.worldServerForDimension(position.dimension)

    server.configurationManager.transferPlayerToDimension(
        this as EntityPlayerMP, position.dimension, NyarukoTeleporter(
            newWorld,
            position.positionX,
            position.positionY,
            position.positionZ,
            position.rotationYaw,
            position.rotationPitch
        )
    )
}
