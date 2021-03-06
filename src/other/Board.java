package other;

import java.util.ArrayList;

import enemies.Enemy;
import player.Player;
import player.PlayerObserver;

public class Board {
	private Floor[][] map;
	private ArrayList<Entity> entities;
	private ObjectiveComponent objectivesOnBoard;	// A board will have objectives that the player needs to complete
	
	public Board() {
		// Default size if no given width/height
		map = new Floor[10][10];
		entities = new ArrayList<>();
		this.objectivesOnBoard = new ObjectiveComponent();
		initFloor(10, 10);
	}
	
	// If width and height are specified
	public Board(int width, int height) {
		this();
		map = new Floor[height][width];
		initFloor(width, height);
	}
	
	// For each grid on the map we need to instantiate a floor object
 	public void initFloor(int width, int height) {
 		for (int i = 0; i < height; i++) {
 			for (int j = 0; j < width; j++) {
 				map[i][j] = new Floor();
 			}
 		}
 	}
 	
 	public Floor getFloor(int x, int y) {
 		return map[x][y];
 	}
 	
 	public int getWidth() {
 		return map[0].length;
 	}
 	
 	public int getHeight() {
 		return map.length;
 	}
 	/**
 	 * If an entity is added to the board that has an objective associated with it, then
 	 * we need to update the ObjectiveComponent on the board so it stores this.
 	 * @param e
 	 */
 	public void addNewObjective(Entity e) {
 		if (e.getAssociatedPointType() != null) {
 			// Use observer pattern here, subscribe objectivesOnBoard to the point
 			objectivesOnBoard.addPoint(e.getAssociatedPointType());
 			e.getAssociatedPointType().addObserver(objectivesOnBoard);
 		}
 	}
 	
 	/**
 	 * This method returns all the enemy entities associated with this board.
 	 * @return an arraylist of the enemy entities
 	 */
 	public ArrayList<Enemy> getEnemyObjects() {
 		ArrayList<Enemy> enemies = new ArrayList<>();
 		for (Entity entity : entities) {
 			if (entity instanceof Enemy) {
 				enemies.add((Enemy) entity);
 			}
 		}
 		return enemies;
 	}
 	
	/**
	 * This method returns player entity associated with this board. It searches
	 * based on the entities instance. 
	 * @return player entity or null if nothing was found.
	 */
	public Player getPlayerObject() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Player) {
				return (Player) entities.get(i);
			}
		}
		return null;
	}
	
	/**
	 * This method returns all the objectives that has to be completed on this board 
	 * in order to complete the level.
	 * @return an objectiveComponent of all the objectives
	 */
	public ObjectiveComponent getObjectivesOnThisBoard() {
		return this.objectivesOnBoard;
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
		// If the entity is an enemy we need to 'subscribe' them to the player object
		if (entity instanceof Enemy) {
			getPlayerObject().addObserver((PlayerObserver)entity); 
		}
		this.addNewObjective(entity);
	}
	
	/**
	 * Method that returns the object contained on the floor grid
	 * @param x: The x-coordinate that the user wants to look at
	 * @param y: The y-coordinate that the user wants to look at
	 * @return the object that is located at the given coordinates
	 */
	public Entity getEntity(int x, int y) {
		// Can throw an error if the given x or y coordinate is out of bounds.
		if ((x >= 0 && x < map.length) && (y >= 0 && y < map.length)) {
			return map[x][y].getFrontEntity();
		}
		return null;
	}
	
	/**
	 * Method that places the object at the given coordinates on the board
	 * @pre (x => 0 && x < map.length) && ( y > 0 && y <= map.length)
	 * @post true/false
	 * 
	 * @param entity: The entity we want to place on the board
	 * @param x: The x-coordinate that the entity will be placed at
	 * @param y: The y-coordinate that the entity will be placed at
	 * @return true/false if the entity was successfully placed at the given argument
	 */
	public boolean placeEntity(Entity entity, int x, int y) {
		// Board checks that the new coordinates are within the board. Then it checks if the passed in entity
		// is allowed to pass over the objects that might occupy the new coordinates.
		if ((x >= 0 && x < map.length) && (y >= 0 && y < map.length)) {
			if (entities.contains(entity) == false) addEntity(entity); // If board does not know about this entity then we add it to the boards entities arraylist
			
			if (map[x][y].addEntity(entity)) {
				entity.setCoordinates(x, y);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method that removes the entity from the map & game as a result of the entity dying,
	 * or from the player removing it during the design mode
	 * @param e
	 */
	public void removeEntity(Entity e) {
		this.entities.remove(e);
		removeFromMap(e, e.getXCoordinate(), e.getYCoordinate());
		// If the entity is being removed during the design mode then that means
		// the point has not yet been obtained. In which case we need to
		// remove it from the objectives that the board has.
		if (e.getAssociatedPointType() != null && !e.getAssociatedPointType().checkPointObtained()) {
			this.objectivesOnBoard.removePoint(e.getAssociatedPointType());
		}
		
		// If the entity is an enemy that has died/been removed we need to remove it
		// from the players observers list so they don't randomly pop up after dying
		if (e instanceof Enemy) {
			this.getPlayerObject().removeObserver((PlayerObserver) e);
		}
	}
	
	/**
	 * Method that removes the object at the given coordinates on the board as a result of the entity moving.
	 * @param x
	 * @param y
	 */
	public void removeFromMap(Entity e, int x, int y) {
		map[x][y].removeEntity(e);
	}
	
	/**
	 * Method that prints out the board and all the entities on it
	 */
	public void printBoard() {
		//updateBoard();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[j][i].getFrontEntity() == null) {
					System.out.print(" . ");
				}
				else {
					System.out.print(map[j][i].getFrontEntity().getIcon());
				}
			}
			System.out.print("\n");
		}
		
	}
}
