package fr.exodeus.zombies.Common.Saver.Network.Capabilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCapabilities implements IMessage {
	
	public int thirstLevel;

	// =======================================================

	public PacketCapabilities(Capabilities capabilities) {
		this.thirstLevel = capabilities.thirstLevel;
	}

	public PacketCapabilities() {
	}

	// =======================================================

	@Override
	public void fromBytes(ByteBuf buf) {
		this.thirstLevel = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.thirstLevel);
	}
	
	//========================================================
	
	public static class ServerHandler implements IMessageHandler<PacketCapabilities, IMessage>
    {
            
        @Override
        public IMessage onMessage(PacketCapabilities message, MessageContext ctx)
        {
            
            Minecraft.getMinecraft().theWorld.getMinecraftServer().addScheduledTask(new ScheduledPacketTask(ctx.getServerHandler().playerEntity, message));
            
            return null;
        }
            
    }
        
    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketCapabilities, IMessage>
    {

        @Override
        public IMessage onMessage(PacketCapabilities message, MessageContext ctx)
        {
             Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
             return null;
         }
            
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
