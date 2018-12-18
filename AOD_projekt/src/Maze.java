import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {

	private Game game;

	public static Cell[][] cells;

	private Cell currentCell;
	private Cell nextCell;

	private Cell goalCell;
	private Cell startCell;

	private Stack<Cell> stack;
	private Stack<Cell> path;
	private ArrayList<Cell> solutionPath;

	private ArrayList<Cell> neighbours;

	private Random random;

	private int mazeWidth = 20;
	private int mazeHeight = 20;

	private boolean goalIsFound;

	public Maze(Game game) {
		this.game = game;

		random = new Random();

		initializeCells();

		stack = new Stack<Cell>();
		path = new Stack<Cell>();
		solutionPath = new ArrayList<Cell>();

		goalCell = cells[cells.length-1][cells[0].length-1];

		startCell = cells[0][0];

		createMaze(startCell);
	}

	private void initializeCells() {
		cells = new Cell[mazeWidth][mazeHeight];

		for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[x].length; y++)
				cells[x][y] = new Cell(x, y);
	}

	private void createMaze(Cell currentCell) {

		if(isAllVisited())
			return;
		if(currentCell.equals(goalCell) && !goalIsFound)
			goalIsFound = true;

		this.currentCell = currentCell;
		currentCell.setVisited(true);
		neighbours = findNeighbours(currentCell);

		if(neighbours.size() == 0) {
			if(!goalIsFound)
				nextCell.setSolution(false);
			nextCell = stack.pop();
		}else {
			nextCell = selectRandomNeighbour(neighbours);
			if(!goalIsFound)
				nextCell.setSolution(true);

			stack.push(currentCell);
			removeWallBetween(currentCell, nextCell);
		}



		createMaze(nextCell);

	}

	private boolean isAllVisited() {
		for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[x].length; y++)
				if(cells[x][y].getIsVisited() == false)
					return false;
		return true;
	}


	private void removeWallBetween(Cell cc, Cell nc) {

		int ccX = cc.getX();
		int ccY = cc.getY();

		int ncX = nc.getX();
		int ncY = nc.getY();

		if(ccY - ncY == 1) {
			cc.walls[0] = 0;
			nc.walls[1] = 0;
		}

		if(ccY - ncY == -1) {
			cc.walls[1] = 0;
			nc.walls[0] = 0;
		}

		if(ccX - ncX == 1) {
			cc.walls[2] = 0;
			nc.walls[3] = 0;
		}

		if(ccX - ncX == -1) {
			cc.walls[3] = 0;
			nc.walls[2] = 0;
		}

	}

	private ArrayList<Cell> findNeighbours(Cell currentCell) {

		ArrayList<Cell> neighbours = new ArrayList<Cell>();

		if(!(currentCell.getY() == 0)) {
			Cell up = cells[currentCell.getX()][currentCell.getY() - 1];
			if(!up.getIsVisited())
				neighbours.add(up);
		}

		if(!(currentCell.getX() == 0)) {
			Cell west = cells[currentCell.getX() - 1][currentCell.getY()];
			if(!west.getIsVisited())
				neighbours.add(west);
		}

		if(!(currentCell.getY() == cells[0].length - 1)) {
			Cell down = cells[currentCell.getX()][currentCell.getY() + 1];
			if(!down.getIsVisited())
				neighbours.add(down);
		}

		if(!(currentCell.getX() == cells.length - 1)) {
			Cell east = cells[currentCell.getX() + 1][currentCell.getY()];
			if(!east.getIsVisited())
				neighbours.add(east);
		}

		return neighbours;

	}

	private Cell selectRandomNeighbour(ArrayList<Cell> neighbours) {

		Cell randomCell = neighbours.get(random.nextInt(neighbours.size()));

		return randomCell;

	}


	public void update() {

	}

	public void render(Graphics g) {

		//Draw walls
		for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[x].length; y++)
				cells[x][y].drawWalls(g);


		for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[x].length; y++) {
				if(cells[x][y].isSolution()) {
					g.setColor(Color.MAGENTA);
					cells[x][y].fillCell(g);
				}
			}


		//Neighbour test

		/*
		for(int i = 0; i < neighbours.size(); i++) {
			g.setColor(Color.black);
			neighbours.get(i).fillCell(g);
		}
		 */



		//Fill start
		g.setColor(Color.GREEN);
		startCell.fillCellGoal(g);

		//Fill goal
		g.setColor(Color.blue);
		goalCell.fillCellGoal(g);


	}

}
