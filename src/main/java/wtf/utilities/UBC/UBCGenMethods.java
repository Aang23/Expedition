package wtf.utilities.UBC;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.worldgen.GeneratorMethods;

public class UBCGenMethods extends GeneratorMethods{

	public UBCGenMethods(World world, ChunkCoords coords, Random random) {
		super(world, coords, random);
	}


	@Override
	public void genFloatingStone(BlockPos pos){
		replaceBlock(pos, ReplacerUBCAbstract.getUBCStone(pos));
	}

	
}
