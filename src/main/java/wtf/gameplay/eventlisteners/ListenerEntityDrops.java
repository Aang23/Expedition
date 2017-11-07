package wtf.gameplay.eventlisteners;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.config.GameplayConfig;

import java.util.Random;

public class ListenerEntityDrops {

	private Random random = new Random();
	
	@SubscribeEvent
	public void entityDrops(LivingDropsEvent event){
		if (event.getEntity() instanceof EntityPlayer || event.getSource().getTrueSource() instanceof EntityPlayer){
			return;
		}
		int r = random.nextInt(100);
		//System.out.println("Mob drop event caught " + r + " > " + GameplayConfig.mobDropsReqPlayer);
		if (r > GameplayConfig.mobDropsReqPlayer){
			//event.setCanceled(true);
			//System.out.println("Canceling drops");
		}
	}
	
}
