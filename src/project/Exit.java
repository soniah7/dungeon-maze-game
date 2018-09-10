package project;

/**
 * If the player goes through this exit class then the puzzle is complete.
 * @author court
 *
 */
public class Exit extends Entity{

	public Exit(int x, int y, Board board) {
		super(x, y, board);
		// TODO Auto-generated constructor stub
	}

	public void exitFound(Player player) {
		player.tickObjective(this);
	}
	
	@Override
	public boolean overlappingEffect(Entity entity) {
		// If the overlapping entity is a player
		if (entity instanceof Player) {
			exitFound((Player)entity);
			return true;
		}
		return false;
	}
}
