package fr.exodeus.zombies.Objects.Game;

import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class GameLogic {

	public EntityPlayer player;

	public ThirstLogic thirst;

	public GameLogic(EntityPlayer player) {
		this.player = player;
		this.thirst = new ThirstLogic(player);

	}

	public void onTick() {

		this.thirst.onTick();
	}

	public void onDeath() {
		
		this.thirst.onDeath();
	}

	public void addExhaustion(float f) {
		
		thirst.addExhaustion(f);
		
	}

}
