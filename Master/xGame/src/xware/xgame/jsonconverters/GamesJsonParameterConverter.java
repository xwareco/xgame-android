package xware.xgame.jsonconverters;

public class GamesJsonParameterConverter {
	private String category_id;
	private String limit;
	private String my_last_game;
	public GamesJsonParameterConverter(String ID , String lim , String Last)
	{
		setCategory_id(ID);
		setLimit(lim);
		setMy_last_game(Last);
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getMy_last_game() {
		return my_last_game;
	}
	public void setMy_last_game(String my_last_game) {
		this.my_last_game = my_last_game;
	}

}
