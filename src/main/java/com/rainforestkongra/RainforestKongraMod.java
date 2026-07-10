package com.rainforestkongra;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RainforestKongraMod implements ModInitializer {
    public static final String MOD_ID = "rainforestkongra";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Tracks how long each player has been continuously exposed to rain.
    private static final java.util.Map<java.util.UUID, Integer> RAIN_EXPOSURE = new java.util.HashMap<>();

    // Ticks of continuous rain before damage begins (~15 seconds).
    private static final int RAIN_DAMAGE_THRESHOLD = 300;
    // Interval between damage ticks once threshold is passed (~2 seconds).
    private static final int RAIN_DAMAGE_INTERVAL = 40;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Rainforest Kongra Ecosystem");

        RainforestKongraModItems.registerItems();
        RainforestKongraModEntities.registerEntities();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                tickRainDamage(world);
            }
        });
    }

    private void tickRainDamage(ServerWorld world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (player.isCreative() || player.isSpectator()) {
                RAIN_EXPOSURE.remove(player.getUuid());
                continue;
            }

            boolean exposed = isExposedToRain(world, player);

            if (!exposed) {
                RAIN_EXPOSURE.put(player.getUuid(), 0);
                continue;
            }

            int exposure = RAIN_EXPOSURE.getOrDefault(player.getUuid(), 0) + 1;
            RAIN_EXPOSURE.put(player.getUuid(), exposure);

            if (exposure >= RAIN_DAMAGE_THRESHOLD
                    && (exposure - RAIN_DAMAGE_THRESHOLD) % RAIN_DAMAGE_INTERVAL == 0) {

                if (!hasFullKongraArmor(player)) {
                    DamageSource source = world.getDamageSources().magic();
                    player.damage(source, 1.5f);
                }
            }
        }
    }

    private boolean isExposedToRain(ServerWorld world, PlayerEntity player) {
        if (!world.isRaining()) {
            return false;
        }
        BlockPos playerPos = player.getBlockPos();
        BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, playerPos);
        boolean underOpenSky = topPos.getY() <= playerPos.getY();
        boolean warmBiome = world.getBiome(playerPos).value().getTemperature() > 0.85f;
        return underOpenSky && warmBiome;
    }

    public static boolean hasFullKongraArmor(PlayerEntity player) {
        ItemStack head = player.getInventory().getArmorStack(3);
        ItemStack chest = player.getInventory().getArmorStack(2);
        ItemStack legs = player.getInventory().getArmorStack(1);
        ItemStack feet = player.getInventory().getArmorStack(0);
        return head.isOf(RainforestKongraModItems.KONGRA_HELMET)
                && chest.isOf(RainforestKongraModItems.KONGRA_CHESTPLATE)
                && legs.isOf(RainforestKongraModItems.KONGRA_LEGGINGS)
                && feet.isOf(RainforestKongraModItems.KONGRA_BOOTS);
    }
}