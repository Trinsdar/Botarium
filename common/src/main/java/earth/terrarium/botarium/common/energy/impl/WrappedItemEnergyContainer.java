package earth.terrarium.botarium.common.energy.impl;

import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.base.EnergySnapshot;
import earth.terrarium.botarium.util.Updatable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public record WrappedItemEnergyContainer(ItemStack stack, EnergyContainer container) implements EnergyContainer, Updatable<ItemStack> {

    public WrappedItemEnergyContainer {
        container.deserialize(stack.getOrCreateTag());
    }

    @Override
    public long insertEnergy(long energy, boolean simulate) {
        return container.insertEnergy(energy, simulate);
    }

    @Override
    public long extractEnergy(long energy, boolean simulate) {
        return container.extractEnergy(energy, simulate);
    }

    @Override
    public void setEnergy(long energy) {
        container.setEnergy(energy);
    }

    @Override
    public long getStoredEnergy() {
        return container.getStoredEnergy();
    }

    @Override
    public long getMaxCapacity() {
        return container.getMaxCapacity();
    }

    @Override
    public long maxInsert() {
        return container.maxInsert();
    }

    @Override
    public long maxExtract() {
        return container.maxExtract();
    }

    @Override
    public boolean allowsInsertion() {
        return container.allowsInsertion();
    }

    @Override
    public boolean allowsExtraction() {
        return container.allowsExtraction();
    }

    @Override
    public EnergySnapshot createSnapshot() {
        return container.createSnapshot();
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        container.deserialize(nbt);
    }

    @Override
    public CompoundTag serialize(CompoundTag nbt) {
        return container.serialize(nbt);
    }

    @Override
    public void update(ItemStack object) {
        container.serialize(object.getOrCreateTag());
    }

    @Override
    public void clearContent() {
        container.clearContent();
    }
}