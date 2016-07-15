package fr.exodeus.zombies.Proxy;

import fr.exodeus.zombies.Common.Reference;
import fr.exodeus.zombies.Common.Saver.PlayerContainer;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieButcher;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCivil;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCrawler;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieHerobrine;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieMiner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieNazi;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePolice;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePrisoner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSoldier;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSurgeon;
import fr.exodeus.zombies.Objects.Items.CleanChain;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.RustyChain;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Food.CookedFlesh;
import fr.exodeus.zombies.Objects.Items.Food.Donut;
import fr.exodeus.zombies.Objects.Items.Food.PurifiedFlesh;
import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import fr.exodeus.zombies.Objects.Items.Usable.Bandage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		//Usable Items
		Antibiotic.registeerRednders();
		Bandage.registeerRednders();
		//Simple Items
		CleanChain.registeerRednders();
		IronNugget.registeerRednders();
		RustyChain.registeerRednders();
		SandDust.registeerRednders();
		//Food Items
		CookedFlesh.registeerRednders();
		Donut.registeerRednders();
		PurifiedFlesh.registeerRednders();
		//Entities
		ZombieButcher.registerRender();
		ZombieCivil.registerRender();
		ZombieCrawler.registerRender();
		ZombieHerobrine.registerRender();
		ZombieMiner.registerRender();
		ZombieNazi.registerRender();
		ZombiePolice.registerRender();
		ZombiePrisoner.registerRender();
		ZombieSoldier.registerRender();
		ZombieSurgeon.registerRender();
		
		
		/*network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_NAME);
		network.registerMessage(PacketCapabilities.ClientHandler.class, PacketCapabilities.class, 3, Side.CLIENT);
		network.registerMessage(PacketCapabilities.ServerHandler.class, PacketCapabilities.class, 3, Side.SERVER);  
		Ancien code*/
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
