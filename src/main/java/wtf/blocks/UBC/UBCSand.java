package wtf.blocks.UBC;

import exterminatorjeff.undergroundbiomes.api.enums.SedimentaryVariant;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import wtf.Core;

public class UBCSand extends BlockFalling{

	private static final PropertyEnum<SedimentaryVariant> VARIANT = PropertyEnum.create("variant", SedimentaryVariant.class);
	
	public UBCSand(){
		this.setCreativeTab(Core.wtfTab);
		 this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SedimentaryVariant.LIMESTONE));
		 //OreDictionary.registerOre("sand", this);
		 this.setSoundType(SoundType.SAND);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, SedimentaryVariant.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		if (getCreativeTabToDisplayOn() == itemIn)
			for (int loop = 0; loop < 8; loop++){
				items.add(new ItemStack(this, 1, loop));
			}
	}
	
}
