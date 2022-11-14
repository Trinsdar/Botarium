package earth.terrarium.botarium.forge.extensions;

import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.msrandom.extensions.annotations.ClassExtension;
import net.msrandom.extensions.annotations.ImplementsBaseElement;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

@ClassExtension(ClientHooks.class)
public class ClientHooksImpl {
    @ImplementsBaseElement
    public void registerItemProperty(Item pItem, ResourceLocation pName, ClampedItemPropertyFunction pProperty) {
        ItemProperties.register(pItem, pName, pProperty);
    }

    @ImplementsBaseElement
    public <T extends BlockEntity> void registerBlockEntityRenderers(BlockEntityType<T> type, BlockEntityRendererProvider<T> provider) {
        BlockEntityRenderers.register(type, provider);
    }

    @ImplementsBaseElement
    public <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entity, EntityRendererProvider<T> rendererProvider) {
        EntityRenderers.register(entity.get(), rendererProvider);
    }

    @ImplementsBaseElement
    public void setRenderLayer(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }
}
