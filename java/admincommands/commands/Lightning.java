package admincommands.commands;

import java.util.ArrayList;
import java.util.List;

import admincommands.Utils.Coord;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Lightning implements ICommand {

	private List aliases;
	public Lightning()  {
		this.aliases = new ArrayList();
		this.aliases.add("lightning");
		this.aliases.add("lightningbolt");
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
		return "lightning";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/lightning";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		
		World entityworld = commandsender.getEntityWorld();
		
		EntityPlayerMP player = (EntityPlayerMP)commandsender;
		
		
        Vec3 vec3 = Vec3.createVectorHelper(player.posX, player.posY+player.getEyeHeight(), player.posZ);
        Vec3 vec31 = player.getLookVec();
        Vec3 vec32 = vec3.addVector(vec31.xCoord * 500, vec31.yCoord * 500, vec31.zCoord * 500);
        //MovingObjectPosition mop = player.worldObj.func_147447_a(vec3, vec32, false, false, true);
        MovingObjectPosition mop = player.worldObj.rayTraceBlocks(vec3, vec32);
        
        if(mop == null) {
        	entityworld.playSoundEffect(player.posX, player.posY, player.posZ, "minecraft:ambient.weather.thunder", 1, 1);
        	return;
        }
		
		entityworld.spawnEntityInWorld(new EntityLightningBolt(entityworld, mop.blockX, mop.blockY, mop.blockZ));
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
