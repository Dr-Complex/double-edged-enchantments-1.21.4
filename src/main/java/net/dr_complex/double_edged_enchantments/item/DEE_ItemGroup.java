package net.dr_complex.double_edged_enchantments.item;

import net.dr_complex.double_edged_enchantments.DEE_Main;
import net.dr_complex.double_edged_enchantments.other.DEE_Tags;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.text.Text;


public class DEE_ItemGroup {

    public static final ItemGroup DEE_GROUP = Registry.register(Registries.ITEM_GROUP,DEE_Main.id("dee_group"),
            FabricItemGroup.builder()
                    .icon(()->new ItemStack(DEE_Items.XP_NEEDLE))
                    .displayName(Text.translatable("item_group.double_edged_enchantments.dee_group"))
                    .entries((displayContext, entries) ->{

                        entries.add(DEE_Items.REVERED_ENDER_PEARL);
                        entries.add(DEE_Items.XP_NEEDLE);
                        entries.add(DEE_Items.DEBUGGING_TOOL);

                        displayContext.lookup().getOptional(RegistryKeys.ENCHANTMENT).ifPresent(enchantmentImpl -> enchantmentImpl.streamEntries()
                                .filter(tag -> !tag.isIn(EnchantmentTags.CURSE) && !tag.isIn(DEE_Tags.Enchantments.NEUTRAL_MAGIC))
                                .map(reference -> EnchantmentHelper.getEnchantedBookWith(new EnchantmentLevelEntry(reference,reference.value().getMaxLevel())))
                                .forEach(itemStack -> entries.add(itemStack, ItemGroup.StackVisibility.PARENT_TAB_ONLY)));

                        displayContext.lookup().getOptional(RegistryKeys.ENCHANTMENT).ifPresent(enchantmentImpl -> enchantmentImpl.streamEntries()
                                .filter(tag -> tag.isIn(EnchantmentTags.CURSE) && !tag.isIn(DEE_Tags.Enchantments.NEUTRAL_MAGIC))
                                .map(reference -> EnchantmentHelper.getEnchantedBookWith(new EnchantmentLevelEntry(reference,reference.value().getMaxLevel())))
                                .forEach(itemStack -> entries.add(itemStack, ItemGroup.StackVisibility.PARENT_TAB_ONLY)));

                        displayContext.lookup().getOptional(RegistryKeys.ENCHANTMENT).ifPresent(enchantmentImpl -> enchantmentImpl.streamEntries()
                                .filter(tag -> !tag.isIn(EnchantmentTags.CURSE) && tag.isIn(DEE_Tags.Enchantments.NEUTRAL_MAGIC))
                                .map(reference -> EnchantmentHelper.getEnchantedBookWith(new EnchantmentLevelEntry(reference,reference.value().getMaxLevel())))
                                .forEach(itemStack -> entries.add(itemStack, ItemGroup.StackVisibility.PARENT_TAB_ONLY)));
                    }).build());

    public static void LoadItemGroups(){
        DEE_Main.LOGGER.info("ItemGroups are now known");
    }
}
