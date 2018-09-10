package project;

public class Door extends Entity{

	private DoorStatus status;
	private int doorNum;
	
	public Door(int x, int y, Board board) {
		super(x, y, board);
		status = DoorStatus.Closed;
	}
	
	// Method removed as overlappingEffect method is used instead. 
	/*@Override
	public boolean affectPlayer(Player player) {
		if (status.equals(DoorStatus.Open)) {
			return false;
		}
		return true;
	}*/
	
	/**
	 * Method checks if the entity that is passing over the door is allowed to pass through it.
	 * The only entities allowed to pass through the door are players, and this is only allowed
	 * if the door is opened as well.
	 * @param entity: the entity that is trying to pass over the door
	 * @return true/false if the entity is allowed to pass over the door
	 */
	@Override
	public boolean overlappingEffect(Entity entity) {
		if (entity instanceof Player && getDoorStatus() == DoorStatus.Open) {
			return true;
		}
		return false;
	}
	
	public void changeStatus(DoorStatus s) {
		status = s;
	}
	
	public DoorStatus getDoorStatus() {
		return this.status;
	}
	
	public int getDoorNum() {
		return doorNum;
	}
	
}
