package wtf.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wtf.Core;

import java.util.List;

public class RecipeWrapper {

	private final NonNullList<NonNullList<ItemStack>> wrappedRecipe = NonNullList.create();
	public final ItemStack output;


	//modify the recipe wrapper to be an ArrayList<ArrayList<IngrediantWrapper>>
	//basic recipes, everything will have a size of one


	public RecipeWrapper(ShapelessRecipes rawRecipe){
		for (ItemStack stack: rawRecipe.recipeItems){
			wrappedRecipe.add(getSublist(rawRecipe, stack));
		}
		output = rawRecipe.getRecipeOutput();
	}


	public RecipeWrapper(ShapedRecipes rawRecipe){

		//int width = ObfuscationReflectionHelper.getPrivateValue(ShapedRecipes.class, rawRecipe, "recipeWidth");
		//int height = ObfuscationReflectionHelper.getPrivateValue(ShapedRecipes.class, rawRecipe, "recipeHeight");
		for (ItemStack stack : rawRecipe.recipeItems){
			wrappedRecipe.add(getSublist(rawRecipe, stack));
		}
		output = rawRecipe.getRecipeOutput();

	}

	public RecipeWrapper(ShapelessOreRecipe rawRecipe){
		
		for (Object obj : rawRecipe.getInput()){
			wrappedRecipe.add(getSublist(rawRecipe, obj));
		}
		output = rawRecipe.getRecipeOutput();
	}

	public RecipeWrapper(ShapedOreRecipe rawRecipe){
		int width = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, rawRecipe, "width");
		int height = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, rawRecipe, "height");

		//for (Object obj : rawRecipe.getInput()){
		//	wrappedRecipe.add(getSublist(obj));
		//}
		
		//  This approach doesn't work, and I don't know why 
		int count = 0;
		Object[] ingrediants = rawRecipe.getInput();
		for (int hloop = 0; hloop < 3; hloop++){
			for (int vloop = 0; vloop < 3; vloop++){
				if (hloop < height && vloop < width && count < ingrediants.length){
					wrappedRecipe.add(getSublist(rawRecipe, ingrediants[count]));
					count++;
				}
				else {
					wrappedRecipe.add(NonNullList.create());
				}
			}
		}
		output = rawRecipe.getRecipeOutput();
	}



	public NonNullList<NonNullList<ItemStack>> getIngrediants(){
		return wrappedRecipe;
	}

	
	private NonNullList<ItemStack> getSublist(IRecipe recipe, Object obj){
        NonNullList<ItemStack> subList = NonNullList.create();
		if (obj instanceof ItemStack){
			subList = parseItemStack(recipe, subList, (ItemStack)obj);
		}
		else if (obj instanceof List){
			for (Object subObj : (List<?>)obj){
				subList = parseItemStack(recipe, subList, (ItemStack)subObj);
			}
		}
		else if (obj == null){
			subList.add(ItemStack.EMPTY);
			//do nothing, this is a placeholder null in a shaped recipe
		}
		else {
			Core.coreLog.info("Skipping unrecognised object in a recipe " + obj);
		}
		return subList;
	}

	public NonNullList<ItemStack> parseItemStack(IRecipe recipe, NonNullList<ItemStack> subList, ItemStack stack){

		try {
			if (stack != ItemStack.EMPTY && (stack.getItemDamage() < 0 || stack.getItemDamage() > 15)){
				for (int loop = 0; loop < 15 ; loop ++){
					ItemStack newStack =new ItemStack(stack.getItem(), stack.getCount(), loop);
					subList.add(newStack);
				}
			}
			else {
				subList.add(stack);
			}

		}
		catch (NullPointerException e){
			if (recipe != null){
				Core.coreLog.info("Ignoring bad itemstack while trying to parse recipe ingrediant for unknown recipe ");
			}
		}
		return subList;
	}
}
