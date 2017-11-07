package wtf.worldgen.caves;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import wtf.worldgen.caves.types.*;
import wtf.worldgen.dungeoncaves.AbstractDungeonType;
import wtf.worldgen.dungeoncaves.DungeonTypeRegister;

import java.util.ArrayList;
import java.util.HashMap;


public class CaveTypeRegister {

	private static HashMap<Biome, CaveProfile> cavebiomemap = new HashMap<>();


	//jacko lantern
	//mineshaft, nether portal, mushroom, cave in,
	//carved stone, carved sandstone

	private static int floorChance = 2;
	private static int ceilingChance = 3;

	//shallow caves
	private static final AbstractCaveType simple = new CaveTypeDefault("default", floorChance, ceilingChance);

	private static final AbstractCaveType wet =new CaveTypeWet ("wet", floorChance, ceilingChance+1);
	
	private static final AbstractCaveType swamp = new CaveTypeSwamp ("swamp", floorChance, ceilingChance+2);
	
	private static final AbstractCaveType sandy = new CaveTypeSandy ("sandy", floorChance, ceilingChance, false);
	public static final AbstractCaveType redSandy = new CaveTypeSandy ("redSand", floorChance, ceilingChance, true);
	private static final AbstractCaveType sandyRocky = new CaveTypeRockySandy ("sandRocky", floorChance, ceilingChance+3, false);
	private static final AbstractCaveType redSandyRocky = new CaveTypeRockySandy ("redsandRocky", floorChance, ceilingChance+3, true);
	private static final AbstractCaveType paintedDesert = new CaveTypePaintedDesert("paintedDesert", 0, ceilingChance);
	
	private static final AbstractCaveType lush = new CaveTypeLush ("lush", floorChance+3, ceilingChance+12);
	private static final AbstractCaveType lushVolcanic = new CaveTypeJungleVolcano ("jungleVolcano", floorChance+3, ceilingChance+12);
	
	private static final AbstractCaveType rocky = new CaveTypeRocky ("rocky", floorChance, ceilingChance+3);
	
	private static final AbstractCaveType ice = new CaveTypeIce ("ice", floorChance, ceilingChance+3);
	private static final AbstractCaveType iceRocky = new CaveTypeIceRocky ("iceRocky", floorChance, ceilingChance+3);
	
	private static final AbstractCaveType fungal = new CaveTypeFungal ("fungal", floorChance, ceilingChance);
	
	private static final AbstractCaveType plains = new CaveTypeDirtWater ("dirtWater", floorChance, ceilingChance);
	
	private static final AbstractCaveType podzol = new CaveTypePodzol("podzol", floorChance, ceilingChance);
	private static final AbstractCaveType mossy = new CaveTypeMossy("mossy", floorChance, ceilingChance);
	private static final AbstractCaveType mossyRocky = new CaveTypeMossyRocky("mossyRocky", floorChance, ceilingChance);
	
	private static final AbstractCaveType volcanic = new CaveTypeVolcanic ("volcanic", floorChance, ceilingChance+4);
	private static final AbstractCaveType sandyVolcanic = new CaveTypeSandyVolcanic("redSandyVolcanic", floorChance, ceilingChance+4);
	
	public static final CaveTypeHell nether = new CaveTypeHell ("nether", floorChance, ceilingChance);


	public static CaveProfile getCaveProfile(Biome biome){
		//System.out.println("Getting cave profile for " + biome.getBiomeName());
		return cavebiomemap.get(biome) != null ? cavebiomemap.get(biome) : getNewProfile(biome); 
	}


	public static CaveProfile getNewProfile(Biome biome){

		//System.out.println("No profile found, generating new profile for  " + biome.getBiomeName());

		ArrayList<AbstractDungeonType> dungeonShallow = new ArrayList<>(DungeonTypeRegister.defaultList());
		AbstractCaveType shallow = simple;
		
		if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MESA) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SAVANNA)){
			shallow = paintedDesert;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS)) {

			if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SNOWY)){
				shallow = iceRocky;
				dungeonShallow.addAll(DungeonTypeRegister.coldList());		
			}
			else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SAVANNA)){
				shallow = redSandyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.desertList());
			}
			else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SANDY)){
				shallow = sandyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.desertList());
			}
			else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.JUNGLE)){
				shallow = lushVolcanic;
				dungeonShallow.addAll(DungeonTypeRegister.lushList());
			}
			else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)){
				shallow = mossyRocky;
				dungeonShallow.addAll(DungeonTypeRegister.forestList());
			}
			else {
				shallow = rocky;
			}
			dungeonShallow.addAll(DungeonTypeRegister.mountainList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SNOWY)){
			shallow = ice;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.OCEAN)|| BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.RIVER)){
			shallow = wet;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SWAMP)){
			shallow = swamp;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SANDY)){
			shallow = sandy;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.JUNGLE)){
			shallow = lush;
			dungeonShallow.addAll(DungeonTypeRegister.lushList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MUSHROOM)){
			shallow = fungal;
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS)){
			shallow = plains;
			dungeonShallow.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.CONIFEROUS)){
			shallow =podzol;
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
			dungeonShallow.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)){
			if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.LUSH)){
				shallow =swamp;
				dungeonShallow.addAll(DungeonTypeRegister.forestList());
				dungeonShallow.addAll(DungeonTypeRegister.wetList());
			}
			else{
				shallow = mossy;
			}
			dungeonShallow.addAll(DungeonTypeRegister.forestList());
		}

		//MID
		ArrayList<AbstractDungeonType> dungeonMid = new ArrayList<>(DungeonTypeRegister.defaultList());
		AbstractCaveType mid = rocky;
		
		if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SNOWY)){
			mid = iceRocky;
			dungeonMid.addAll(DungeonTypeRegister.coldList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.OCEAN) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.RIVER)){
			mid = wet;
			dungeonMid.addAll(DungeonTypeRegister.wetList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SAVANNA) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MESA)){
			mid = redSandyRocky;
			dungeonMid.addAll(DungeonTypeRegister.desertList());	
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SANDY)){
			mid = sandyRocky;
			dungeonMid.addAll(DungeonTypeRegister.desertList());	
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.JUNGLE)){
			mid = lushVolcanic;
			dungeonMid.addAll(DungeonTypeRegister.volcanicList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)){
			mid = mossyRocky;
			dungeonMid.addAll(DungeonTypeRegister.forestList());
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.WET)){
			mid = wet;
			dungeonMid.addAll(DungeonTypeRegister.wetList());
		}

		//DEEP
		AbstractCaveType deep = volcanic;
		ArrayList<AbstractDungeonType> dungeonDeep = new ArrayList<>(DungeonTypeRegister.netherList());
		if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SNOWY)){
			deep = iceRocky;
			dungeonShallow.addAll(DungeonTypeRegister.coldList());	
		}
		else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SANDY) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SAVANNA)){
			deep = sandyVolcanic;
			dungeonShallow.addAll(DungeonTypeRegister.desertList());
		}

		if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.NETHER)){
			shallow = nether;
			mid = nether;
			deep = nether;
		}

		CaveProfile profile = new CaveProfile(deep, mid, shallow);
		profile.dungeonDeep = dungeonDeep;
		profile.dungeonMid = dungeonMid;
		profile.dungeonShallow = dungeonShallow;

		cavebiomemap.put(biome, profile);
		return profile;
	}


}
