package steampunked.core.handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import steampunked.Mod_Steampunked;
import steampunked.tileentities.TileEntityInjector;
import steampunked.tileentities.TileEntityMachine;
import steampunked.tileentities.TileEntitySteamFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class SteampunkedPacketHandler implements IPacketHandler
{
	  static final int MAX_DISTANCE = 192;
	  
	  public static void SendTileEntityPacketToPlayers(Packet packet, TileEntity tile)
	  {
	    World world = tile.worldObj; 
	    if(!world.isRemote && packet != null)
	    {
	      for(int j = 0; j < world.playerEntities.size(); j++)
	      {
	        EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);

	        if(Math.abs(player.posX - tile.xCoord) <= MAX_DISTANCE && Math.abs(player.posY - tile.yCoord) <= MAX_DISTANCE && Math.abs(player.posZ - tile.zCoord) <= MAX_DISTANCE)
	        {
	          player.playerNetServerHandler.sendPacketToPlayer(packet);
	        }
	      }
	    }
	  }
	  
	  static private Packet250CustomPayload MakeInjectorModePacket(TileEntityInjector sender)
	  {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    DataOutputStream data = new DataOutputStream(bytes);
	    try
	    {
	      //Position
	      data.writeInt(sender.xCoord);
	      data.writeInt(sender.yCoord);
	      data.writeInt(sender.zCoord);
	      
	    } catch(IOException e)
	    {
	      e.printStackTrace();
	    }
	    return MakePacket(bytes);
	  }

	  
	  static public void SendInjectorModeToServer(TileEntityInjector sender)
	  {
	    PacketDispatcher.sendPacketToServer(MakeInjectorModePacket(sender));
	  }

	  public static void SendInjectorModeToClients(TileEntityInjector sender)
	  {
	    SendTileEntityPacketToPlayers(MakeInjectorModePacket(sender), sender);
	  }
	  
	  static private Packet250CustomPayload MakePacket(ByteArrayOutputStream bytes)
	  {
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = Mod_Steampunked.STEAMPUNKED;
	    packet.data = bytes.toByteArray();
	    packet.length = packet.data.length;
	    packet.isChunkDataPacket = true;
	    return packet;
	  }
	  
	 
	  
	

	
	  @Override
	  public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	  {
	    try
	    {
	      ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
	      int x = data.readInt();
	      int y = data.readInt();
	      int z = data.readInt();
	      World world = ((EntityPlayer)player).worldObj;

	      if(world != null)
	      {
	        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	        if(tileEntity != null)
	        {
	          if(tileEntity instanceof TileEntityMachine)
	          {
	            ((TileEntityMachine)tileEntity).ReceivePacketData(manager, packet, ((EntityPlayer)player), data);
	          }
	        }
	      }
	    } catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }

	  static public void SendFurnaceModeToServer(TileEntitySteamFurnace sender)
	  {
	    PacketDispatcher.sendPacketToServer(MakeFurnaceModePacket(sender));
	  }

	  public static void SendFurnaceModeToClients(TileEntitySteamFurnace sender)
	  {
	    SendTileEntityPacketToPlayers(MakeFurnaceModePacket(sender), sender);
	  }
	
	  static private Packet250CustomPayload MakeFurnaceModePacket(TileEntitySteamFurnace sender)
	  {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    DataOutputStream data = new DataOutputStream(bytes);
	    try
	    {
	      //Position
	      data.writeInt(sender.xCoord);
	      data.writeInt(sender.yCoord);
	      data.writeInt(sender.zCoord);
	      
	    } catch(IOException e)
	    {
	      e.printStackTrace();
	    }
	    return MakePacket(bytes);
	  }
	}