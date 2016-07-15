package fr.exodeus.zombies.Common;

import fr.exodeus.zombies.Common.Saver.PlayerContainer;
import fr.exodeus.zombies.Common.Saver.Network.NetworkHandler;
import fr.exodeus.zombies.Common.Saver.Network.Capabilities.Capabilities;
import fr.exodeus.zombies.Common.Saver.Network.Capabilities.PacketCapabilities;
import fr.exodeus.zombies.Objects.Entity.EntityCreator;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstRender;
import fr.exodeus.zombies.Objects.Items.CleanChain;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.RustyChain;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Food.CookedFlesh;
import fr.exodeus.zombies.Objects.Items.Food.Donut;
import fr.exodeus.zombies.Objects.Items.Food.PurifiedFlesh;
import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import fr.exodeus.zombies.Objects.Items.Usable.Bandage;
import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import fr.exodeus.zombies.Objects.Potion.Infection;
import fr.exodeus.zombies.Objects.Recipe.Recipes;
import fr.exodeus.zombies.Objects.Tab.ZombiesTab;
import fr.exodeus.zombies.Proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class MainZombies {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@Instance
	public static MainZombies instance;
	
	@CapabilityInject(Capabilities.class)
	public static final Capability<Capabilities> ZOMBIESPLUS_CAP = null;
	
	//===============================================================================================================
	
	public static final ZombiesTab tabZombies = new ZombiesTab();
	
	//===============================================================================================================
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Simple Items
		CleanChain.init();
		CleanChain.register();
		IronNugget.init();
		IronNugget.register();
		RustyChain.init();
		RustyChain.register();
		SandDust.init();
		SandDust.register();
		//Food Items
		CookedFlesh.init();
		CookedFlesh.register();
		Donut.init();
		Donut.register();
		PurifiedFlesh.init();
		PurifiedFlesh.register();
		//Usable Items
		Antibiotic.init();
		Antibiotic.register();
		Bandage.init();
		Bandage.register();
		
		//Register Events
		MinecraftForge.EVENT_BUS.register(new ZombiesEvent());
		MinecraftForge.EVENT_BUS.register(new ThirstRender());
		
		//Setup the player status connection between client and server
		new NetworkHandler();
		
	}
	
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		Recipes.loadRecipes();
		
		EntityCreator.registerEntities();
		
		Infection.init();
		Bonebreak.init();
		
		Capabilities.register();
		
		proxy.registerRenders();
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		Reference.registerAllPotionEffects();
		
		//actualy needed but gives me an null exception on launch
		//network.registerMessage(PacketCapabilities.ClientHandler.class, PacketCapabilities.class, 3, Side.CLIENT);
		//network.registerMessage(PacketCapabilities.ServerHandler.class, PacketCapabilities.class, 3, Side.SERVER);
		
	}
	
	@EventHandler
	public void serverClosed(FMLServerStoppedEvent event) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			PlayerContainer.ALL_PLAYERS.clear();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
