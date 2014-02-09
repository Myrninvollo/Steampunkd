package steampunked.core.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import steampunked.Mod_Steampunked;
import steampunked.tileentities.TileEntityTinkerBench;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class ServerPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload payload, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				payload.data));
		EntityPlayer sender = (EntityPlayer) player;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (payload.channel.equals(Mod_Steampunked.STEAMPUNKED)) {
			if (side == Side.SERVER)
				handleServerPacket(payload, player);
		}
	}

	private void handleServerPacket(Packet250CustomPayload payload,
			Player player) {
		DataInputStream inputStream = new DataInputStream(
				new ByteArrayInputStream(payload.data));
		byte packetID;
		try {
			packetID = inputStream.readByte();
			if (packetID == 2) {
				int dimension = inputStream.readInt();
				World world = DimensionManager.getWorld(dimension);
				int x = inputStream.readInt();
				int y = inputStream.readInt();
				int z = inputStream.readInt();
				TileEntity te = world.getBlockTileEntity(x, y, z);

				Short itemID = inputStream.readShort();
				Short itemDamage = inputStream.readShort();
				if (te instanceof TileEntityTinkerBench) {
					((TileEntityTinkerBench) te).setInventorySlotContents(8,
							new ItemStack(itemID, 1, itemDamage));
					((TileEntityTinkerBench) te).setInventorySlotContents(0,
							null);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}