package wtf.gameplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWTFSlidingBlock extends EntityWTFFallingBlock{

	public EntityWTFSlidingBlock(World worldIn, BlockPos pos, BlockPos targetpos, IBlockState fallingBlockState) {
		super(worldIn, pos, fallingBlockState);
		double motx = pos.getX() - targetpos.getX();
		double motz = pos.getZ() - targetpos.getZ();
		addVelocity(0.05D * motx, -0.1D, 0.05D * motz);
		
		worldIn.spawnEntity(this);
		worldIn.setBlockToAir(pos);
	
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

    
	@Override
	public void onUpdate()
    {
		if (this.fallTime++ > 18){
    		super.onUpdate();
    	}
    	else {
    		this.move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
    	}
    }
	
	
	
}
