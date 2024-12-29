package net.dr_complex.double_edged_enchantments.other;

import net.dr_complex.double_edged_enchantments.DEE_Main;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class DEE_DataComponentTypes {

    public static final ComponentType<BlockPos> POS_CONTAINER = register("pos_container",blockPosBuilder -> blockPosBuilder.codec(BlockPos.CODEC));
    public static final ComponentType<Integer> XP_CONTAINER = register("xp_container", integerBuilder -> integerBuilder.codec(Codec.INT));
    public static final ComponentType<Integer> MODE_CONTAINER = register("mode_container",integerBuilder -> integerBuilder.codec(Codec.INT));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator){
        return Registry.register(Registries.DATA_COMPONENT_TYPE,DEE_Main.id(name), builderUnaryOperator.apply(ComponentType.builder()).build());
    }

    public static void LoadDataComponents(){
        DEE_Main.LOGGER.info("Damaging the data (not really)");
    }
}
