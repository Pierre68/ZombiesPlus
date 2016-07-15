package fr.exodeus.zombies.Common.Saver.Network;

public class ClientSideStats {
	//Thirst
	public float thirstLevel;
	public float thirstSaturation;
	public boolean isPoisoned;
	//
	public int movementSpeed;
	public float temperature;
	//
	
	
	private static ClientSideStats instance = new ClientSideStats();

	public static ClientSideStats getInstance() {
		return instance;
	}
}
