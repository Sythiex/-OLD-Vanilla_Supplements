package com.sythiex.vanilla_supplements.items;

import java.util.List;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemWand extends Item
{
	public static int numberOfModes = 8;
	
	public ItemWand()
	{
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		if(itemStack.stackTagCompound == null)
		{
			itemStack.stackTagCompound = new NBTTagCompound();
			itemStack.stackTagCompound.setInteger("mode", 1);
			itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
			itemStack.stackTagCompound.setInteger("x", 0);
			itemStack.stackTagCompound.setInteger("y", 0);
			itemStack.stackTagCompound.setInteger("z", 0);
			itemStack.stackTagCompound.setInteger("world", entity.dimension);
		}
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	{
		if (itemStack.stackTagCompound != null)
		{
			list.add("Mode: " + getMode(itemStack.stackTagCompound.getInteger("mode")));
		}
	}
	
	/**
	 * sets critical points for and calls createShape()
	 */
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float sideX, float sideY, float sideZ)
    {
		if(!player.canPlayerEdit(x, y, z, side, itemStack) || world.isRemote || player.isSneaking())
        {
            return false;
        }
		else
		{
			if(itemStack.stackTagCompound.getBoolean("firstBlockSet"))
			{
				int x1 = itemStack.stackTagCompound.getInteger("x");
				int y1 = itemStack.stackTagCompound.getInteger("y");
				int z1 = itemStack.stackTagCompound.getInteger("z");
				createShape(itemStack, world, x1, y1, z1, x, y, z);
			}
			else
			{
				itemStack.stackTagCompound.setInteger("x", x);
				itemStack.stackTagCompound.setInteger("y", y);
				itemStack.stackTagCompound.setInteger("z", z);
				itemStack.stackTagCompound.setBoolean("firstBlockSet", true);
			}
			return true;
		}
    }
	
	/*
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
		if(!world.isRemote && player.isSneaking())
		{
			int mode = itemStack.stackTagCompound.getInteger("mode");
			++mode;
			if(mode > numberOfModes)
				mode = 1;
			itemStack.stackTagCompound.setInteger("mode", mode);
			player.addChatMessage(new ChatComponentText("Mode: " + getMode(mode)));
		}
        return itemStack;
    }
    */
	
	/**
	 * gets the shape the wand will create
	 * @param mode
	 * @return shape
	 */
	public static String getMode(int mode)
	{
		switch(mode)
		{
		case 1:	return "Rectangular Prism";
		case 2:	return "Rectangular Prism, Hollow";
		case 3:	return "Cylinder";
		case 4:	return "Cylinder, Hollow";
		case 5:	return "Sphere";
		case 6:	return "Sphere, Hollow";
		case 7:	return "Hemisphere";
		case 8:	return "Hemisphere, Hollow";
		default: return "ERROR";
		}
	}
	
	/**
	 * generates the shape in the world using the critical points set by onItemUse()
	 * @param itemStack
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 */
	public void createShape(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int mode = itemStack.stackTagCompound.getInteger("mode");
		switch(mode)
		{
		//rect prism
		case 1:	
			Block block = world.getBlock(x1, y1, z1);
			int metadata = world.getBlockMetadata(x1, y1, z1);
			int minX = (x1 <= x2) ? x1 : x2;
			int minY = (y1 <= y2) ? y1 : y2;
			int minZ = (z1 <= z2) ? z1 : z2;
			int maxX = (x1 > x2) ? x1 : x2;
			int maxY = (y1 > y2) ? y1 : y2;
			int maxZ = (z1 > z2) ? z1 : z2;
			
			for(int i = minX; i <= maxX; ++i)
			{
				for(int j = minY; j <= maxY; ++j)
				{
					for(int k = minZ; k <= maxZ; ++k)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
			
			itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
			break;
			
		case 2:	
		case 3:	
		case 4:	
		case 5:	
		case 6:	
		case 7:	
		case 8:	
		default: break;
		}
	}
}