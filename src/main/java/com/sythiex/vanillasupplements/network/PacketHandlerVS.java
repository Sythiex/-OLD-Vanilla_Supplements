package com.sythiex.vanillasupplements.network;

import com.sythiex.vanillasupplements.VanillaSupplements;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandlerVS
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(VanillaSupplements.MODID);
}
