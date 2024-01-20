package earth.terrarium.botarium.impl.energy;

import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.util.Updatable;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ForgeItemEnergyContainer<T extends EnergyContainer & Updatable>(T container) implements IEnergyStorage, ICapabilityProvider {

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        LazyOptional<IEnergyStorage> of = LazyOptional.of(() -> this);
        return capability.orEmpty(ForgeCapabilities.ENERGY, of.cast()).cast();
    }

    @Override
    public int receiveEnergy(int maxAmount, boolean bl) {
        if (maxAmount <= 0) return 0;
        int inserted = (int) container.insertEnergy(maxAmount, bl);
        if(!bl) container.update();
        return inserted;
    }

    @Override
    public int extractEnergy(int maxAmount, boolean bl) {
        if (maxAmount <= 0) return 0;
        int extracted = (int) container.extractEnergy(maxAmount, bl);
        if(!bl) container.update();
        return extracted;
    }


    @Override
    public int getEnergyStored() {
        return (int) container.getStoredEnergy();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) container.getMaxCapacity();
    }

    @Override
    public boolean canExtract() {
        return container.allowsExtraction();
    }

    @Override
    public boolean canReceive() {
        return container.allowsInsertion();
    }
}
