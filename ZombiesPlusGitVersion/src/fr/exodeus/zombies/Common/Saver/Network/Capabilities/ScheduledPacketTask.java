package fr.exodeus.zombies.Common.Saver.Network.Capabilities;

import java.util.concurrent.Callable;

import fr.exodeus.zombies.Common.MainZombies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScheduledPacketTask implements Runnable
{
   private EntityPlayer player;
   private PacketCapabilities message;

   public ScheduledPacketTask(EntityPlayer player, PacketCapabilities message)
   {
       this.player = player;
       this.message = message;
   }
   
   @Override
   public void run()
   {
       //Condition ternaire pour r�cup�rer le joueur selon le c�t�.
       EntityPlayer player = this.player == null ? getPlayer() : this.player;
       //On revient sur cette ligne plus tard.
       player.getCapability(MainZombies.ZOMBIESPLUS_CAP, null).setStats(message.thirstLevel);
   }

   @SideOnly(Side.CLIENT)
   private EntityPlayer getPlayer()
   {
       return Minecraft.getMinecraft().thePlayer;
       
   }

}
