package admincommands.commands;

import java.util.ArrayList;
import java.util.List;

import admincommands.Utils.Coord;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class Dimension implements ICommand {

	private List aliases;
	public Dimension()  {
		this.aliases = new ArrayList();
		this.aliases.add("dimension");
		this.aliases.add("dim");
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
		return "dimension";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/dimension <dimension ID>";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		
		Coord cords = new Coord(Integer.parseInt(args[0]),
				commandsender.getEntityWorld().getSpawnPoint().posX,
				commandsender.getEntityWorld().getSpawnPoint().posY,
				commandsender.getEntityWorld().getSpawnPoint().posZ
				);
		admincommands.Utils.teleportPlayer((EntityPlayerMP)commandsender, cords);
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
