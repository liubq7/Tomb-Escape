package Maze;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Use Recursive Backtracker algorithm to generate maze.
 * Also generate properties randomly.
 */
public class MazeCreator {
    private int col;  // width, needs to be odd number
    private int row;  // height, needs to be odd number
    public Cell[][] maze;
    private LinkedList<Cell> recurseList;
    private LinkedList<Cell> availableList;  // road cells which haven't been marked

    private int shovelNum;  // the number of cell which has shovel
    private int bloodBagNum;  // the number of cell which has blood bag
    private int cloakNum;  // the number of cell which has invisible cloak
    private int trapNum;  // the number of cell which has trap

    public ArrayList<Cell> shovelList;
    public ArrayList<Cell> bloodBagList;
    public ArrayList<Cell> cloakList;
    public ArrayList<Cell> trapList;


    public MazeCreator(int x, int y) {
        col = x;
        row = y;
        maze = new Cell[x][y];
        recurseList = new LinkedList<>();
        availableList = new LinkedList<>();

        shovelNum = 2;
        bloodBagNum = 3;
        cloakNum = 1;
        trapNum = 3;

    }

    /* Generate a base map with 0, 1 interval. */
    private void initMaze() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }
        for (int i = 1; i < col; i += 2) {
            for (int j = 1; j < row; j += 2) {
                maze[i][j].status = 1;
                availableList.add(maze[i][j]);
            }
        }
    }

    /* Randomly choose a 1 as the starting point. */
    private Cell randomStart() {
        int i = 1 + 2 * (int) (Math.random() * (col-1) / 2);
        int j = 1 + 2 * (int) (Math.random() * (row-1) / 2);
        recurseList.add(maze[i][j]);
        availableList.remove(maze[i][j]);
        return maze[i][j];
    }

    /* Determine whether a coordinate is in this maze. */
    private boolean isContain(int x, int y) {
        if (x >= 0 && x < col && y >= 0 && y < row) {
            return true;
        } else {
            return false;
        }
    }

    /* Get all 1s that have not been marked around the start point. */
    private ArrayList<Cell> getAround(Cell start) {
        ArrayList<Cell> around = new ArrayList<>();
        if (isContain(start.x, start.y-2) && availableList.contains(maze[start.x][start.y-2])) {
            around.add(maze[start.x][start.y-2]);
        }
        if (isContain(start.x, start.y+2) && availableList.contains(maze[start.x][start.y+2])) {
            around.add(maze[start.x][start.y+2]);
        }
        if (isContain(start.x-2, start.y) && availableList.contains(maze[start.x-2][start.y])) {
            around.add(maze[start.x-2][start.y]);
        }
        if (isContain(start.x+2, start.y) && availableList.contains(maze[start.x+2][start.y])) {
            around.add(maze[start.x+2][start.y]);
        }
        return around;
    }

    /**
     * Randomly get an unmarked 1 around the start point
     * @param start start point
     * @return a random unmarked 1 around the start point, if no, return null
     */
    private Cell getEnd(Cell start) {
        ArrayList<Cell> around = getAround(start);
        int n = around.size();
        if (n != 0) {
            int random = (int)(Math.random() * n);
            return around.get(random);
        } else {
            return null;
        }
    }

    /* Connect road between start and end, and add end to recurseList to be the next start. */
    private void generatePath(Cell start, Cell end) {
        int midX = (start.x + end.x) / 2;
        int midY = (start.y + end.y) / 2;
        maze[midX][midY].status = 1;
        recurseList.add(end);
        availableList.remove(end);
    }

    /* Generate maze and properties randomly. */
    public void generateMaze() {
        initMaze();
        Cell start = randomStart();
        while (!availableList.isEmpty()) {
            if (getEnd(start) != null) {
                Cell end = getEnd(start);
                generatePath(start, end);
                start = recurseList.getLast();
            } else {
                recurseList.removeLast();
                start = recurseList.getLast();
            }
        }


        shovelList = new ArrayList<>();
        while (shovelList.size() < shovelNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 1;
                shovelList.add(maze[i][j]);
            }
        }

        bloodBagList = new ArrayList<>();
        while (bloodBagList.size() < bloodBagNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 2;
                bloodBagList.add(maze[i][j]);
            }
        }

        cloakList = new ArrayList<>();
        while (cloakList.size() < cloakNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 3;
                cloakList.add(maze[i][j]);
            }
        }


        trapList = new ArrayList<>();
        while (trapList.size() < trapNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 4;
                trapList.add(maze[i][j]);
            }
        }
    }



    public static void main(String args[]) {
        int x = 21;
        int y = 13;
        MazeCreator mazeCreator = new MazeCreator(x, y);
        mazeCreator.generateMaze();
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                System.out.print(mazeCreator.maze[i][j].status + " ");
            }
            System.out.println();
        }
    }
}
