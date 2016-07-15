package fr.exodeus.zombies.Common.Saver;

import java.util.HashMap;
import java.util.Map;

import fr.exodeus.zombies.Objects.Game.GameLogic;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerContainer {
	
	public static final Map<EntityPlayer, PlayerContainer> ALL_PLAYERS = new HashMap<EntityPlayer, PlayerContainer>();
	
	public EntityPlayer player;
	public GameLogic stats;

	public PlayerContainer(EntityPlayer player, GameLogic gameLogic) {
		this.player = player;
		this.stats = gameLogic;
	}

	public static void addPlayer(EntityPlayer player) {
		if (!ALL_PLAYERS.containsKey(player)) {
			PlayerContainer container = new PlayerContainer(player, new GameLogic(player));
			ALL_PLAYERS.put(player, container);
		}
	}
	
	public static void respawnPlayer(EntityPlayer newPlayer) {
		ALL_PLAYERS.remove(newPlayer);
		addPlayer(newPlayer);
		
	}
	
	public GameLogic getStats() {
		return stats;
	}
	
	public static PlayerContainer getPlayer(EntityPlayer player) {
		return ALL_PLAYERS.get(player);
	}

	public void addExhaustion(float f) {
		
		getStats().addExhaustion(f);
		
	}

}
