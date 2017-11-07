package wtf.init;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import wtf.blocks.substitution.BlockWTFTorch;
import wtf.config.GameplayConfig;
import wtf.config.MasterConfig;

public class WTFSubstitutions {
	
	public static void init(){
		
		BlockWTFTorch.torch_on = WTFBlocks.registerBlock(new BlockWTFTorch(true), "torch_on");
		
		if (MasterConfig.gameplaytweaks && GameplayConfig.replaceTorch){

			BlockWTFTorch.torch_off.setUnlocalizedName("torch");
			System.out.println("Attempting torch replacement");
			ForgeRegistries.BLOCKS.register(new BlockWTFTorch(false).setRegistryName(Blocks.TORCH.getRegistryName()));

		}
		
		/*
		if (OverworldGenConfig.subLeaves){
			
			Block oldLeaves = new CustomOldLeaves().setUnlocalizedName("leaves");		
			try {
				GameRegistry.addSubstitutionAlias("minecraft:leaves", GameRegistry.Type.BLOCK, oldLeaves);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}
			oldLeaves.setRegistryName("minecraft:leaves");


			Block newLeaves = new CustomNewLeaves().setUnlocalizedName("leaves2");		
			try {
				GameRegistry.addSubstitutionAlias("minecraft:leaves2", GameRegistry.Type.BLOCK, newLeaves);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}
			newLeaves.setRegistryName("minecraft:leaves2");

			
		}
		*/
	}
	
}
