package uencom.xgame.jsonconverters;

public class BoardJsonParameterConverter {
 private String game_id;
 
 public BoardJsonParameterConverter(String ID)
 {
	 setGame_id(ID);
 }

public String getGame_id() {
	return game_id;
}

public void setGame_id(String game_id) {
	this.game_id = game_id;
}
}
