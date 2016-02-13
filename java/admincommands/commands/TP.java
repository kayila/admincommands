package admincommands.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import admincommands.Utils;
import admincommands.Utils.Coord;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

// TODO Dimension Teleport


public class TP implements ICommand{

	private List aliases;
	public TP()
	{
		this.aliases = new ArrayList();
		this.aliases.add("teleport");
		this.aliases.add("tp");
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "teleport";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		String usage = new String(""
				+ "/tp <username> - Teleport to a user"
				+ "/tp <username> <username> - Teleport first user to second user"
				+ "/tp <X> <Y> <Z> - Teleport to coords"
				+ "/tp <username> <X> <Y> <Z> - Teleport user to coords");
		return usage;
	}

	@Override
	public List getCommandAliases() {
	    return this.aliases;
	}

	private List<EntityPlayerMP> findMatches(String name) {
		// Setup the vars
		List<EntityPlayerMP> playersOnline = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		List<EntityPlayerMP> matches = new ArrayList<EntityPlayerMP>();
		
		// Find any matches
		for(int x=0; x<playersOnline.size(); x++) {
			EntityPlayerMP player = playersOnline.get(x);
			if (player.getDisplayName().toLowerCase().contains(name.toLowerCase())){
				// If this is an exact match, return only this name
				if (player.getDisplayName().toLowerCase() == name.toLowerCase()) {
					matches.clear();
					matches.add(player);
					return matches;
				}
				matches.add(player);
			}
		}
		
		return matches;
	}
	
	// Fix the coords to put us on the center of a block
	
	private Coord fixCoords(Coord location) {
		location.x = Math.floor(location.x)+0.5;
		location.y = Math.floor(location.y)+0.5;
		location.z = Math.floor(location.z)+0.5;
		return location;
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		
		// If we're using 1 or 3 parameters, this can only be run by a player
		if (args.length == 1 || args.length == 3) {
			if (!(commandsender instanceof EntityPlayerMP)) {
				System.err.println("This mode is only supported by players.");
				return;
			}
		}
		
		World entityworld = commandsender.getEntityWorld();
		List<EntityPlayerMP> matches = new ArrayList<EntityPlayerMP>();
		
		EntityPlayerMP player, target;
		Coord location = new Coord();
		Coord destination = new Coord();
		
		switch(args.length){
		case 1:
			// arg0 is destination player
			player = (EntityPlayerMP)commandsender;
			
			matches.clear();
			matches = findMatches(args[0]);
			System.out.println("TP >> Matches found: "+ matches.size());
			
			if (matches.size() == 1) {
				// We've found just one match, teleport me to them.
				target = matches.get(0);

				// Store my location
				location.dim = player.dimension;
				location.x = player.posX;
				location.y = player.posY;
				location.z = player.posZ;
				location = fixCoords(location);
				
				// Get my destination
				destination.dim = target.dimension;
				destination.x = target.posX;
				destination.y = target.posY;
				destination.z = target.posZ;
				destination = fixCoords(destination);
				
				// Teleport me
				Utils.teleportPlayer(player, destination);
				
				System.out.println(commandsender.getCommandSenderName()+">> Teleported "+player.getDisplayName()+" to "+matches.get(0).getDisplayName());
			} else {
				player.addChatMessage(new ChatComponentText("Non-singular match found("+matches.size()+"):"));
				for(int x=0; x<matches.size(); x++) {
					player.addChatMessage(new ChatComponentText("  "+matches.get(x).getDisplayName()));
				}
				throw new PlayerNotFoundException();
			}
			break;
		case 2:
			// arg0 is player to move
			// arg1 is destination player (target)
			
			// Find the player
			matches.clear();
			matches = findMatches(args[0]);
			System.out.println("TP >> Matches found: "+ matches.size());
			
			if (matches.size() == 1) {
				// We've found just one match, mark them
				player = matches.get(0);
			} else {
				System.err.println("TP >> (Target)Non-singular match");
				commandsender.addChatMessage(new ChatComponentText("(Target)Non-singular match found("+matches.size()+"):"));
				for(int x=0; x<matches.size(); x++) {
					commandsender.addChatMessage(new ChatComponentText("  "+matches.get(x).getDisplayName()));
				}
				throw new PlayerNotFoundException();
			}
			
			// Find the target
			matches.clear();
			matches = findMatches(args[1]);
			System.out.println("TP >> Matches found: "+ matches.size());
			
			if (matches.size() == 1) {
				// We've found just one match, mark them
				target = matches.get(0);
			} else {
				System.err.println("TP >> (Destination)Non-singular match");
				commandsender.addChatMessage(new ChatComponentText("(Destination)Non-singular match found("+matches.size()+"):"));
				for(int x=0; x<matches.size(); x++) {
					commandsender.addChatMessage(new ChatComponentText("  "+matches.get(x).getDisplayName()));
				}
				throw new PlayerNotFoundException();
			}

			// Store my location
			location.dim = player.dimension;
			location.x = player.posX;
			location.y = player.posY;
			location.z = player.posZ;
			location = fixCoords(location);
			
			// Get my destination
			destination.dim = target.dimension;
			destination.x = target.posX;
			destination.y = target.posY;
			destination.z = target.posZ;
			destination = fixCoords(destination);
			
			// Teleport me
			Utils.teleportPlayer(player, destination);
			System.out.println(commandsender.getCommandSenderName()+">> Teleported "+player.getDisplayName()+" to "+matches.get(0).getDisplayName());
			
			break;
		case 3:
			// arg0 = X
			// arg1 = Y
			// arg2 = Z
			player = (EntityPlayerMP)commandsender;

			// Store my location
			location.dim = player.dimension;
			location.x = player.posX;
			location.y = player.posY;
			location.z = player.posZ;
			location = fixCoords(location);
			
			// Get my destination
			destination.dim = player.dimension;
			destination.x = Double.parseDouble(args[0]);
			destination.y = Double.parseDouble(args[1]);
			destination.z = Double.parseDouble(args[2]);
			destination = fixCoords(destination);
			
			// Teleport me
			Utils.teleportPlayer(player, destination);
			System.out.println(commandsender.getCommandSenderName()+">> Teleported "+player.getDisplayName()+" to "+destination.dim+" - "+destination.x+","+destination.y+","+destination.z);
			
			break;
		case 4:
			// arg0 is target player
			// arg1 = X
			// arg2 = Y
			// arg3 = Z
			
			// Find the target
			matches.clear();
			matches = findMatches(args[0]);
			System.out.println("TP >> Matches found: "+ matches.size());
			
			if (matches.size() == 1) {
				// We've found just one match, mark them
				player = matches.get(0);
			} else {
				System.err.println("TP >> (Target)Non-singular match");
				break;
			}
			
			// Store my location
			location.dim = player.dimension;
			location.x = player.posX;
			location.y = player.posY;
			location.z = player.posZ;
			location = fixCoords(location);
			
			// Get my destination
			destination.dim = player.dimension;
			destination.x = Double.parseDouble(args[1]);
			destination.y = Double.parseDouble(args[2]);
			destination.z = Double.parseDouble(args[3]);
			destination = fixCoords(destination);
			
			// Teleport me
			Utils.teleportPlayer(player, destination);
			System.out.println(commandsender.getCommandSenderName()+">> Teleported "+player.getDisplayName()+" to "+destination.dim+" - "+destination.x+","+destination.y+","+destination.z);
			
			break;
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandsender) {
		return commandsender.canCommandSenderUseCommand(2, "");
	}

	@Override
	public List addTabCompletionOptions(ICommandSender commandsender,
			String[] args) {

		return Utils.getNamesMatchingLastWord(args);
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
