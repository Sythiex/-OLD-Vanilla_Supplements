package com.sythiex.vanilla_supplements;

import com.sythiex.vanilla_supplements.blocks.BlockBerryBush;
import com.sythiex.vanilla_supplements.blocks.BlockCotton;
import com.sythiex.vanilla_supplements.items.ItemEmeraldArmor;
import com.sythiex.vanilla_supplements.items.ItemEmeraldAxe;
import com.sythiex.vanilla_supplements.items.ItemEmeraldHoe;
import com.sythiex.vanilla_supplements.items.ItemEmeraldPickaxe;
import com.sythiex.vanilla_supplements.items.ItemEmeraldShovel;
import com.sythiex.vanilla_supplements.items.ItemEmeraldSword;
import com.sythiex.vanilla_supplements.items.ItemFoodVS;
import com.sythiex.vanilla_supplements.items.ItemSeedsVS;
import com.sythiex.vanilla_supplements.items.ItemVS;
import com.sythiex.vanilla_supplements.items.ItemWand;
import com.sythiex.vanilla_supplements.world.WorldGenVS;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = VanillaSupplements.MODID, version = VanillaSupplements.VERSION)
public class VanillaSupplements
{
    public static final String MODID = "vanilla_supplements";
    public static final String VERSION = "0.2.0";
    
    //config variables
    public static boolean addEmeraldTools;
    public static boolean addCotton;
    public static boolean addBerries;
    public static boolean addFertilizer;
    public static boolean addGrassBlockRecipe;
    public static boolean addClayRecipies;
    public static boolean addVineRecipe;
    public static boolean addStringRecipe;
    public static boolean addMossStoneRecipe;
    
    //blocks
    public static Block cottonPlant;
    public static Block berryBush;
    
    //items
    public static Item seedCotton;
    public static Item cotton;
    public static Item berry;
    public static Item fertilizer;
    
    public static Item wand;
    
    public static Item emeraldShovel;
    public static Item emeraldPickaxe;
    public static Item emeraldAxe;
    public static Item emeraldHoe;
    public static Item emeraldSword;
    
    public static Item emeraldHelmet;
    public static Item emeraldChestplate;
    public static Item emeraldLeggings;
    public static Item emeraldBoots;
    
    //materials
    static ToolMaterial trueEmerald_Tool = EnumHelper.addToolMaterial("trueEmerald_Tool", 3, 500, 6.0F, 2.0F, 25);
    static ArmorMaterial trueEmerald_Armor = EnumHelper.addArmorMaterial("trueEmerald_Armor", 23, new int[]{3, 8, 6, 3}, 25);
    
    public static WorldGenVS worldGen = new WorldGenVS();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	addEmeraldTools = config.getBoolean("Enable emerald tools/armor", config.CATEGORY_GENERAL, true, "Change to false to remove crafting recipies for emerald tools/armor");
    	addCotton = config.getBoolean("Enable cotton plants", config.CATEGORY_GENERAL, true, "Change to false to remove cotton seed drops");
    	addBerries = config.getBoolean("Enable berries", config.CATEGORY_GENERAL, true, "Change to false to disable berry bush generation");
    	addFertilizer = config.getBoolean("Enable fertilizer", config.CATEGORY_GENERAL, true, "Change to false to disable crafting fertilizer from rotten flesh");
    	addStringRecipe = config.getBoolean("Enable string recipe", config.CATEGORY_GENERAL, false, "Change to true to allow crafting string from wool (Considered overpowered with some mods)");
    	addGrassBlockRecipe = config.getBoolean("Enable grass block recipe", config.CATEGORY_GENERAL, true, "Change to false to remove crafting grass blocks with dirt and seeds");
    	addClayRecipies = config.getBoolean("Enable clay recipies", config.CATEGORY_GENERAL, true, "Change to false to remove convenience recipies for clay");
    	addVineRecipe = config.getBoolean("Enable vine recipe", config.CATEGORY_GENERAL, true, "Change to false to remove crafting vines from grass");
    	addMossStoneRecipe = config.getBoolean("Enable 1.8 mossy cobblestone recipe", config.CATEGORY_GENERAL, true, "Change to false to remove the MC 1.8 recipe for mossy cobblestone");
    	
    	config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	registerBlocks();
    	registerItems();
    	registerOreDictionary();
    	registerRecipies();
    	
    	GameRegistry.registerWorldGenerator(worldGen, 1000);
    	
