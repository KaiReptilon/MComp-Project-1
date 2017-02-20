package PathPlanning;
import java.util.*;

/**
 * Main PathFinding module
 * 
 * @author Nathan Rose
 * Student number: 14820340
 * 
 */
public class FindPath extends AStarAlgorithm<FindPath.Node>
{
		private int[][] map;
		ArrayList<String> checked = new ArrayList<String>();
		int startX;
		int startY;
		static int finalgoalsY;
		static int finalgoalsX;
		static int goalsY;
		static int goalsX;
		/*Wraps X and Y coordinates into a node*/
		public static class Node{
			public int x;
			public int y;
			Node(int x, int y){
				this.x = x;
				this.y = y;
			}
		}
		public static String toString(Node node){
			return "(" + node.x + ", " + node.y + ") ";
		} 
		
		public FindPath(int[][] map){
			this.map = map;
		}
		
		/*Checks whether the node it is at is the goal then returns true or false*/
		protected boolean goal(Node node){
			return (node.x == goalsY) && (node.y == goalsX);
		}
		
		/*Gets estimation from the node to the goal*/
		protected Double heuristic(Node from, Node to){
				//Diagonal Distance Heuristic
				return new Double((Math.abs(from.x - to.x) + Math.abs(from.y - to.y)) + 
						(1 - 2 * 1) * Math.min(Math.abs(from.x - to.x), Math.abs(from.y - to.y)));
		}
		
		/*Base cost of moving to the node. Increases for every node passes through*/
		protected Double g(Node from, Node to){
			return 1.0;
		}

		/*Checks surrounding nodes for unoccupied spaces and puts them in a list*/
		protected List<Node> grabNodes(Node node){
				List<Node> toCheck = new LinkedList<Node>();
				int x = node.x;
				int y = node.y;	
				for(String s: checked){
					if(s.equals("(" + x + ", " + y + ") ")){
						return toCheck;
					}
				}
				/*Go through this to sort out in a order*/
				if(y < map.length - 1 && map[x][y+1] == 0){
						toCheck.add(new Node(x, y+1));
				}
				if(x < map.length - 1 && map[x+1][y] == 0){
						toCheck.add(new Node(x+1, y));
				}
				if(x < map.length - 1 && y < map.length - 1 ){
					if(map[x+1][y+1] == 0){
							toCheck.add(new Node(x+1, y+1));
					}
				}
				if(y > 0 && y < map.length - 1){
					if(y < map.length - 1 && map[x][y-1] == 0){
							toCheck.add(new Node(x, y-1));
					}
				}
					if(x < map.length - 1){
						if(y < map.length - 1 && map[x+1][y-1] == 0){
								toCheck.add(new Node(x+1, y-1));
					}
				}
				if(x > 0){
					if(y < map.length - 1 && map[x-1][y-1] == 0){
						toCheck.add(new Node(x-1, y-1));
					}
				}
				if(x > 0 && x < map.length - 1){
					if(x < map.length - 1 && map[x-1][y] == 0){
							toCheck.add(new Node(x-1, y));
					}
				}
				if(y < map.length - 1){
					if(x < map.length - 1 && map[x-1][y+1] == 0){
							toCheck.add(new Node(x-1, y+1));
					}
				}
				if(y > 0){
					if(x < map.length - 1 && map[x-1][x-1] == 0){
						toCheck.add(new Node(x-1, y-1));
					}
				}
				checked.add("(" + node.x + ", " + node.y + ") ");
				return toCheck;
		}
		
		/*Sets the goal as the location that is clicked on the map*/
		public static void goals (int goalX, int goalY){
			goalsY = goalX;
			goalsX = goalY;
		}
		
		public static void updatedGoal(){
			goalsY = finalgoalsY;
			goalsX = finalgoalsX;
		}
		
		/*Main section of the code, initiates the pathfinding*/
		public static void PathIt(int[][] mapXY, int startX, int startY){
			int[][] map = mapXY;
			if (goalsY > 0 && goalsX > 0){
				if(map[goalsX][goalsY] == 0){
					FindPath path = new FindPath(map);

					long begin = System.currentTimeMillis();
				
					List<Node> finalPath = path.calculate(new FindPath.Node(startX, startY));

					long end = System.currentTimeMillis();
				
					System.out.println("Time = " + (end - begin) + " ms" );
					System.out.println("Cost = " + path.getCost());
				
					if(finalPath == null){
							System.out.println("No path");
						
					}else{
							System.out.println("Path found");
							finalgoalsY = goalsY;
							finalgoalsX = goalsX;
							goalsY = 0;
							goalsX = 0;
							System.out.print("Path = ");
								for(Node n : finalPath)
									System.out.print(toString(n));
								System.out.println();
							}
				}else{
						System.out.println("Goal obstructed");
				}
			}
				
		}
}