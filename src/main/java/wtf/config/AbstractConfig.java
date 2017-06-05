package wtf.config;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary;
import wtf.utilities.UBC.UBCCompat;

public abstract class AbstractConfig {

	protected final static String configPath = "config/WTF-Expedition/";
	
	
	public static BiomeDictionary.Type getBiomeTypeFromString(String biomestring) throws Exception{
		try {
			return BiomeDictionary.Type.getType(biomestring.toUpperCase());
		} catch (IllegalArgumentException e){
			throw new Exception("Ore Config Parsing Exception while trying to parse Biome Percent modifier : " + biomestring + ":  "  + "Unrecognised Forge BiomeDictionary BiomeType");
		}
	}
	
	public static Block getBlockFromString(String string){
		
			Block block = Block.getBlockFromName(string);
			if (block == null){
				try {
					throw new NoBlockRegistryFoundForStringException ("Unable to find block for " + string);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return block;
	}
	
	public static IBlockState getBlockState(String string){
		if (string == null || !string.contains("@")){
			return null;
		}
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);

		if (block == null){
			try {
				throw new NoBlockRegistryFoundForStringException("Unable to find block for " + stringArray[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stringArray.length < 1){
			try {
				throw new Exception("Unable to find metadata argument for :" + string +".");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		IBlockState state = block != null ? block.getStateFromMeta(Integer.parseInt(stringArray[1])) : null;
		if (state == null){
			try {
				throw new Exception("Invalid blockstate for block and meta " + string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state; 
	}

	public static ArrayList<IBlockState> getBlockStateArray(String string){
		ArrayList<IBlockState> list = new ArrayList<>();
		String[] stringArray = string.split(",");
		for (String substring :stringArray){

			switch (substring) {
				case "igneous":
					list.addAll(Arrays.asList(UBCCompat.IgneousStone));
					break;
				case "metamorphic":
					list.addAll(Arrays.asList(UBCCompat.MetamorphicStone));
					break;
				case "sedimentary":
					list.addAll(Arrays.asList(UBCCompat.SedimentaryStone));
					break;
				default:
					list.add(getBlockState(substring));
					break;
			}
		}
		return list;
	}

	protected static String[] appendArray(String[] arrayString, String stringToAdd){
		String[] newStringArray = new String[arrayString.length+1];
		System.arraycopy(arrayString, 0, newStringArray, 0, arrayString.length);
		newStringArray[newStringArray.length-1] = stringToAdd;
		return newStringArray;
		
		
	}
	public static class NoBlockRegistryFoundForStringException extends Exception{
		
		public NoBlockRegistryFoundForStringException(String string){
			super(string);
		}

		private static final long serialVersionUID = 1L;
		
		
	}
}
