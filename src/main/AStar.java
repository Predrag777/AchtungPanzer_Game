package main;

import java.util.*;

public class AStar {

    private static final int[][] MOVES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static double euclideanHeuristic(int[] start, int[] goal) {
        return Math.sqrt(Math.pow(start[0] - goal[0], 2) + Math.pow(start[1] - goal[1], 2));
    }

    public static List<int[]> astar(int[] start, int[] goal, int[][] grid, int tankWidth, int tankHeight) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        HashMap<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start[0], start[1], 0, euclideanHeuristic(start, goal), null);
        openList.add(startNode);
        allNodes.put(startNode.getKey(), startNode);

        Set<String> closedList = new HashSet<>();

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            int[] current = currentNode.getPosition();

            if (current[0] == goal[0] && current[1] == goal[1]) {
                List<int[]> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(new int[]{currentNode.x, currentNode.y});
                    currentNode = currentNode.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedList.add(currentNode.getKey());

            for (int[] move : MOVES) {
                int newX = current[0] + move[0];
                int newY = current[1] + move[1];

                if (isValid(newX, newY, grid, tankWidth, tankHeight) && !closedList.contains(getKey(newX, newY))) {
                    double gCost = currentNode.g + 1;
                    Node neighbor = allNodes.getOrDefault(getKey(newX, newY), new Node(newX, newY, Double.MAX_VALUE, euclideanHeuristic(new int[]{newX, newY}, goal), currentNode));

                    if (gCost < neighbor.g) {
                        neighbor.g = gCost;
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = currentNode;

                        openList.remove(neighbor);
                        openList.add(neighbor);
                        allNodes.put(neighbor.getKey(), neighbor);
                    }
                }
            }
        }

        return null;
    }

    private static boolean isValid(int x, int y, int[][] grid, int tankWidth, int tankHeight) {
    	if (x < 0 || y < 0 || y - tankHeight + 1 < 0 || x + tankWidth > grid[0].length || 
    		    isTankOverlapping(grid, x, y, tankWidth, tankHeight)) {
    		    //System.out.println("Invalid tank position: Out of bounds or overlaps with an obstacle");
    		    return false;
    		}

        return true;
    }
    

    public static boolean isTankOverlapping(int[][] grid, int x, int y, int tankWidth, int tankHeight) {
        return grid[y][x] != 0 || 
               grid[y][x + tankWidth - 1] != 0 || 
               grid[y - tankHeight + 1][x] != 0 ||  
               grid[y - tankHeight + 1][x + tankWidth - 1] != 0;  
    }

    
    private static String getKey(int x, int y) {
        return x + "," + y;
    }

    static class Node {
        int x, y;
        double g, h, f;
        Node parent;

        Node(int x, int y, double g, double h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.parent = parent;
        }

        int[] getPosition() {
            return new int[]{x, y};
        }

        String getKey() {
            return x + "," + y;
        }
    }
}
