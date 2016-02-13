package admincommands;


import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
 
@Mod(modid = AdminCommands.MODID, name = AdminCommands.NAME, version = AdminCommands.VERSION, acceptableRemoteVersions = "*")
public class AdminCommands
{
	
    public static final String MODID = "admincommands";
    public static final String NAME = "Admin Commands";
    public static final String VERSION = "2.1";
 
    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	
    	Tweaks.runTweaks();
    	event.registerServerCommand(new admincommands.commands.TP());
    	event.registerServerCommand(new admincommands.commands.Back());
    	event.registerServerCommand(new admincommands.commands.Lightning());
    	event.registerServerCommand(new admincommands.commands.Bind());
    	event.registerServerCommand(new admincommands.commands.Unbind());
    	event.registerServerCommand(new admincommands.commands.Dimension());
    	event.registerServerCommand(new admincommands.commands.Kill());
    	event.registerServerCommand(new admincommands.commands.AC());
    	MinecraftForge.EVENT_BUS.register(new admincommands.AdminCommands());
    }
    
    @SubscribeEvent(priority=EventPriority.NORMAL,receiveCanceled=false)
    public void onCommandEvent(CommandEvent event) {
    	
    	//System.out.println(event.command.toString());
    	
    	if(event.command.getClass().isInstance(new net.minecraft.command.server.CommandTeleport())) {
    		admincommands.commands.TP tp = new admincommands.commands.TP();
    		if(tp.canCommandSenderUseCommand(event.sender)){
    			tp.processCommand(event.sender, event.parameters);
    			event.setCanceled(true);
    		}
    	}

    	if(event.command.getClass().isInstance(new net.minecraft.command.CommandKill())) {
    		admincommands.commands.Kill kill = new admincommands.commands.Kill();
    		if(kill.canCommandSenderUseCommand(event.sender)){
    			kill.processCommand(event.sender, event.parameters);
    			event.setCanceled(true);
    		}
    	}
    }
    
    @SubscribeEvent(priority=EventPriority.HIGH)
    public void onItemUse(PlayerInteractEvent event) {
    	if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
    		return;
    	}
    	ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();
		NBTTagCompound playerdata, playerbinds;
		playerdata = event.entityPlayer.getEntityData();
		playerbinds = playerdata.getCompoundTag("playerbinds");

		if (playerbinds.hasNoTags()) {
			return;
		}
		String itemID = Integer.toString(Item.getIdFromItem(heldItem.getItem()));
		if (!(heldItem.getItem().isDamageable())) {
			itemID += ":"+Integer.toString(heldItem.getItemDamage());
		}
	
		String command = playerbinds.getString(itemID);
		
		if (command.isEmpty())
			return;

		MinecraftServer.getServer().getCommandManager().executeCommand(event.entityPlayer, command);
		
		event.setCanceled(true);
    }
}
