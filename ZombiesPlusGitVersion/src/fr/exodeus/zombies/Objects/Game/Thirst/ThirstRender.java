package fr.exodeus.zombies.Objects.Game.Thirst;

import java.util.UUID;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import fr.exodeus.zombies.Common.Saver.Network.ClientSideStats;
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if (!event.isCancelable() && event.getType() == ElementType.EXPERIENCE) {
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.thePlayer.capabilities.isCreativeMode)
				return;

			int posX = event.getResolution().getScaledWidth() / 2 + 10;
			int posZ = event.getResolution().getScaledHeight() - 48;

			mc.renderEngine.bindTexture(new ResourceLocation("zombies", "textures/gui/status_Gui.png"));

			float thirst = ClientSideStats.getInstance().thirstLevel;
			//int thirst = mc.thePlayer.getCapability(MainZombies.ZOMBIESPLUS_CAP, null).thirstLevel;
			

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

	
	/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (Minecraft.getMinecraft().theWorld == null)
			return;
		
		if (Minecraft.getMinecraft().theWorld.getMinecraftServer() == null)
			return;

		decrasePlayerThirstLevel((EntityPlayer) event.getEntityLiving());

	}

	public static void setPlayerTirstLevel(EntityPlayer entityPlayer, float thirst) {
		
		//TODO

	}

	public static float getPlayerTirstLevel(EntityPlayer entityPlayer) {
		
		//TODO
		
		return 11;
	}

	public static void decrasePlayerThirstLevel(EntityPlayer player) {

		float thirst = getPlayerTirstLevel(player);

		float decrase = 0.0101F;

		setPlayerTirstLevel(player, (thirst - decrase));

	}

	
	/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			if (event.getOriginal().hasCapability(MainZombies.TUTO_CAP, null)) {
				Capabilities cap = event.getOriginal().getCapability(MainZombies.TUTO_CAP, null);
				Capabilities newCap = event.getEntityPlayer().getCapability(MainZombies.TUTO_CAP, null);
				newCap.setThirst(cap.getThirst());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (!event.player.worldObj.isRemote) {
			event.player.getCapability(MainZombies.TUTO_CAP, null).sync();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent.Entity event) {
		if (event.getEntity() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(Reference.MOD_ID + ":TUTO_CAP"),
					new Capabilities((EntityPlayer) event.getEntity()));
		}
	}
	Ancien code */

}
