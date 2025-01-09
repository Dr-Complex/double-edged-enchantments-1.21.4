package net.dr_complex.double_edged_enchantments.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.dr_complex.double_edged_enchantments.enchantments.DEE_Enchantments;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public abstract class CrossbowMixin extends RangedWeaponItem {

    public CrossbowMixin(Settings settings) {
        super(settings);
    }

    @Shadow private boolean loaded;
    @Shadow private boolean charged;

    @Shadow public abstract void shootAll(World world, LivingEntity shooter, Hand hand, ItemStack stack, float speed, float divergence, @Nullable LivingEntity target);

    @Shadow
    private static float getSpeed(ChargedProjectilesComponent stack) {
        return stack.contains(Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }


    @Shadow
    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Shadow
    public int getRange() {
        return 8;
    }

    @Shadow
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target){}

    @Inject(method = "use",at = @At("HEAD"), cancellable = true)
    public void use(World world, @NotNull PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        ItemStack stack = user.getStackInHand(hand);
        ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
        boolean jammed = false;
        int Level = 0;
        if(stack.hasEnchantments() && !world.isClient){
            var enchantments = stack.getEnchantments().getEnchantments().stream().map(RegistryEntry::getIdAsString).toList();
            var CJamming = DEE_Enchantments.CURSE_JAMMING.getValue().toString();

            var level = stack.getEnchantments().getEnchantmentEntries().stream().map(Object2IntMap.Entry::getIntValue).toList();
            for (int j = 0; j< enchantments.size(); j++) {
                if (Objects.equals(enchantments.get(j), CJamming)) {
                    jammed = world.random.nextFloat() >= ((float) 1 /level.get(j));
                    Level = level.get(j);
                }
            }
        }
        if (chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty() && !jammed) {
            this.shootAll(world, user, hand, stack, getSpeed(chargedProjectilesComponent), 10.0F, null);
            cir.setReturnValue(ActionResult.CONSUME);
        } else if (!user.getProjectileType(stack).isEmpty()) {
            this.charged = false;
            this.loaded = false;
            user.setCurrentHand(hand);
            cir.setReturnValue(ActionResult.CONSUME);
        } else {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
