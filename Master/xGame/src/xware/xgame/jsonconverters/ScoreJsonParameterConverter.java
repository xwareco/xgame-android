package xware.xgame.jsonconverters;

public class ScoreJsonParameterConverter {
private String user_id;
private String game_id;
private String score;

public ScoreJsonParameterConverter(String uId , String gId , String score)
{
	setUser_id(uId);
	setGame_id(gId);
	setScore(score);
}

public String getUser_id() {
	return user_id;
}

public void setUser_id(String user_id) {
	this.user_id = user_id;
}

public String getGame_id() {
	return game_id;
}

public void setGame_id(String game_id) {
	this.game_id = game_id;
}

public String getScore() {
	return score;
}

public void setScore(String score) {
	this.score = score;
}
}
