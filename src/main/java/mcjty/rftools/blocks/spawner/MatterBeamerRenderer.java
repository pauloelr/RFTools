package mcjty.rftools.blocks.spawner;

import com.mojang.blaze3d.platform.GlStateManager;
import mcjty.lib.client.RenderGlowEffect;
import mcjty.lib.client.RenderHelper;
import mcjty.rftools.RFTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.opengl.GL11;

public class MatterBeamerRenderer extends TileEntityRenderer<MatterBeamerTileEntity> {

    private static final ResourceLocation redglow = new ResourceLocation(RFTools.MODID, "textures/blocks/redglow.png");
    private static final ResourceLocation blueglow = new ResourceLocation(RFTools.MODID, "textures/blocks/blueglow.png");

    @Override
    public void render(MatterBeamerTileEntity tileEntity, double x, double y, double z, float time, int destroyStage) {
        ResourceLocation txt;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        BlockPos destination = tileEntity.getDestination();
        if (destination != null) {
            if (tileEntity.isPowered()) {
                GlStateManager.pushMatrix();

                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

                Minecraft mc = Minecraft.getInstance();
                PlayerEntity p = mc.player;
                double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * time;
                double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * time;
                double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * time;

                RenderHelper.Vector start = new RenderHelper.Vector(tileEntity.getPos().getX() + .5f, tileEntity.getPos().getY() + .5f, tileEntity.getPos().getZ() + .5f);
                RenderHelper.Vector end = new RenderHelper.Vector(destination.getX() + .5f, destination.getY() + .5f, destination.getZ() + .5f);
                RenderHelper.Vector player = new RenderHelper.Vector((float) doubleX, (float) doubleY + p.getEyeHeight(), (float) doubleZ);
                GlStateManager.translated(-doubleX, -doubleY, -doubleZ);

                this.bindTexture(redglow);

                RenderHelper.drawBeam(start, end, player, tileEntity.isGlowing() ? .1f : .05f);

                tessellator.draw();
                GlStateManager.popMatrix();
            }
        }

        BlockPos coord = tileEntity.getPos();
        if (coord.equals(RFTools.instance.clientInfo.getSelectedTE())) {
            txt = redglow;
        } else if (coord.equals(RFTools.instance.clientInfo.getDestinationTE())) {
            txt = blueglow;
        } else {
            txt = null;
        }

        if (txt != null) {
            this.bindTexture(txt);
            RenderGlowEffect.renderGlow(tessellator, x, y, z);
        }
    }

    public static void register() {
        ClientRegistry.bindTileEntitySpecialRenderer(MatterBeamerTileEntity.class, new MatterBeamerRenderer());
    }
}

