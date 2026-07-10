package com.rainforestkongra;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RainforestKongraModItems {

    public static Item RAINFOREST_JADE;
    public static Item COBRA_SCALE;

    public static ArmorItem KONGRA_HELMET;
    public static ArmorItem KONGRA_CHESTPLATE;
    public static ArmorItem KONGRA_LEGGINGS;
    public static ArmorItem KONGRA_BOOTS;

    public static SpawnEggItem KONGRA_SPAWN_EGG;
    public static SpawnEggItem JAGUAR_SPAWN_EGG;
    public static SpawnEggItem TOUCAN_SPAWN_EGG;

    public static ItemGroup GENERAL_GROUP;

    public static void registerItems() {
        RAINFOREST_JADE = register("rainforest_jade", new Item(new FabricItemSettings()));
        COBRA_SCALE = register("cobra_scale", new Item(new FabricItemSettings()));

        KONGRA_HELMET = register("kongra_helmet",
                new ArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.HELMET,
                        new FabricItemSettings().maxCount(1)));
        KONGRA_CHESTPLATE = register("kongra_chestplate",
                new ArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE,
                        new FabricItemSettings().maxCount(1)));
        KONGRA_LEGGINGS = register("kongra_leggings",
                new ArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS,
                        new FabricItemSettings().maxCount(1)));
        KONGRA_BOOTS = register("kongra_boots",
                new ArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS,
                        new FabricItemSettings().maxCount(1)));

        KONGRA_SPAWN_EGG = register("kongra_spawn_egg",
                new SpawnEggItem(RainforestKongraModEntities.KONGRA, 0x3B2F2F, 0x66CC66,
                        new FabricItemSettings()));
        JAGUAR_SPAWN_EGG = register("jaguar_spawn_egg",
                new SpawnEggItem(RainforestKongraModEntities.JAGUAR, 0xC49A46, 0x2B2016,
                        new FabricItemSettings()));
        TOUCAN_SPAWN_EGG = register("toucan_spawn_egg",
                new SpawnEggItem(RainforestKongraModEntities.TOUCAN, 0x111111, 0xF4A300,
                        new FabricItemSettings()));

        GENERAL_GROUP = Registry.register(Registries.ITEM_GROUP,
                new Identifier(RainforestKongraMod.MOD_ID, "general"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(KONGRA_CHESTPLATE))
                        .displayName(Text.translatable("itemGroup.rainforestkongra.general"))
                        .entries((displayContext, entries) -> {
                            entries.add(RAINFOREST_JADE);
                            entries.add(COBRA_SCALE);
                            entries.add(KONGRA_HELMET);
                            entries.add(KONGRA_CHESTPLATE);
                            entries.add(KONGRA_LEGGINGS);
                            entries.add(KONGRA_BOOTS);
                            entries.add(KONGRA_SPAWN_EGG);
                            entries.add(JAGUAR_SPAWN_EGG);
                            entries.add(TOUCAN_SPAWN_EGG);
                        })
                        .build());
    }

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM,
                new Identifier(RainforestKongraMod.MOD_ID, name), item);
    }
}