package com.sythiex.vanilla_supplements.blocks;

import java.util.Random;

import com.sythiex.vanilla_supplements.VanillaSupplements;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBerryBush extends BlockBushVS
{
	public BlockBerryBush()
	{
		setBlockName("berryBush");
		setBlockTextureName(VanillaSupplements.MODID + ":berryBush_stage_0");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sideX, float sideY, float sideZ)
    {
		if(!world.isRemote)
		{
			if(world.getBlockMetadata(x, y, z) == maxGrowthStage)
			{
				int pX = player.getPlayerCoordinates().posX;
				int pY = player.getPlayerCoordinates().posY;
				int pZ = player.getPlayerCoordinates().posZ;
				ItemStack berries = new ItemStack(VanillaSupplements.berry, (world.rand.nextInt(3) + 1));
				Entity entity = new EntityItem(world, pX, pY, pZ, berries);
				world.spawnEntityInWorld(entity);
				//player.inventory.addItemStackToInventory(new ItemStack(VanillaSupplements.berry, (world.rand.nextInt(3) + 1)));
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
				return true;
			}
		}
		return true;
    }
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		iIcon = new IIcon[maxGrowthStage + 1];
		iIcon[0] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[1] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[2] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[3] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[4] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[5] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[6] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_0");
		iIcon[7] = iconRegister.registerIcon(VanillaSupplements.MODID + ":berryBush_stage_1");
	}
}