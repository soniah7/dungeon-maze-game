package other;

import enemies.Hunter;
import items.Arrow;
import items.Bomb;
import items.InvincibilityPotion;
import items.Sword;
import items.Treasure;

public class Main {
	
	// Class to test the movement of the character class by receiving playerInput.
	// Also tests the implementation of the wall class - prevent character from passing through it.
	public static void main(String[] args) {
		Board board = new Board();
		Player p1 = new Player(board);
		Exit e1 = new Exit(board);
		Door d1 = new Door(board, 0);
		//d1.changeStatus(DoorStatus.Open);
		Pit pit1 = new Pit(board);
		Boulder bo1 = new Boulder(board);
		Switch sw1 = new Switch(board);
		Bomb bomb = new Bomb(board);
		Arrow arrow = new Arrow(board);
		Sword sword = new Sword(board);
		InvincibilityPotion IPotion = new InvincibilityPotion(board);
		Hunter hunter = new Hunter(board);
		Treasure treasure = new Treasure(board);
		
		board.placeEntity(p1, 1, 1);
		board.placeEntity(new Wall(board), 0, 0);
		board.placeEntity(new Wall(board), 0, 1);
		board.placeEntity(e1, 2, 4);
		board.placeEntity(d1, 4, 4);
		board.placeEntity(pit1, 1, 4);
		board.placeEntity(bo1, 1, 2);
		board.placeEntity(sw1, 3, 3);
		board.placeEntity(bomb, 3, 2);
		board.placeEntity(arrow, 5, 2);
		board.placeEntity(sword, 3, 0);
		board.placeEntity(IPotion, 6, 0);
		board.placeEntity(hunter, 7, 7);
		board.placeEntity(treasure, 6, 6);
		
		System.out.println(p1.getObjectiveString());
		board.printBoard();
		while (!p1.getKeyboardInput()) {
			System.out.println(p1.getObjectiveString());
			board.printBoard();
			// If the player has completed all objectives then show congratulations message
			if (p1.checkAllObjectivesCompleted()) {
				System.out.println("Congratulations you have completed this level!");
				break;
			}
			
			if (p1.checkIfAlive() == false) {
				System.out.println("Game over you have died");
				break;
			}
		}
		
		// System exit here so the program terminates and stops running in the background.
		System.exit(0);
	}
}