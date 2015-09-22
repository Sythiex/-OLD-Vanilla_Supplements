package com.sythiex.vanilla_supplements.network;

import com.sythiex.vanilla_supplements.VanillaSupplements;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandlerVS
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(VanillaSupplements.MODID);
}
