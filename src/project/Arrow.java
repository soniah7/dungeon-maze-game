package project;

public class Arrow extends Item{

	public Arrow(Board board) {
		super(board);
		this.name = "arrow";
		this.icon = " ↖ ";
	}
	
	
	public void Fly(Direction d) {
		// Arrow will fly until it hits objects that have a higher zorder than itself
		switch (d) {
			case Up:
				while(this.move.moveUp(this, this.board)) {
					this.icon = " ↟ ";
					board.printBoard();
				}
				break;
			case Down:
				while (this.move.moveDown(this, this.board)) {
					this.icon = " ↡ ";
					board.printBoard();
				}
				break;
			case Right:
				while (this.move.moveRight(this, this.board)) {
					this.icon = " ↠ ";
					board.printBoard();
				}
				break;
			case Left:
				while (this.move.moveLeft(this, this.board)) {
					this.icon = " ↞ ";
					board.printBoard();
				}
				break;
		}
		board.removeEntity(this);
	}


	@Override
	public void useItem(Player player) {
		board.addEntity(this);	// Add the arrow to the board again because it will appear on the map
		this.setCoordinates(player.getXCoordinate(), player.getYCoordinate());	// Update the coordinates so the arrow knows where it will 'fly'
		
		// Now we ask the player which direction they would like to shoot the arrow in to determine the direction	
		System.out.println("Which direction would you like to launch the arrow?");
		this.Fly(getPlayerInputForDirection());	
	}
}
