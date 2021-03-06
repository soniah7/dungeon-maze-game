package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import other.Board;
import other.Boulder;
import other.Door;
import other.Pit;
import other.Wall;
import player.Player;

public class BoulderTest {
	Board b1;
	Boulder bou1;
	Door d1;
	Player p1;
	Wall w1;
	Pit pit1;

	@Before
	public void setUp() throws Exception {
		b1 = new Board();
		bou1 = new Boulder(b1);
		d1 = new Door(b1, 0);
		p1 = new Player(b1);
		w1 = new Wall(b1);
		pit1 = new Pit(b1);
		
		b1.placeEntity(bou1, 2, 1);
	}
		
	@Test
	public void instantiateABoulderOnBoardWithCorrectCoordinates() {
		b1.placeEntity(bou1, 2, 1);
		assertEquals(true, b1.getEntity(2, 1) == bou1);
	}
	
	@Test
	public void otherEntitiesCannotExistOnTopOfTheBoulder() {
		b1.placeEntity(bou1, 2, 1);
		b1.placeEntity(d1, 2, 1);
		assertEquals(true, b1.getEntity(2, 1) == bou1);
	}
	
	@Test
	public void pushingBoulderIntoAdjacentSquare() {
		b1.placeEntity(bou1, 2, 1);
		
		b1.placeEntity(p1, 1, 1);
		
		// Player will move to the right to push the boulder to the right
		b1.placeEntity(p1, 2, 1);
		
		assertEquals(true, bou1.getXCoordinate() == 3 && bou1.getYCoordinate() == 1);
		assertEquals(true, p1.getXCoordinate() == 2 && p1.getYCoordinate() == 1);
	}
	
	@Test
	public void tryingToPushBoulderPastEdgeOfBoard() {
		// The boulder should not move because it is not allowed to move out of the board.
		// As a result the player should also not move
		b1.placeEntity(bou1, 0, 0);
		
		b1.placeEntity(p1, 0, 1);
		
		// Player will move up to try move the boulder up into its next adjacent square
		b1.placeEntity(p1, 0, 0);
		
		assertEquals(true, bou1.getXCoordinate() == 0 && bou1.getYCoordinate() == 0);
		assertEquals(true, p1.getXCoordinate() == 0 && p1.getYCoordinate() == 1);
	}
	
	@Test
	public void tryingToPushBoulderPastAWall() {
		// The boulder should not move because it is not allowed to pass through a wall
		// As a result the player should also not move
		b1.placeEntity(bou1, 1, 0);
		
		b1.placeEntity(p1, 2, 0);
		b1.placeEntity(w1, 0, 0);
		
		// Player will move left to try move the boulder left into its next adjacent square
		b1.placeEntity(p1, 1, 0);
		
		assertEquals(true, bou1.getXCoordinate() == 1 && bou1.getYCoordinate() == 0);
		assertEquals(true, p1.getXCoordinate() == 2 && p1.getYCoordinate() == 0);
	}
	
	@Test
	public void pushingBoulderIntoAPit() {
		// Pushing a boulder into a pit will cause the boulder to disappear from the map.
		b1.placeEntity(bou1, 1, 0);
		b1.placeEntity(p1, 2, 0);
		b1.placeEntity(pit1, 0, 0);
		
		// Player will move left causing the boulder to move into a pit and disappear
		b1.placeEntity(p1, 1, 0);
		
		assertEquals(true, b1.getEntity(0, 0) == pit1);
		assertEquals(true, p1.getXCoordinate() == 1 && p1.getYCoordinate() == 0);
	}
}
