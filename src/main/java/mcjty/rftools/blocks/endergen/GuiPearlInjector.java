package mcjty.rftools.blocks.endergen;

import mcjty.lib.container.GenericContainer;
import mcjty.lib.gui.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.rftools.RFTools;
import mcjty.rftools.network.RFToolsMessages;
import mcjty.rftools.setup.GuiProxy;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;

public class GuiPearlInjector extends GenericGuiContainer<PearlInjectorTileEntity, GenericContainer> {

    public GuiPearlInjector(PearlInjectorTileEntity pearlInjectorTileEntity, GenericContainer container, PlayerInventory inventory) {
        super(RFTools.instance, RFToolsMessages.INSTANCE, pearlInjectorTileEntity, container, inventory, GuiProxy.GUI_MANUAL_MAIN, "powinjector");
    }

    @Override
    public void init() {
        window = new Window(this, tileEntity, RFToolsMessages.INSTANCE, new ResourceLocation(RFTools.MODID, "gui/pearl_injector.gui"));
        super.init();
    }
}
