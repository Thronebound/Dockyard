package gg.thronebound.dockyard.pathfinding

import de.metaphoriker.pathetic.api.wrapper.PathEnvironment
import de.metaphoriker.pathetic.api.wrapper.PathPosition
import de.metaphoriker.pathetic.api.wrapper.PathVector
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3d
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.WorldManager

object PatheticPlatformDockyard {

    fun toLocation(pathPosition: PathPosition): Location {
        return Location(pathPosition.x, pathPosition.y, pathPosition.z, toWorld(pathPosition.pathEnvironment))
    }

    @JvmName("ExtensionToLocation")
    fun PathPosition.toLocation(): Location {
        return toLocation(this)
    }

    @JvmName("ExtensionToPathPosition")
    fun Location.toPathPosition(): PathPosition {
        return toPathPosition(this)
    }

    fun toPathPosition(location: Location): PathPosition {
        return PathPosition(toPathWorld(location.world), location.x, location.y, location.z)
    }

    fun toWorld(pathEnvironment: PathEnvironment): World {
        return WorldManager.getOrThrow(pathEnvironment.name)
    }

    fun toPathWorld(world: World): PathEnvironment {
        return PathEnvironment(world.uuid, world.name, world.dimensionType.minY, world.dimensionType.height)
    }

    fun toVector(pathVector: PathVector): Vector3d {
        return Vector3d(pathVector.x, pathVector.y, pathVector.z)
    }

    fun toPathVector(vector3d: Vector3d): PathVector {
        return PathVector(vector3d.x, vector3d.y, vector3d.z)
    }

}