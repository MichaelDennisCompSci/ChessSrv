package chess.data;

import chess.enums.Team;
import chess.enums.Unit;

//Default hash code and equals are necessary
public class Peice { 
	private Unit u;
	private Team t;
	
	public Peice(Unit uIn, Team tIn){
		u=uIn;
		t=tIn;
	}

	public Unit getUnit() {
		return u;
	}

	public Team getTeam() {
		return t;
	}
}
