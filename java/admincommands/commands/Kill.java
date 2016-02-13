package admincommands.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import admincommands.Utils;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import scala.tools.nsc.doc.model.LowerBoundedTypeParamConstraint;

public class Kill implements ICommand {

	private List aliases;
	public Kill()  {
		this.aliases = new ArrayList();
		this.aliases.add("kill");
		this.aliases.add("smite");
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
		return "kill";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/kill <player>";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		boolean lightning = false;
		if(args.length == 2)
			System.out.println(StringUtils.join(args, ","));
			if(args[0].equalsIgnoreCase("-l")) {
				System.out.println("Ping");
				lightning = true;
				args[0] = args[1];
				System.out.println(StringUtils.join(args, ","));
			}
		
		EntityPlayerMP target;
		try{
			System.out.println(args[0]);
			target = Utils.findSinglePlayer(args[0]);
		} catch (PlayerNotFoundException e){
			commandsender.addChatMessage(new ChatComponentText("Player not found"));
			return;
		}
		
		if(lightning){
			World entityworld = target.worldObj;
			entityworld.spawnEntityInWorld(new EntityLightningBolt(entityworld, target.posX, target.posY, target.posZ));
		}
		target.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandsender) {
		return commandsender.canCommandSenderUseCommand(2, "");
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,
			String[] args) {
		
		return Utils.getNamesMatchingLastWord(args);		
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
