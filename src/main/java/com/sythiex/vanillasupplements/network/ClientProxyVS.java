package com.sythiex.vanillasupplements.network;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxyVS extends CommonProxyVS
{
	public static KeyBinding wandMode = new KeyBinding("key.wand_mode.desc", Keyboard.KEY_F, "key.vanilla_supplements.category");
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		super.init(event);
		ClientRegistry.registerKeyBinding(wandMode);
    }
}