package no.woact.marchr16.database.entities;
// Created by Christian on 22/03/2018.

public class MatchStats {

	private long id;
	private String player_name;
	private int victories;
	private int defeats;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}

	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	@Override
	public String toString() {
		return "MatchStats{" +
				"player_name=" + player_name +
				", victories=" + victories +
				", defeats=" + defeats +
				'}';
	}
}
