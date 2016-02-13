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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class Unbind implements ICommand {

	private List aliases;
	public Unbind()  {
		this.aliases = new ArrayList();
		this.aliases.add("unbind");
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
		return "unbind";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/unbind";
	}
	
	@Override
	public void processCommand(ICommandSender commandsender, String[] args) {
		ItemStack heldItem = ((EntityPlayerMP)commandsender).inventory.getCurrentItem();
		NBTTagCompound playerdata, playerbinds;
		playerdata = ((EntityPlayerMP)commandsender).getEntityData();
		playerbinds = playerdata.getCompoundTag("playerbinds");
		String itemID = Integer.toString(Item.getIdFromItem(heldItem.getItem()));
		if (!(heldItem.getItem().isDamageable())) {
				itemID += ":"+Integer.toString(heldItem.getItemDamage());
		}
		
		playerbinds.removeTag(itemID);;
		playerdata.setTag("playerbinds", playerbinds);
		
		((EntityPlayerMP)commandsender).addChatMessage(new ChatComponentText("Command cleared"));
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
