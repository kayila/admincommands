package admincommands;

import java.util.ArrayList;
import java.util.List;

import admincommands.Utils.Coord;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class Utils {
	
	public static class Coord {
		public int dim;
		public double x;
		public double y;
		public double z;
		
		public Coord() {};
		public Coord(int locDim, double locX, double locY, double locZ){
			dim = locDim;
			x = locX;
			y = locY;
			z = locZ;
		}
		public Coord(double locX, double locY, double locZ){
			x = locX;
			y = locY;
			z = locZ;
		}
	}

	public static void setLastCoords(EntityPlayerMP player, Coord last) {
		
		NBTTagCompound playerdata, lastcoords;
		
		playerdata = player.getEntityData();
		lastcoords = playerdata.getCompoundTag("lastcoords");
		
		lastcoords.setInteger("dim", last.dim);
		lastcoords.setDouble("posX", last.x);
		lastcoords.setDouble("posY", last.y);
		lastcoords.setDouble("posZ", last.z);
		playerdata.setTag("lastcoords",lastcoords);
		
	}
	
	public static Coord getLastCoords(EntityPlayerMP player) {
		
		NBTTagCompound playerdata, lastcoords;
		Coord last = new Coord();
		
		playerdata = player.getEntityData();
		lastcoords = playerdata.getCompoundTag("lastcoords");
		
		if(lastcoords.hasNoTags()) {
			return null;
		}
		
		last.dim = lastcoords.getInteger("dim");
		last.x = lastcoords.getDouble("posX");
		last.y = lastcoords.getDouble("posY");
		last.z = lastcoords.getDouble("posZ");
		
		return last;
	}
	
	public static List<EntityPlayerMP> findPlayerMatches(String name) {
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
	
	public static EntityPlayerMP findSinglePlayer(String name) {
		List<EntityPlayerMP> matches = findPlayerMatches(name);
		if(matches.size()!=1)
			throw new PlayerNotFoundException();
		return matches.get(0);
	}
	
	public static void teleportPlayer(EntityPlayerMP player, Coord coords){
		// Move player to the right dimension
		Coord lastcoords = new Coord(player.dimension,player.posX,player.posY,player.posZ);
		if(player.dimension != coords.dim) {
			player.travelToDimension(coords.dim);
		}
		
		player.playerNetServerHandler.setPlayerLocation(coords.x, coords.y, coords.z, player.rotationYaw, player.rotationPitch);
		setLastCoords(player,lastcoords);
	}
	
	public static List getNamesMatchingLastWord(String[] words) {
		List<String> possible = new ArrayList<String>();
		String[] users = MinecraftServer.getServer().getAllUsernames();
		for (int x=0; x<users.length; x++) {
			if (users[x].toLowerCase().contains(words[words.length-1])){
				possible.add(users[x]);
			}
		}
		return possible;
	}

}
