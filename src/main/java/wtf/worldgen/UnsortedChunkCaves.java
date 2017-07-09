package wtf.worldgen;

import net.minecraft.util.math.BlockPos;
import wtf.Core;
import wtf.utilities.wrappers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UnsortedChunkCaves {

	private HashMap<XZ, ArrayList<CavePosition>> unsortedCavePos = new HashMap<>();
	private ArrayList<CaveListWrapper> sortedCaveLists = new ArrayList<>();

	private final ChunkCoords coords;

	public UnsortedChunkCaves(ChunkCoords coords){
		this.coords = coords;
	}
	
	public void addWallPos(BlockPos wall, AdjPos adj){
		XZ xz = new XZ(adj.getX(), adj.getZ());
		if (sortedCaveLists.stream().anyMatch(cave -> cave.contains(xz))) return;

        try {
            unsortedCavePos.get(xz).stream().filter(pos -> pos.floor < adj.getY() && pos.ceiling > adj.getY()).forEach(pos -> {
                pos.wall.add(adj);
                pos.adj.add(adj);
            });
        } catch (NullPointerException e) {
            Core.coreLog.fatal("While trying to add a wall pos on a previous column, the cave list was null");
        }

		/*

		try {
            for (CavePosition pos : list) {
                if (pos.floor < adj.getY() && pos.ceiling > adj.getY()) {
                    //System.out.println("cave pos found");
                    pos.wall.add(wall);
                    pos.adj.add(adj);
					return;
                }
            }
        }
		catch (Exception e){
			Core.coreLog.fatal("While trying to add a wall pos on a previous column, the cave list was null");
		}*/

	}



	public void add(CavePosition pos){

		XZ xz = pos.xz();
		if (xz == null) return;
		//System.out.println("Adding CavePos at " + pos.x + " " + pos.z);
		
		if (unsortedCavePos.containsKey(xz)){
			unsortedCavePos.get(xz).add(pos);
		}
		else {
			ArrayList<CavePosition> list = new ArrayList<>();
			list.add(pos);
			unsortedCavePos.put(xz, list);
		}

	}

	public void getSortedCaves(){
		//start at the first position, with an empty sorted-cave list
		//get any adjacent to that position, check that the Y value is reasonably close, and remove them from the unsorted list, and add them to the sorted
		//repeat that, until there are no more adjacent

		unsortedCavePos.forEach((key, value) -> value.forEach(cavepos -> {
			CaveListWrapper newcavelist = new CaveListWrapper(cavepos, coords);
			addAllAdj(newcavelist, cavepos, key);
			sortedCaveLists.add(newcavelist);
		}));

		sortedCaveLists.forEach(CaveListWrapper::setCaveArrayList);

		/*

		Iterator<Entry<XZ, ArrayList<CavePosition>>> iterator = unsortedCavePos.entrySet().iterator();

		//I need to add a check to verify that during addalladj I'm not rechecking columns- if I just use the same method for positions with the hash to store, that should actually work
		//If I rewrite cavepos to give a hashcode of hash- I can't put it in a hashset as a master- am I doing that at all?

		while (iterator.hasNext()){
			Entry<XZ, ArrayList<CavePosition>> entry = iterator.next();
			XZ xz = entry.getKey();
			ArrayList<CavePosition> list = entry.getValue();

			Iterator<CavePosition> listerator = list.iterator();

			while (listerator.hasNext()){
				CavePosition cavepos = listerator.next();
				CaveListWrapper newcavelist = new CaveListWrapper(cavepos, coords);
				listerator.remove();
				addAllAdj(newcavelist, cavepos, xz);
				sortedCaveLists.add(newcavelist);

			}


		}

		for (CaveListWrapper wrapper : sortedCaveLists){
			wrapper.setCaveArrayList();
		}*/
	}

	private void addAllAdj(CaveListWrapper newcavelist, CavePosition oldpos, XZ oldxz){

		XZ[] set = oldxz.getAdj();
		
		for (XZ newxz: set){

			//if there's not already a position in the caveset, but there is a position in the master set
			if (!newcavelist.contains(newxz) && unsortedCavePos.containsKey(newxz)){

                CavePosition usepos = unsortedCavePos.get(newxz).stream().filter(newpos -> Math.abs(oldpos.floor - newpos.floor) < 3 && Math.abs(oldpos.ceiling - newpos.ceiling) < 4).findFirst().orElse(null);

                //if I've gone from air to stone, then the cave position for the air, by definition, alread exists
                //so, I can just get the list at that pos, based on it's x-z coordinates, and add based on that

                if (usepos != null && !usepos.adj.isEmpty()){
                    newcavelist.addPos(usepos); //add the new position
                    addAllAdj(newcavelist, usepos, newxz);
                }

                /*
				CavePosition usepos2 = null;

				Iterator<CavePosition> iterator = unsortedCavePos.get(newxz).iterator();
				while (iterator.hasNext()){
					CavePosition newpos = iterator.next();
					int floordiff = Math.abs(oldpos.floor - newpos.floor);
					int ceildiff = Math.abs(oldpos.ceiling - newpos.ceiling);
					if (floordiff < 3 && ceildiff < 4){
                        usepos2=newpos;
						iterator.remove();
						break; //not sure whether performance is going to be afffected- I shouldn't need to iterate through the rest of the list
					}
				}

                Core.coreLog.info("pos1: " + usepos1.adj + "\n pos2: " + usepos2.adj);*/


			}
		}

	}

	public void printReport(){
		System.out.println("Caves per chunk " + sortedCaveLists.size());
		for (CaveListWrapper list : sortedCaveLists){
			if (list.size() > 10){
			System.out.println("New Cave");
			System.out.println(list.size());
			list.printMetrics();
			}
		}
	}

	public int size(){
		return this.sortedCaveLists.size();
	}
	
	public CaveListWrapper getRandomCave(Random random){
		return this.sortedCaveLists.get(random.nextInt(sortedCaveLists.size()));
	}
	
	public ArrayList<CaveListWrapper> getCaves(){
		return this.sortedCaveLists;
	}
}
