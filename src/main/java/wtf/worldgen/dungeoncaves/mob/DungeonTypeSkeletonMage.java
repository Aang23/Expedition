package wtf.worldgen.dungeoncaves.mob;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.entities.simpleentities.SkeletonMage;
import wtf.utilities.wrappers.CavePosition;
import wtf.worldgen.GeneratorMethods;

public class DungeonTypeSkeletonMage extends DungeonSimpleSkeleton{


	public DungeonTypeSkeletonMage() {
		super("SkeletonMage");
	}

	@Override
	public void generateCenter(GeneratorMethods gen, Random rand, CavePosition pos, float depth) {
		gen.replaceBlock(pos.getFloorPos().up(2), Blocks.ENCHANTING_TABLE.getDefaultState());
		gen.spawnVanillaSpawner(pos.getFloorPos().up(), EntityList.getKey(SkeletonMage.class), 2);
	}

	@Override
	public void generateWall(GeneratorMethods gen, Random random, BlockPos pos, float depth, int height) {
		if (height == 3){
			gen.replaceBlock(pos, Blocks.BOOKSHELF.getDefaultState());
		}
		else {
			super.generateWall(gen, random, pos, depth, height);
		}
	}
}
