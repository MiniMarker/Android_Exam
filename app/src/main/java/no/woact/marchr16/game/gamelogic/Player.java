package no.woact.marchr16.game.gamelogic;

// Created by Christian on 11/03/2018.

public class Player {

	private long mId;
	private String mName;
	private Marks mChosenMark;

	public Player(Marks chosenMark) {
		setChosenMark(chosenMark);
	}

	public long getId() {
		return mId;
	}

	public void setId(long mId) {
		this.mId = mId;
	}

	public Marks getChosenMark() {
		return mChosenMark;
	}

	public void setChosenMark(Marks mChosenMark) {
		this.mChosenMark = mChosenMark;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	@Override
	public String toString() {
		return "Player{" +
				"id=" + mId +
				", name='" + mName + '\'' +
				", chosenMark=" + mChosenMark +
				'}';
	}
}
