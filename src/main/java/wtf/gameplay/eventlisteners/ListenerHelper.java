package wtf.gameplay.eventlisteners;

import net.minecraft.item.ItemStack;

public class ListenerHelper {

	public static boolean isHammer(ItemStack stack) {
		return stack != null && stack.getItem().getItemStackDisplayName(stack).contains("ammer");

	}
	
}
