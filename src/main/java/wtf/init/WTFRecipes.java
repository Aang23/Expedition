package wtf.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wtf.config.GameplayConfig;
import wtf.utilities.RecipeHelper;

public class WTFRecipes {
	
	public static void initRecipes(){
		RecipeHelper.getShapelessRecipe(new ItemStack(Items.GUNPOWDER), WTFItems.sulfur, WTFItems.nitre, WTFItems.nitre, WTFItems.nitre, new ItemStack(Items.COAL, 1, 1));
		if (GameplayConfig.homescroll){
			RecipeHelper.addShapedRecipe(new ItemStack(WTFItems.homescroll), "x","y","x",'x', new ItemStack(Items.PAPER), 'y', new ItemStack(Items.ENDER_PEARL));
		}
		RecipeHelper.getShapelessRecipe(new ItemStack(Items.DYE, 1, 15), WTFItems.nitre);
		
		if (GameplayConfig.sulfurRecipe){
			RecipeHelper.getShapelessRecipe(new ItemStack(WTFItems.sulfur, 1), Blocks.NETHERRACK);
		}
		
		if (GameplayConfig.wcictable){
			RecipeHelper.addShapedRecipe(new ItemStack(WTFBlocks.wcicTable), " ","y","x",'y', new ItemStack(Items.BOOK), 'x', new ItemStack(Blocks.CRAFTING_TABLE));
		}
	}
	
}
