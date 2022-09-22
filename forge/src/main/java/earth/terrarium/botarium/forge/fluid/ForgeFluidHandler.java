package earth.terrarium.botarium.forge.fluid;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ForgeFluidHandler implements PlatformFluidHandler {

    private final IFluidHandler handler;

    public ForgeFluidHandler(IFluidHandler handler) {
        this.handler = handler;
    }

    @Override
    public long insertFluid(FluidHolder fluid, boolean simulate) {
        return handler.fill(new ForgeFluidHolder(fluid), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public FluidHolder extractFluid(FluidHolder fluid, boolean simulate) {
        return new ForgeFluidHolder(handler.drain(new ForgeFluidHolder(fluid), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
    }

    @Override
    public int getTankAmount() {
        return handler.getTanks();
    }

    @Override
    public FluidHolder getFluidInTank(int tank) {
        return new ForgeFluidHolder(handler.getFluidInTank(tank));
    }
}
