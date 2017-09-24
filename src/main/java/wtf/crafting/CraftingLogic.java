package wtf.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.HashMap;

public class CraftingLogic {
	
	public static NonNullList<RecipeWrapper> getCraftableStack(EntityPlayer player) {
		
		HashMap<Integer, Integer> inventoryHashMap = new HashMap<>();
		
		for (ItemStack stack : player.inventory.mainInventory){
			if (stack != ItemStack.EMPTY){
				inventoryHashMap.put(itemNum(stack), stack.getCount());
			}
		}

		NonNullList<RecipeWrapper> craftableRecipes = NonNullList.create();
	
		for (RecipeWrapper recipe : RecipeParser.parsedRecipes) {
			if (canCraft(recipe, inventoryHashMap)) {
				craftableRecipes.add(recipe);
			}
		}
		return craftableRecipes;
	}
	
	public static boolean canCraft(RecipeWrapper recipe, HashMap<Integer, Integer> inventoryHashMap){
		for (NonNullList<ItemStack> subList : recipe.getIngrediants()){
			if (subList.size() > 0 && !hasIngrediant(inventoryHashMap, subList)){
					return false;
			}
		}
		return true;
		
	}
	
	public static boolean hasIngrediant(HashMap<Integer, Integer> inventoryHashMap, NonNullList<ItemStack> subList){
		for (ItemStack ingrediant : subList){
			if(ingrediant == ItemStack.EMPTY){
				return true;
			}
			if (inventoryHashMap.containsKey(itemNum(ingrediant))){// && inventoryHashMap.get(ingrediant) >= ingrediant.stackSize){
				return true;
			}
		}
		return false;
	}
	
	private static int itemNum(ItemStack stack){
		return Item.getIdFromItem(stack.getItem())*16+stack.getItemDamage();
	}
	

	


}
