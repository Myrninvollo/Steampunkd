/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [3 Mar 2014, 20:27:40 (GMT)]
 */
package com.sr2610.steampunked.blocks;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sr2610.steampunked.proxies.ClientProxy;
import com.sr2610.steampunked.tileentities.TileEntityPipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPipe extends BlockContainer {

	public static final float PIPE_MIN_POS = 0.25F;
	public static final float PIPE_MAX_POS = 0.75F;

	private int renderMask = 0;

	private static final ForgeDirection[] DIR_VALUES = ForgeDirection.values();

	static enum Part {
		Pipe
	}

	protected BlockPipe(Material material) {
		super(material);
		setCreativeTab(CreativeTabs.tabAllSearch);

	}


	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPipe();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public void setRenderMask(int mask) {
		renderMask = mask;
	}

	public final void setRenderAllSides() {
		renderMask = 0x3f;
	}

	public void setRenderSide(ForgeDirection side, boolean render) {
		if (render) {
			renderMask |= 1 << side.ordinal();
		} else {
			renderMask &= ~(1 << side.ordinal());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y,
			int z, int side) {
		return (renderMask & (1 << side)) != 0;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k,
			AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
		setBlockBounds(PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MAX_POS,
				PIPE_MAX_POS, PIPE_MAX_POS);
		super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist,
				par7Entity);

		TileEntity tile1 = world.getTileEntity(i, j, k);
		if (tile1 instanceof TileEntityPipe) {
			TileEntityPipe tileG = (TileEntityPipe) tile1;

			if (tileG.isPipeConnected(ForgeDirection.WEST)) {
				setBlockBounds(0.0F, PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MAX_POS,
						PIPE_MAX_POS, PIPE_MAX_POS);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

			if (tileG.isPipeConnected(ForgeDirection.EAST)) {
				setBlockBounds(PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MIN_POS, 1.0F,
						PIPE_MAX_POS, PIPE_MAX_POS);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

			if (tileG.isPipeConnected(ForgeDirection.DOWN)) {
				setBlockBounds(PIPE_MIN_POS, 0.0F, PIPE_MIN_POS, PIPE_MAX_POS,
						PIPE_MAX_POS, PIPE_MAX_POS);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

			if (tileG.isPipeConnected(ForgeDirection.UP)) {
				setBlockBounds(PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MIN_POS,
						PIPE_MAX_POS, 1.0F, PIPE_MAX_POS);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

			if (tileG.isPipeConnected(ForgeDirection.NORTH)) {
				setBlockBounds(PIPE_MIN_POS, PIPE_MIN_POS, 0.0F, PIPE_MAX_POS,
						PIPE_MAX_POS, PIPE_MAX_POS);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

			if (tileG.isPipeConnected(ForgeDirection.SOUTH)) {
				setBlockBounds(PIPE_MIN_POS, PIPE_MIN_POS, PIPE_MIN_POS,
						PIPE_MAX_POS, PIPE_MAX_POS, 1.0F);
				super.addCollisionBoxesToList(world, i, j, k, axisalignedbb,
						arraylist, par7Entity);
			}

		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x,
			int y, int z) {
		RaytraceResult rayTraceResult = doRayTrace(world, x, y, z,
				Minecraft.getMinecraft().thePlayer);

		if (rayTraceResult != null && rayTraceResult.boundingBox != null) {
			AxisAlignedBB box = rayTraceResult.boundingBox;
			switch (rayTraceResult.hitPart) {

			case Pipe: {
				float scale = 0.05F;
				box = box.expand(scale, scale, scale);
				break;
			}
			}
			return box.getOffsetBoundingBox(x, y, z);
		}
		return super.getSelectedBoundingBoxFromPool(world, x, y, z).expand(
				-0.85F, -0.85F, -0.85F);
	}

	static class RaytraceResult {

		RaytraceResult(Part hitPart, MovingObjectPosition movingObjectPosition,
				AxisAlignedBB boundingBox, ForgeDirection side) {
			this.hitPart = hitPart;
			this.movingObjectPosition = movingObjectPosition;
			this.boundingBox = boundingBox;
			this.sideHit = side;
		}

		public final Part hitPart;
		public final MovingObjectPosition movingObjectPosition;
		public final AxisAlignedBB boundingBox;
		public final ForgeDirection sideHit;

		@Override
		public String toString() {
			return String.format("RayTraceResult: %s, %s",
					hitPart == null ? "null" : hitPart.name(),
					boundingBox == null ? "null" : boundingBox.toString());
		}
	}

	private RaytraceResult doRayTrace(World world, int x, int y, int z,
			EntityPlayer player) {
		double reachDistance = 5;

		if (player instanceof EntityPlayerMP) {
			reachDistance = ((EntityPlayerMP) player).theItemInWorldManager
					.getBlockReachDistance();
		}

		double eyeHeight = world.isRemote ? player.getEyeHeight()
				- player.getDefaultEyeHeight() : player.getEyeHeight();
		Vec3 lookVec = player.getLookVec();
		Vec3 origin = world.getWorldVec3Pool().getVecFromPool(player.posX,
				player.posY + eyeHeight, player.posZ);
		Vec3 direction = origin.addVector(lookVec.xCoord * reachDistance,
				lookVec.yCoord * reachDistance, lookVec.zCoord * reachDistance);

		return doRayTrace(world, x, y, z, origin, direction);
	}

	private RaytraceResult doRayTrace(World world, int x, int y, int z,
			Vec3 origin, Vec3 direction) {
		TileEntity pipeTileEntity = world.getTileEntity(x, y, z);

		TileEntityPipe tileG = null;
		if (pipeTileEntity instanceof TileEntityPipe) {
			tileG = (TileEntityPipe) pipeTileEntity;
		}

		if (tileG == null) {
			return null;
		}

		MovingObjectPosition[] hits = new MovingObjectPosition[25];
		AxisAlignedBB[] boxes = new AxisAlignedBB[25];
		ForgeDirection[] sideHit = new ForgeDirection[25];
		Arrays.fill(sideHit, ForgeDirection.UNKNOWN);

		for (ForgeDirection side : DIR_VALUES) {
			if (side == ForgeDirection.UNKNOWN || tileG.isPipeConnected(side)) {
				AxisAlignedBB bb = getPipeBoundingBox(side);
				setBlockBounds(bb);
				boxes[side.ordinal()] = bb;
				hits[side.ordinal()] = super.collisionRayTrace(world, x, y, z,
						origin, direction);
				sideHit[side.ordinal()] = side;
			}
		}

		double minLengthSquared = Double.POSITIVE_INFINITY;
		int minIndex = -1;

		for (int i = 0; i < hits.length; i++) {
			MovingObjectPosition hit = hits[i];
			if (hit == null) {
				continue;
			}

			double lengthSquared = hit.hitVec.squareDistanceTo(origin);

			if (lengthSquared < minLengthSquared) {
				minLengthSquared = lengthSquared;
				minIndex = i;
			}
		}

		// reset bounds

		setBlockBounds(0, 0, 0, 1, 1, 1);

		if (minIndex == -1) {
			return null;
		} else {
			Part hitPart = null;

			if (minIndex < 7) {
				hitPart = Part.Pipe;
			}

			return new RaytraceResult(hitPart, hits[minIndex], boxes[minIndex],
					sideHit[minIndex]);
		}
	}

	private void setBlockBounds(AxisAlignedBB bb) {
		setBlockBounds((float) bb.minX, (float) bb.minY, (float) bb.minZ,
				(float) bb.maxX, (float) bb.maxY, (float) bb.maxZ);
	}

	private AxisAlignedBB getPipeBoundingBox(ForgeDirection side) {
		float min = PIPE_MIN_POS;
		float max = PIPE_MAX_POS;

		if (side == ForgeDirection.UNKNOWN) {
			return AxisAlignedBB.getAABBPool().getAABB(min, min, min, max, max,
					max);
		}

		float[][] bounds = new float[3][2];
		// X START - END
		bounds[0][0] = min;
		bounds[0][1] = max;
		// Y START - END
		bounds[1][0] = 0;
		bounds[1][1] = min;
		// Z START - END
		bounds[2][0] = min;
		bounds[2][1] = max;

		transform(bounds, side);
		return AxisAlignedBB.getAABBPool().getAABB(bounds[0][0], bounds[1][0],
				bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
	}

	public static void transform(float[][] targetArray, ForgeDirection direction) {
		if ((direction.ordinal() & 0x1) == 1) {
			mirrorY(targetArray);
		}

		for (int i = 0; i < (direction.ordinal() >> 1); i++) {
			rotate(targetArray);
		}
	}

	public static void mirrorY(float[][] targetArray) {
		float temp = targetArray[1][0];
		targetArray[1][0] = (targetArray[1][1] - 0.5F) * -1F + 0.5F; // 1 ->
																		// 0.5F
																		// ->
																		// -0.5F
																		// -> 0F
		targetArray[1][1] = (temp - 0.5F) * -1F + 0.5F; // 0 -> -0.5F -> 0.5F ->
														// 1F
	}

	public static void rotate(float[][] targetArray) {
		for (int i = 0; i < 2; i++) {
			float temp = targetArray[2][i];
			targetArray[2][i] = targetArray[1][i];
			targetArray[1][i] = targetArray[0][i];
			targetArray[0][i] = temp;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float xOffset, float yOffset,
			float zOffset) {
		super.onBlockActivated(world, x, y, z, player, side, xOffset, yOffset,
				zOffset);

		world.notifyBlocksOfNeighborChange(x, y, z, ModBlocks.pipetest);

		ItemStack currentItem = player.getCurrentEquippedItem();

		if (currentItem == null) {
		} else if (currentItem.getItem() == Items.sign) {
			return false;
		}
		return false;

	}

}
