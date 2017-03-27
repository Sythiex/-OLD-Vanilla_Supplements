package com.sythiex.vanillasupplements.items;

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
			if(itemStack.stackTagCompound.getBoolean("firstBlockSet"))
			{
				int x = itemStack.stackTagCompound.getInteger("x");
				int y = itemStack.stackTagCompound.getInteger("y");
				int z = itemStack.stackTagCompound.getInteger("z");
				list.add("First block at " + x + ", " + y + ", " + z);
				list.add("Block selected is " + player.getEntityWorld().getBlock(x, y, z).getLocalizedName());
			}
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
			if(itemStack.stackTagCompound.getBoolean("firstBlockSet") && itemStack.stackTagCompound.getInteger("world") == player.dimension)
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
				itemStack.stackTagCompound.setInteger("world", player.dimension);
				itemStack.stackTagCompound.setBoolean("firstBlockSet", true);
				player.addChatMessage(new ChatComponentText("Block selection saved"));
			}
			return true;
		}
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
		if(!world.isRemote && player.isSneaking())
		{
			if(itemStack.stackTagCompound.getBoolean("firstBlockSet"))
			{
				itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
				player.addChatMessage(new ChatComponentText("Block selection cleared"));
			}
			else
				player.addChatMessage(new ChatComponentText("No block selection to clear"));
		}
        return itemStack;
    }
	
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
		case 1:	
			createRectPrism(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 2:	
			createRectPrismHollow(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 3:	
			createCylinder(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 4:	
			createCylinderHollow(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 5:	
			createSphere(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 6:	
			createSphereHollow(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 7:	
			createHemisphere(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		case 8:	
			createHemisphereHollow(itemStack, world, x1, y1, z1, x2, y2, z2);
			break;
			
		default: break;
		}
	}
	
	public void createRectPrism(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
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
	}
	
	public void createRectPrismHollow(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
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
					if(i == minX || i == maxX || j == minY || j == maxY || k == minZ || k == maxZ)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createCylinder(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2))));
		int centerX = x1;
		int centerY = (y1 <= y2) ? y1 : y2;
		int centerZ = z1;
		int minX = (centerX - radius);
		int minZ = (centerZ - radius);
		int maxX = (centerX + radius);
		int maxZ = (centerZ + radius);
		int maxY = (y1 > y2) ? y1 : y2;
		
		for(int i = minX; i <= maxX; i++)
		{
			for(int j = centerY; j <= maxY; j++)
			{
				for(int k = minZ; k <= maxZ; k++)
				{
					if(Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(k - centerZ, 2)) <= radius)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createCylinderHollow(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2))));
		int centerX = x1;
		int centerY = (y1 <= y2) ? y1 : y2;
		int centerZ = z1;
		int minX = (centerX - radius);
		int minZ = (centerZ - radius);
		int maxX = (centerX + radius);
		int maxZ = (centerZ + radius);
		int maxY = (y1 > y2) ? y1 : y2;
		
		for(int i = minX; i <= maxX; i++)
		{
			for(int j = centerY; j <= maxY; j++)
			{
				for(int k = minZ; k <= maxZ; k++)
				{
					double r = Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(k - centerZ, 2));
					//System.out.println(r);
					if(r <= radius && r > radius - 1)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createSphere(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2))));
		int minX = x1 - radius;
		int minY = y1 - radius;
		int minZ = z1 - radius;
		int maxX = x1 + radius;
		int maxY = y1 + radius;
		int maxZ = z1 + radius;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					if(Math.sqrt(Math.pow(i - x1, 2) + Math.pow(j - y1, 2) + Math.pow(k - z1, 2)) <= radius)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createSphereHollow(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2))));
		int minX = x1 - radius;
		int minY = y1 - radius;
		int minZ = z1 - radius;
		int maxX = x1 + radius;
		int maxY = y1 + radius;
		int maxZ = z1 + radius;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					double r = Math.sqrt(Math.pow(i - x1, 2) + Math.pow(j - y1, 2) + Math.pow(k - z1, 2));
					if(r <= radius && r > radius - 1)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createHemisphere(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2))));
		int minX = x1 - radius;
		int minY = y1;
		int minZ = z1 - radius;
		int maxX = x1 + radius;
		int maxY = y1 + radius;
		int maxZ = z1 + radius;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					if(Math.sqrt(Math.pow(i - x1, 2) + Math.pow(j - y1, 2) + Math.pow(k - z1, 2)) <= radius)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
	
	public void createHemisphereHollow(ItemStack itemStack, World world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Block block = world.getBlock(x1, y1, z1);
		int metadata = world.getBlockMetadata(x1, y1, z1);
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2))));
		int minX = x1 - radius;
		int minY = y1;
		int minZ = z1 - radius;
		int maxX = x1 + radius;
		int maxY = y1 + radius;
		int maxZ = z1 + radius;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					double r = Math.sqrt(Math.pow(i - x1, 2) + Math.pow(j - y1, 2) + Math.pow(k - z1, 2));
					if(r <= radius && r > radius - 1)
					{
						world.setBlock(i, j, k, block, metadata, 2);
					}
				}
			}
		}
		itemStack.stackTagCompound.setBoolean("firstBlockSet", false);
	}
}