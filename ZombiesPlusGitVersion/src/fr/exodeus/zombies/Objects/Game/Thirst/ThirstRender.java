package fr.exodeus.zombies.Objects.Game.Thirst;

import java.util.UUID;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThirstRender {

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if (!event.isCancelable() && event.getType() == ElementType.EXPERIENCE) {
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.thePlayer.capabilities.isCreativeMode)
				return;

			int posX = event.getResolution().getScaledWidth() / 2 + 10;
			int posZ = event.getResolution().getScaledHeight() - 48;

			mc.renderEngine.bindTexture(new ResourceLocation("zombies", "textures/gui/status_Gui.png"));

			float thirst = 7;//TODO 
			

			for (int x = 1; x < 11; x++) {

				if (thirst - 2 > -1) {
					thirst = thirst - 2;
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posZ, 0, 0, 8, 8);
				} else if (thirst - 1 > -1) {
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posZ, 8, 0, 8, 8);
					thirst = thirst - 1;
				} else {
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posZ, 16, 0, 8, 8);
				}
				// origineX,origineZ,X,-Z,length,height
			}

		}
	}

}
