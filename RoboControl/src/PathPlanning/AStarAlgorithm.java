package PathPlanning;
import java.util.*;

/**
 * 
 * @author Nathan Rose
 * Student number: 14820340
 * 
 */
public abstract class AStarAlgorithm<Node>
{
		@SuppressWarnings("rawtypes")
		private class Path implements Comparable{
			public Node point;
			public Double f;
			public Double g;
			public Path parent;
			
			public Path(){
				parent = null;
				point = null;
				g = 0.0;
				f = 0.0;
			}
			
			public Path(Path p){
				parent = p;
				g = p.g;
				f = p.f;
			}
			
			public Node getPoint(){
				return point;
			}
			
			public void setPoint(Node point){
				this.point = point;
			}
		
			@SuppressWarnings("unchecked")
			public int compareTo(Object o){
				Path p = (Path)o;
				if(f - p.f < 0){
					return -1;
				}
				else if (f - p.f == 0){
					return 0;
				}
				else if (f - p.f > 0){
					return 1;
				}
				return 0;
			}
		}
		
		private Double lastCost = 0.0;
		private PriorityQueue<Path> paths = new PriorityQueue<Path>();
		private HashMap<Node, Double> mindists = new HashMap<Node, Double>();
		
		protected abstract boolean goal(Node node);
		
		protected abstract Double g(Node from, Node to);
		
		protected abstract Double heuristic(Node from, Node to);
		
		protected abstract List<Node> grabNodes(Node node);
		
		/*Calculates the cost to get to the node*/
		protected Double f(Path p, Node from, Node to){
			Double g;
		if(p.parent != null){
			g =  g(from, to) + p.parent.g;
		}else{
			g = g(from, to) + 0.0;
		}
			Double h = heuristic(from, to);

			p.g = g;
			p.f = g + h;

			return p.f;
		}
		
		private void expandPath(Path path){
			Node p = path.getPoint();
			Double min = mindists.get(p);

			/*
			 * If a better path passing for this point already exists then
			 * don't expand it.
			 */
			if(min == null || min > path.f)
				mindists.put(path.getPoint(), path.f);
			else
				return;

			List<Node> successes = grabNodes(p);

			for(Node to : successes){
					Path newPath = new Path(path);
					newPath.setPoint(to);
					f(newPath, path.getPoint(), to);
					paths.offer(newPath);
			}
		}
		
		public Double getCost(){
			return lastCost;
		}
		
		public List<Node> calculate(Node start){
			try{
				Path root = new Path();

				root.setPoint(start);
				/*Do I need this? This is if the start node has a cost*/
				f(root, start, start);

				expandPath(root);
				
				for(;;){
						Path p = paths.poll();
						if(p == null){
							lastCost = Double.MAX_VALUE;
							return null;
						}
						Node lastPoint = p.getPoint();
						lastCost = p.g;
						/*if at the goal it will add each node it went through to a list of the path to take*/
						if(goal(lastPoint)){
								LinkedList<Node> finalPath = new LinkedList<Node>();
								for(Path i = p; i != null; i = i.parent){
										finalPath.addFirst(i.getPoint());
								}
								return finalPath;
						}
						expandPath(p);
				}
		}
		catch(Exception e){
				e.printStackTrace();
		}
		return null;
				
}
		
}