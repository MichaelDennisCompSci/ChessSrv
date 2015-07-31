package chess.data;

import chess.enums.Team;
import chess.enums.Unit;

//Default hash code and equals are necessary
public class Piece { 
	private Unit u;
	private Team t;
	
	public Piece(Unit uIn, Team tIn){
		u=uIn;
		t=tIn;
	}

	public Unit getUnit() {
		return u;
	}

	public Team getTeam() {
		return t;
	}

  public String toString() {
    String s="";
    switch(t) {
    case WHITE: s="W";
      break;
    case BLACK: s="B";
      break;
    }

    switch(u) {
    case PAWN: s+="P";
      break;
    case KNIGHT: s+="N";
      break;
    case BISHOP: s+="B";
      break;
    case ROOK: s+="R";
      break;
    case QUEEN: s+="Q";
      break;
    case KING: s+="K";
      break;
    }
    return s;
  }
}
