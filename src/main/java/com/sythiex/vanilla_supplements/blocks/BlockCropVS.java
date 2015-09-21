package com.sythiex.vanilla_supplements.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCropVS extends BlockBush implements IGrowable
{
	public static int maxGrowthStage = 7;
	
	@SideOnly(Side.CLIENT)
    protected IIcon[] iIcon;
	
	public BlockCropVS()
	{
		setTickRandomly(true);
		float f = .5F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        setCreativeTab((CreativeTabs)null);
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
        disableStats();
	}
	
	@Override
	protected boolean canPlaceBlockOn(Block block)
	{
		return block == Blocks.farmland;
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return (world.getBlock(x, y - 1, z) == Blocks.farmland) ? true : false;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
	    super.updateTick(world, x, y, z, rand);
	    
	    if (world.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int metadata = world.getBlockMetadata(x, y, z);

            if (metadata < maxGrowthStage)
            {
                float f = this.getUpdateWeight(world, x, y, z);

                if (rand.nextInt((int)(30.0F / f) + 1) == 0)
                {
                    ++metadata;
                    world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
                }
            }
        }
	}
	
	public void incrementGrowStage(World world, Random rand, int x, int y, int z)
	{
		int growStage = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(rand, 2, 5);
		
		if(growStage > maxGrowthStage)
			growStage = maxGrowthStage;
		
		world.setBlockMetadataWithNotify(x, y, z, growStage, 2);
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
		return EnumPlantType.Crop;
    }
	
	@Override
	public int getRenderType()
	{
		return 1; //flower style cross
	}
	
	@Override
	public IIcon getIcon(int side, int growthStage)
	{
		return iIcon[growthStage];
	}
	
	/*
	 * is bonemeal allowed?
	 * (non-Javadoc)
	 * @see net.minecraft.block.IGrowable#func_149851_a(net.minecraft.world.World, int, int, int, boolean)
	 */
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_)
	{
		return world.getBlockMetadata(x, y, z) != maxGrowthStage;
	}

	/*
	 * are conditions for bonemeal growth tick acceptable?
	 * (non-Javadoc)
	 * @see net.minecraft.block.IGrowable#func_149852_a(net.minecraft.world.World, java.util.Random, int, int, int)
	 */
	@Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
	{
		return true;
	}

	/*
	 * processes growth-tick
	 * (non-Javadoc)
	 * @see net.minecraft.block.IGrowable#func_149853_b(net.minecraft.world.World, java.util.Random, int, int, int)
	 */
	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z)
	{
		incrementGrowStage(world, rand, x, y, z);
	}
	
	/*
	 * f*cking crazy wheat growth tick function thing
	 * I have no clue how the weight system works
	 */
	private float getUpdateWeight(World world, int x, int y, int z)
    {
        float f = 1.0F;
        Block block = world.getBlock(x, y, z - 1);
        Block block1 = world.getBlock(x, y, z + 1);
        Block block2 = world.getBlock(x - 1, y, z);
        Block block3 = world.getBlock(x + 1, y, z);
        Block block4 = world.getBlock(x - 1, y, z - 1);
        Block block5 = world.getBlock(x + 1, y, z - 1);
        Block block6 = world.getBlock(x + 1, y, z + 1);
        Block block7 = world.getBlock(x - 1, y, z + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int i = x - 1; i <= x + 1; ++i)
        {
            for (int j = z - 1; j <= z + 1; ++j)
            {
                float f1 = 0.0F;

                if (world.getBlock(i, y - 1, j).canSustainPlant(world, i, y - 1, j, ForgeDirection.UP, this))
                {
                    f1 = 1.0F;

                    if (world.getBlock(i, y - 1, j).isFertile(world, i, y - 1, j))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != x || j != z)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }
}