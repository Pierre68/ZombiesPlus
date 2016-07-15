package fr.exodeus.zombies.Proxy;

import fr.exodeus.zombies.Common.Reference;
import fr.exodeus.zombies.Common.Saver.PlayerContainer;
import fr.exodeus.zombies.Common.Saver.Network.ClientSideStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public void registerRenders(){
		
		
	}
	
	public void serverTick(EntityPlayer player) {
		PlayerContainer handler = PlayerContainer.getPlayer(player);
		if (handler != null) {
			if (!player.capabilities.isCreativeMode) {
				handler.getStats().onTick();
				
			}
		} else {
			PlayerContainer.addPlayer(player);
		}
	}
}
