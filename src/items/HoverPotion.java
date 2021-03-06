package items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import other.Board;
import other.Buff;
import player.Player;

public class HoverPotion extends Item {

	public HoverPotion(Board board) {
		super(board);
		this.name = "Hover Potion";
		this.entityIcon = new ImageView(new Image("icons/hover_potion.png"));
		this.description = "A potion that makes your player hover with no time limit";
	}

	@Override
	public String useItem(Player player) {
		player.addBuff(Buff.Hover);
		return "You drank the hover potion";
	}

}
