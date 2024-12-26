package main;
import java.util.*;

public class main {

    // Pomaci za 4 smera (gore, dole, levo, desno)
    private static final int[][] MOVES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    // Euklidska heuristika
    public static double euclideanHeuristic(int[] start, int[] goal) {
        return Math.sqrt(Math.pow(start[0] - goal[0], 2) + Math.pow(start[1] - goal[1], 2));
    }

    // A* algoritam
    public static List<int[]> astar(int[] start, int[] goal, int[][] grid) {
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
                    path.add(new int[] { currentNode.x, currentNode.y });
                    currentNode = currentNode.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedList.add(currentNode.getKey());

            for (int[] move : MOVES) {
                int newX = current[0] + move[0];
                int newY = current[1] + move[1];

                if (isValid(newX, newY, grid) && !closedList.contains(getKey(newX, newY))) {
                    double gCost = currentNode.g + 1;
                    Node neighbor = allNodes.getOrDefault(getKey(newX, newY), new Node(newX, newY, Double.MAX_VALUE, euclideanHeuristic(new int[]{newX, newY}, goal), currentNode));

                    if (gCost < neighbor.g) {
                        neighbor.g = gCost;
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = currentNode;

                        openList.remove(neighbor); // Uklanja čvor ako je već u listi
                        openList.add(neighbor); // Ponovno dodavanje sa ažuriranim vrednostima
                        allNodes.put(neighbor.getKey(), neighbor);
                    }
                }
            }
        }

        return null; // Ako nema puta do cilja
    }

    private static boolean isValid(int x, int y, int[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 0;
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

    public static void main(String[] args) {
        // 0 označava slobodan prostor, 1 označava prepreku
        int[][] grid = new int[500][500]; // Manja mapa (100x100)

        // Dodavanje prepreka na mapu
        grid[50][50] = 1; // Prepreka na poziciji (50, 50)
        grid[51][51] = 1;
        grid[51][50] = 1;
        grid[51][52] = 1;
        // Početna i ciljna pozicija
        int[] start = {0, 0};
        int[] goal = {499, 499};

        // Pozivanje A* algoritma
        List<int[]> path = astar(start, goal, grid);

        if (path != null) {
            System.out.println("Put do cilja je:");
            for (int[] step : path) {
                System.out.println(Arrays.toString(step));
            }
        } else {
            System.out.println("Nema puta do cilja!");
        }
    }
}
