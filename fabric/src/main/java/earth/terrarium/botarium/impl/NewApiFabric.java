package earth.terrarium.botarium.impl;

import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.item.ItemContainerBlock;
import earth.terrarium.botarium.impl.fluid.storage.FabricBlockFluidContainer;
import earth.terrarium.botarium.impl.fluid.storage.FabricItemFluidContainer;
import earth.terrarium.botarium.impl.energy.FabricBlockEnergyContainer;
import earth.terrarium.botarium.impl.energy.FabricItemEnergyContainer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.impl.transfer.item.InventoryStorageImpl;
import team.reborn.energy.api.EnergyStorage;

public class NewApiFabric {
     public static void initialize() {
         Botarium.init();

         EnergyStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
             if (blockEntity instanceof BotariumEnergyBlock<?> attachment) {
                 var container = attachment.getEnergyStorage(world, pos, state, blockEntity, context);
                 return container == null ? null : new FabricBlockEnergyContainer<>(container);
             } else if(state.getBlock() instanceof BotariumEnergyBlock<?> attachment) {
                 var container = attachment.getEnergyStorage(world, pos, state, blockEntity, context);
                 return container == null ? null : new FabricBlockEnergyContainer<>(container);
             }
             else {
                 BotariumEnergyBlock<?> blockEnergyGetter = EnergyApi.getEnergyBlock(state.getBlock());
                 if (blockEnergyGetter != null) {
                     var container = blockEnergyGetter.getEnergyStorage(world, pos, state, blockEntity, context);
                     if (container != null) {
                         return new FabricBlockEnergyContainer<>(container);
                     }
                 }
                 if(blockEntity != null) {
                     BotariumEnergyBlock<?> entityEnergyGetter = EnergyApi.getEnergyBlock(blockEntity.getType());
                     if (entityEnergyGetter == null) return null;
                     var entityContainer = entityEnergyGetter.getEnergyStorage(world, pos, state, blockEntity, context);
                     return entityContainer == null ? null : new FabricBlockEnergyContainer<>(entityContainer);
                 }
             }
             return null;
         });

         EnergyStorage.ITEM.registerFallback((itemStack, context) -> {
             if (itemStack.getItem() instanceof BotariumEnergyItem<?> attachment) {
                 var energyStorage = attachment.getEnergyStorage(itemStack);
                 return energyStorage == null ? null : new FabricItemEnergyContainer<>(context, itemStack, energyStorage);
             } else {
                 BotariumEnergyItem<?> itemEnergyGetter = EnergyApi.getEnergyItem(itemStack.getItem());
                 if (itemEnergyGetter == null) return null;
                 var container = itemEnergyGetter.getEnergyStorage(itemStack);
                 return container == null ? null : new FabricItemEnergyContainer<>(context, itemStack, container);
             }
         });

         FluidStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
             if (blockEntity instanceof BotariumFluidBlock<?> attachment) {
                 var container = attachment.getFluidContainer(world, pos, state, blockEntity, context);
                 return container == null ? null : new FabricBlockFluidContainer<>(container);
             } else if (state.getBlock() instanceof BotariumFluidBlock<?> attachment) {
                 var container = attachment.getFluidContainer(world, pos, state, blockEntity, context);
                 return container == null ? null : new FabricBlockFluidContainer<>(container);
             } else {
                 var blockEnergyGetter = FluidApi.getFluidBlock(state.getBlock());
                 if (blockEnergyGetter != null) {
                     var container = blockEnergyGetter.getFluidContainer(world, pos, state, blockEntity, context);
                     if (container != null) {
                         return new FabricBlockFluidContainer<>(container);
                     }
                 }
                 if (blockEntity != null) {
                     var entityEnergyGetter = FluidApi.getFluidBlock(blockEntity.getType());
                     if (entityEnergyGetter == null) return null;
                     var entityContainer = entityEnergyGetter.getFluidContainer(world, pos, state, blockEntity, context);
                     return entityContainer == null ? null : new FabricBlockFluidContainer<>(entityContainer);
                 }
             }
             return null;
         });


         FluidStorage.ITEM.registerFallback((itemStack, context) -> {
             if (itemStack.getItem() instanceof BotariumFluidItem<?> attachment) {
                 FluidContainer fluidContainer = attachment.getFluidContainer(itemStack);
                 return fluidContainer == null ? null : new FabricItemFluidContainer(context, fluidContainer);
             } else {
                 var itemFluidGetter = FluidApi.getFluidItem(itemStack.getItem());
                 if (itemFluidGetter == null) return null;
                 var container = itemFluidGetter.getFluidContainer(itemStack);
                 return container == null ? null : new FabricItemFluidContainer(context, container);
             }
         });

         ItemStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
             if (blockEntity instanceof ItemContainerBlock energyContainer) {
                 return InventoryStorageImpl.of(energyContainer.getContainer(), context);
             }
             return null;
         });
     }
}
