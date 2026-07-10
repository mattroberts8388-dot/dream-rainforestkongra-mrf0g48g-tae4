package com.rainforestkongra;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RainforestKongraModEntities {

    public static EntityType<KongraEntity> KONGRA;
    public static EntityType<JaguarEntity> JAGUAR;
    public static EntityType<ToucanEntity> TOUCAN;

    public static void registerEntities() {
        KONGRA = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(RainforestKongraMod.MOD_ID, "kongra"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KongraEntity::new)
                        .dimensions(EntityDimensions.fixed(1.4f, 2.8f))
                        .build());

        JAGUAR = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(RainforestKongraMod.MOD_ID, "jaguar"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, JaguarEntity::new)
                        .dimensions(EntityDimensions.fixed(0.9f, 0.9f))
                        .build());

        TOUCAN = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(RainforestKongraMod.MOD_ID, "toucan"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ToucanEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.6f))
                        .build());

        FabricDefaultAttributeRegistry.register(KONGRA, KongraEntity.createKongraAttributes());
        FabricDefaultAttributeRegistry.register(JAGUAR, JaguarEntity.createJaguarAttributes());
        FabricDefaultAttributeRegistry.register(TOUCAN, ToucanEntity.createToucanAttributes());
    }
}