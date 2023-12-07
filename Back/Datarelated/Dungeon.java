package Back.Datarelated;

import java.io.Serializable;

public class Dungeon  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int start;
	private int clear;
	private int difficulty;
	private char maptype;
	
	public Dungeon(int start, int clear, int difficulty, char maptype) {
		setStart(start);
		setClear(clear);
		setDifficulty(difficulty);
		setMaptype(maptype);
	}
	
	public void setStart(int start) {this.start = start;	}
	public int getStart() {return start;	}
	
	public void setClear(int clear) {this.clear = clear;	}
	public int getClear() {return clear;	}
	
	public void setDifficulty(int difficulty) {this.difficulty = difficulty;	}
	public int getDifficulty() {return difficulty;
	}	
	public void setMaptype(char maptype) {this.maptype = maptype;	}
	public char getMaptype() {return maptype;	}
}
