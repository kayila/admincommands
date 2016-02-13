package admincommands.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import scala.Array;

public class AC implements ICommand {

	private List aliases;
	public AC()  {
		this.aliases = new ArrayList();
		this.aliases.add("ac");
		this.aliases.add("admincommands");
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
		return "ac";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/ac <command> [parameters]";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		// Disbatch to other commands
		
		if(args.length == 0) {
			return;
		}
		
		String command = args[0];
		String[] newargs = new String[]{};
		if (args.length>1) {
			newargs = Arrays.copyOfRange(args, 1, args.length);
		}
		
		ICommand commandToRun = null;
		command=command.toLowerCase();
		if(command.contentEquals("bind")){
			commandToRun = new admincommands.commands.Bind();
		} else if (command.contentEquals("unbind")) {
			commandToRun = new admincommands.commands.Unbind();
		}
		
		if(commandToRun != null){
			if(commandToRun.canCommandSenderUseCommand(commandsender)){
				commandToRun.processCommand(commandsender, newargs);
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandsender) {
		return true;
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
