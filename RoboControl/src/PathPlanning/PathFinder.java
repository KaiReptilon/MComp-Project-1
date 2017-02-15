package PathPlanning;
import java.util.*;

public class PathFinder
{
		private int[][] map;
		static int c = 0;
		static int startX;
		int startY;
		static int goalsX;
		static int goalsY;
		
/*		protected Double h(Node from, Node to){
				// Use the Manhattan distance heuristic
				return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
				//return new Double(1 * Math.sqrt(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y)));
				//return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
				//return new Double((Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y)) + 
						//(1 - 2 * 1) * Math.min(Math.abs(map[0].length - 1 - to.x), Math.abs(map.length - 1 - to.y)));
		}

		protected List<Node> generateSuccessors(Node node){
				List<Node> ret = new LinkedList<Node>();
				int x = node.x;
				int y = node.y;
				if(y < map.length - 1 && map[y+1][x] == 0)
						ret.add(new Node(x, y+1));

				if(x < map[0].length - 1 && map[y][x+1] == 0)
						ret.add(new Node(x+1, y));
				if(x < map[0].length - 1 && y < map.length - 1 )
					if(map[y+1][x+1] == 0)
							ret.add(new Node(x+1, y+1));
				
				if(y > 0 && y < map.length){
					if(y < map.length - 1 && map[y-1][x] == 0)
							ret.add(new Node(x, y-1));
					if(x < map[0].length - 1)
						if(y < map.length - 1 && map[y-1][x+1] == 0)
								ret.add(new Node (x+1, y-1));
				if(x > 0)
					if(y < map.length - 1 && map[y-1][x-1] == 0)
						ret.add(new Node (x-1, y-1));
				}
				if(x > 0 && x < map[0].length){
					if(x < map[0].length - 1 && map[y][x-1] == 0)
							ret.add(new Node(x-1, y));
				if(y < map.length - 1)
					if(x < map[0].length - 1 && map[y+1][x-1] == 0)
							ret.add(new Node(x-1, y+1));
				if(y > 0)
					if(x < map[0].length - 1 && map[y-1][x-1] == 0)
						ret.add(new Node(x-1, y-1));
				}
				return ret;
		}*/

		public static void PathIt (int[][] mapXY, int startX, int startY, int goalX, int goalY){
				int[][] map = mapXY;
				goalsX = goalX;
				goalsY = goalY;
				System.out.println("Start" + startX);
				System.out.println("Start" + startY);

		}

}
