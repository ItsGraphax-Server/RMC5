package de.itsgraphax.rmc5.token.creaking;

import org.bukkit.*;

public class ArenaWorldManager {
    private static final String WORLD_NAME = "combat_arena";

    private World arenaWorld;

    public ArenaWorldManager() {
        createOrLoadWorld();
    }

    public void createOrLoadWorld() {
        arenaWorld = Bukkit.getWorld(WORLD_NAME);

        if (arenaWorld == null) {
            WorldCreator creator = new WorldCreator(WORLD_NAME);

            creator.environment(World.Environment.NORMAL);
            creator.generator(new VoidGenerator());
            creator.generateStructures(false);

            arenaWorld = creator.createWorld();

            if (arenaWorld == null) {
                throw new RuntimeException("creaking world creation failed");
            }

        }

        configureWorld();
    }

    private void configureWorld() {
        arenaWorld.setDifficulty(Difficulty.HARD);

        arenaWorld.setGameRule(GameRules.ADVANCE_TIME, false);
        arenaWorld.setGameRule(GameRules.ADVANCE_WEATHER, false);
        arenaWorld.setGameRule(GameRules.SPAWN_MOBS, false);
        arenaWorld.setGameRule(GameRules.FIRE_DAMAGE, false);

        arenaWorld.setTime(18000);
        arenaWorld.setStorm(false);
        arenaWorld.setThundering(false);
    }

    public Location getSpawn() {
        return new Location(arenaWorld, 0, 0, 0);
    }

    public boolean isInWorld(Location l) {
        return l.getWorld() == arenaWorld;
    }
}