    	if(addCotton)
    		MinecraftForge.addGrassSeed(new ItemStack(seedCotton), 7);
    }
    
    private void registerBlocks()
    {
    	cottonPlant = new BlockCotton();
    	GameRegistry.registerBlock(cottonPlant, MODID + "_cottonPlant");
    	
    	berryBush = new BlockBerryBush();
    	GameRegistry.registerBlock(berryBush, MODID + "_berryBush");
    }
    
    private void registerItems()
    {
    	seedCotton = new ItemSeedsVS(cottonPlant, Blocks.farmland).setUnlocalizedName("seedCotton").setTextureName(MODID + ":seedCotton");
    	GameRegistry.registerItem(seedCotton, MODID + "_seedCotton");
    	
    	cotton = new ItemVS().setUnlocalizedName("cotton").setTextureName(MODID + ":cotton").setCreativeTab(CreativeTabs.tabMaterials);
    	GameRegistry.registerItem(cotton, MODID + "_cotton");
    	
    	berry = new ItemFoodVS(3, 4.0F).setUnlocalizedName("berry").setTextureName(MODID + ":berry").setCreativeTab(CreativeTabs.tabFood);
    	GameRegistry.registerItem(berry, MODID + "_berry");
    	
    	fertilizer = new ItemVS().setUnlocalizedName("fertilizer").setTextureName(MODID + ":fertilizer").setCreativeTab(CreativeTabs.tabMaterials);
    	GameRegistry.registerItem(fertilizer, MODID + "_fertilizer");
    	
    	wand = new ItemWand().setUnlocalizedName("wand").setTextureName(MODID + ":wand").setCreativeTab(CreativeTabs.tabTools);
    	GameRegistry.registerItem(wand, MODID + "_wand");
    	
    	//tools
    	emeraldShovel = new ItemEmeraldShovel(trueEmerald_Tool);
    	GameRegistry.registerItem(emeraldShovel, MODID + "_emeraldShovel");
    	
    	emeraldPickaxe = new ItemEmeraldPickaxe(trueEmerald_Tool);
    	GameRegistry.registerItem(emeraldPickaxe, MODID + "_emeraldPickaxe");
    	
    	emeraldAxe = new ItemEmeraldAxe(trueEmerald_Tool);
    	GameRegistry.registerItem(emeraldAxe, MODID + "_emeraldAxe");
    	
    	emeraldHoe = new ItemEmeraldHoe(trueEmerald_Tool);
    	GameRegistry.registerItem(emeraldHoe, MODID + "_emeraldHoe");
    	
    	emeraldSword = new ItemEmeraldSword(trueEmerald_Tool);
    	GameRegistry.registerItem(emeraldSword, MODID + "_emeraldSword");
    	
    	//armor
    	emeraldHelmet = new ItemEmeraldArmor(trueEmerald_Armor, 0);
    	GameRegistry.registerItem(emeraldHelmet, MODID + "_emeraldHelmet");
    		
    	emeraldChestplate = new ItemEmeraldArmor(trueEmerald_Armor, 1);
    	GameRegistry.registerItem(emeraldChestplate, MODID + "_emeraldChestplate");
    		
    	emeraldLeggings = new ItemEmeraldArmor(trueEmerald_Armor, 2);
    	GameRegistry.registerItem(emeraldLeggings, MODID + "_emeraldLeggings");
    		
    	emeraldBoots = new ItemEmeraldArmor(trueEmerald_Armor, 3);
    	GameRegistry.registerItem(emeraldBoots, MODID + "_emeraldBoots");
    }
    
    private void registerOreDictionary()
    {
    	OreDictionary.registerOre("seedCotton", new ItemStack(seedCotton));
    	OreDictionary.registerOre("cropCotton", new ItemStack(cotton));
    	OreDictionary.registerOre("foodBerry", new ItemStack(berry));
    	OreDictionary.registerOre("cropBerry", new ItemStack(berry));
    	OreDictionary.registerOre("materialFertilizer", new ItemStack(fertilizer));
    }
    
    private void registerRecipies()
    {
    	if(addEmeraldTools)
    	{
    		//tools
    		GameRegistry.addRecipe(new ItemStack(emeraldShovel), new Object[]{
    			"E",
    			"S",
    			"S",
    			'E', Items.emerald, 'S', Items.stick});
    	
    		GameRegistry.addRecipe(new ItemStack(emeraldPickaxe), new Object[]{
    			"EEE",
    			" S ",
    			" S ",
    			'E', Items.emerald, 'S', Items.stick});
    	
    		GameRegistry.addRecipe(new ItemStack(emeraldAxe), new Object[]{
    			"EE",
    			"ES",
    			" S",
    			'E', Items.emerald, 'S', Items.stick});
    	
    		GameRegistry.addRecipe(new ItemStack(emeraldHoe), new Object[]{
    			"EE",
    			" S",
    			" S",
    			'E', Items.emerald, 'S', Items.stick});
    	
    		GameRegistry.addRecipe(new ItemStack(emeraldSword), new Object[]{
    			"E",
    			"E",
    			"S",
    			'E', Items.emerald, 'S', Items.stick});
    		
    		//armor
    		GameRegistry.addRecipe(new ItemStack(emeraldHelmet), new Object[]{
    			"EEE",
    			"E E",
    			'E', Items.emerald});
    		
    		GameRegistry.addRecipe(new ItemStack(emeraldChestplate), new Object[]{
    			"E E",
    			"EEE",
    			"EEE",
    			'E', Items.emerald});
    		
    		GameRegistry.addRecipe(new ItemStack(emeraldLeggings), new Object[]{
    			"EEE",
    			"E E",
    			"E E",
    			'E', Items.emerald});
    		
    		GameRegistry.addRecipe(new ItemStack(emeraldBoots), new Object[]{
    			"E E",
    			"E E",
    			'E', Items.emerald});
    	}
    	
    	GameRegistry.addRecipe(new ShapedOreRecipe(Items.string, new Object[]{
			"CCC",
			'C', "cropCotton"}));
    	
    	if(addFertilizer)
    	{
    		GameRegistry.addShapelessRecipe(new ItemStack(fertilizer, 8), new ItemStack(Items.rotten_flesh));
    	}
    	
    	if(addGrassBlockRecipe)
    	{
    		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.grass), new ItemStack(Blocks.dirt), new ItemStack(Items.wheat_seeds));
    	}
    	
    	if(addClayRecipies)
    	{
    		GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4), new ItemStack(Blocks.clay));
    		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.sand), new ItemStack(Items.water_bucket));
    	}
    	
    	if(addVineRecipe)
    	{
    		GameRegistry.addRecipe(new ItemStack(Blocks.vine), new Object[]{
    			"GG",
    			"GG",
    			'G', Blocks.tallgrass});
    	}
    	
    	if(addStringRecipe)
    	{
    		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 4), new ItemStack(Blocks.wool));
    	}
    	
    	if(addMossStoneRecipe)
    	{
    		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
    	}
    }
}