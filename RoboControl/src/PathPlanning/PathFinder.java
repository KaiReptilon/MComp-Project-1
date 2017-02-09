package PathPlanning;

import java.util.*;

//import main.PathFinder.Node;

public class PathFinder {
	//private int [][] 
	 Double heuristic(){
		// Uses Euclidean distance heuristics
		//return new Double(Math.abs(mapXY[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
		//return new Double(Math.sqrt(Math.abs(mapXY[0].length - 1 - this.x) + Math.abs(mapXY[0].length - 1 - this.y)));
		 return new Double(Math.abs(mapIt[0].length - 1 - goal.x) + Math.abs(mapIt.length - 1 - goal.y)) + 
				 (1 - 2 * 1) * Math.min(Math.abs(mapIt[0].length - 1 - goal.x), Math.abs(mapIt.length - 1 - goal.y));
	}	
		
	private static int [][] mapIt;
		{
		int x = 0;
		int y = 0;

		if(y < mapIt.length - 1 && mapIt[y+1][x] == 0)
			//add node (x, y+1)

		if(x < mapIt[0].length - 1 && mapIt[y][x+1] == 0)
			//add node (x+1, y)
	
		if(x < mapIt.length - 1 && mapIt[y+1][x+1] == 0)
			//add node (x+, y+1)
		
		if(y > 0 && y < mapIt.length){
			if(y < mapIt.length - 1 && mapIt[y-1][x] == 0)
						//add node (x, y-1)
			if(x < mapIt[0].length - 1)
				if(y < mapIt.length - 1 && mapIt[y-1][x+1] == 0)
						//add node (x+1, y-1)
					if(x > 0)
						if(y < mapIt.length - 1 && mapIt[y-1][x-1] == 0)
							//add node (x-1, y-1)
		}
		if(x > 0 && x < mapIt[0].length){
			if(x < mapIt[0].length - 1 && mapIt[y][x-1] == 0)
				//add node (x-1, y)
		if(y < mapIt.length - 1)
			if(x < mapIt[0].length - 1 && mapIt[y+1][x-1] == 0)
				//add node (x-1, y+1)
		if(y > 0)
			if(x < mapIt[0].length - 1 && mapIt[y-1][x-1] == 0){}
				//add node (x-1, y-1)
		}
	}
		
	public static void PathIt (int[][] mapXY){
		/*int [][] mapIt = new int [][]{
			{1, 1, 1, 1, 1, 1, 1, 1, 0},
			{1, 1, 1, 0, 0, 0, 0, 1, 1},
			{0, 0, 1, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 1, 1, 0, 0, 1, 0, 0, 1},
			{1, 1, 1, 1, 0, 0, 1, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 0, 1, 0, 0, 1},
			{1, 1, 1, 1, 0, 1, 0, 0, 1},
			{1, 1, 1, 1, 0, 1, 1, 0, 1},
		};*/
		mapIt = mapXY;
		int c = 0;
		//mapIt = distanceDataProcessing.getMapXY();
		for(int i = 0; i < mapIt.length; i++){
			for(int j = 0; j < mapIt[0].length; j++){
					c++;
				}
			}
			System.out.print(c);
		}
}