package mcjty.rftools.blocks.security;

import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericContainer;
import mcjty.rftools.RFTools;
import mcjty.rftools.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SecuritySetup {
    public static GenericBlock<SecurityManagerTileEntity, GenericContainer> securityManagerBlock;

    public static SecurityCardItem securityCardItem;
    public static OrphaningCardItem orphaningCardItem;

    public static void init() {
        if(!SecurityConfiguration.enabled) return;
        securityManagerBlock = ModBlocks.builderFactory.<SecurityManagerTileEntity, GenericContainer> builder("security_manager")
                .tileEntityClass(SecurityManagerTileEntity.class)
                .container(GenericContainer.class, SecurityManagerTileEntity.CONTAINER_FACTORY)
                .guiId(RFTools.GUI_SECURITY_MANAGER)
                .information("message.rftools.shiftmessage")
                .informationShift("message.rftools.security_manager", stack -> {
                    int cnt = 0;
                    NBTTagCompound tagCompound = stack.getTagCompound();
                    if (tagCompound != null) {
                        NBTTagList bufferTagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);

                        for (int i = 0 ; i < bufferTagList.tagCount() ; i++) {
                            NBTTagCompound itemTag = bufferTagList.getCompoundTagAt(i);
                            if (itemTag != null) {
                                ItemStack s = new ItemStack(itemTag);
                                if (!s.isEmpty()) {
                                    cnt++;
                                }
                            }
                        }
                    }
                    return Integer.toString(cnt);
                })
                .build();
        orphaningCardItem = new OrphaningCardItem();
        securityCardItem = new SecurityCardItem();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        if(!SecurityConfiguration.enabled) return;
        securityManagerBlock.initModel();
        securityManagerBlock.setGuiClass(GuiSecurityManager.class);
        orphaningCardItem.initModel();
        securityCardItem.initModel();
    }
}
