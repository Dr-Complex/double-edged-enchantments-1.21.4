package net.dr_complex.double_edged_enchantments;

import net.dr_complex.double_edged_enchantments.entity.DEE_Entities;
import net.dr_complex.double_edged_enchantments.entity.client.AbstractSpearEntityRenderer;
import net.dr_complex.double_edged_enchantments.entity.client.SpearEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DEE_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(SpearEntityModel.Main,SpearEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(DEE_Entities.WOODEN_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.STONE_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.GOLDEN_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.COPPER_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.IRON_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.DIAMOND_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
        EntityRendererRegistry.register(DEE_Entities.NETHERITE_SPEAR_ENTITY_TYPE, AbstractSpearEntityRenderer::new);
    }
}
