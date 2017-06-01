package wtf.gameplay.eventlisteners;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ListenerBlockNameGetter {



	@SubscribeEvent
	public void rightClick(RightClickBlock event)
	{
		if (event.getItemStack() != ItemStack.EMPTY){
			Block block = Block.getBlockFromItem(event.getItemStack().getItem());
			if (block != null){
				event.getEntityPlayer().sendMessage(new TextComponentString("The block registry name is : " + block.getRegistryName()));
				event.getEntityPlayer().sendMessage(new TextComponentString("The block metadata is : " + event.getItemStack().getItemDamage()));
			}
			else {
				event.getEntityPlayer().sendMessage(new TextComponentString("The item registry name is : " + event.getItemStack().getItem().getRegistryName()));
				event.getEntityPlayer().sendMessage(new TextComponentString("The item metadata is : " + event.getItemStack().getItemDamage()));
				event.getEntityPlayer().sendMessage(new TextComponentString("The stack number is : " + event.getItemStack().getCount()));
				
			}
		}
	}

}
