package earth.terrarium.botarium.impl.extensions;

import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import earth.terrarium.botarium.impl.energy.PlatformBlockEnergyManager;
import earth.terrarium.botarium.impl.energy.PlatformItemEnergyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.msrandom.extensions.annotations.ClassExtension;
import net.msrandom.extensions.annotations.ImplementedByExtension;
import org.jetbrains.annotations.Nullable;

@ClassExtension(EnergyContainer.class)
public interface EnergyContainerImpl {
    static EnergyContainer of(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        PlatformBlockEnergyManager platformBlockEnergyManager = PlatformBlockEnergyManager.of(level, pos, state, entity, direction);
        return platformBlockEnergyManager == null ? EnergyApi.getAPIEnergyContainer(level, pos, state, entity, direction) : platformBlockEnergyManager;
    }

    static EnergyContainer of(ItemStackHolder holder) {
        return PlatformItemEnergyManager.of(holder);
    }

    @ImplementedByExtension
    static boolean holdsEnergy(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).isPresent();
    }

    @ImplementedByExtension
    static boolean holdsEnergy(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        return (entity != null && entity.getCapability(ForgeCapabilities.ENERGY, direction).isPresent()) || EnergyApi.getAPIEnergyContainer(level, pos, state, entity, direction) != null;
    }
}