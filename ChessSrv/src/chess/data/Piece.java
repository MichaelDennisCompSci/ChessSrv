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
  
  public Piece(String pieceString) {
    if (pieceString.charAt(0)=='W') {
      t = Team.WHITE;
    } else {
      t = Team.BLACK;
    }
    if (pieceString.charAt(1)=='P') {
      u = Unit.PAWN;
    } else if (pieceString.charAt(1)=='B') {
      u = Unit.BISHOP;
    } else if (pieceString.charAt(1)=='N') {
      u = Unit.KNIGHT;
    } else if (pieceString.charAt(1)=='R') {
      u = Unit.ROOK;
    } else if (pieceString.charAt(1)=='Q') {
      u = Unit.QUEEN;
    } else {
      u = Unit.KING;
    }
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
