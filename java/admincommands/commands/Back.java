package admincommands.commands;

import java.util.ArrayList;
import java.util.List;

import admincommands.Utils;
import admincommands.Utils.Coord;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Back implements ICommand {

	private List aliases;
	public Back()  {
		this.aliases = new ArrayList();
		this.aliases.add("back");
	}
	
	@Override
	public List getCommandAliases() {
		return this.aliases;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "back";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/back";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		
		World entityworld = commandsender.getEntityWorld();
		List<EntityPlayerMP> matches = new ArrayList<EntityPlayerMP>();
		
		EntityPlayerMP player, target;
		int destDim;
		double destX,destY,destZ;
		Coord lastcoords;
		
		player = (EntityPlayerMP)commandsender;
		
		lastcoords = Utils.getLastCoords(player);
		
		if(lastcoords == null) {
			player.addChatMessage(new ChatComponentText("No back data"));
			return;
		}
		
		destDim = lastcoords.dim;
		destX = lastcoords.x;
		destY = lastcoords.y;
		destZ = lastcoords.z;

		Utils.teleportPlayer(player, new Coord(destX, destY, destZ));
		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandsender) {
		return commandsender.canCommandSenderUseCommand(2, "");
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,
			String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
