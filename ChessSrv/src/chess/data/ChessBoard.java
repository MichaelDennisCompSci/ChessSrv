package chess.data;
import static chess.enums.MoveResponse.*;
import static chess.enums.Team.*;
import static chess.enums.Unit.*;

import java.util.List;
import java.util.ArrayList;

import chess.enums.MoveResponse;
import chess.enums.Team;
import chess.enums.Unit;

public class ChessBoard {
	public static final Unit[][] u = new Unit[][]
    {{ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK},
     {PAWN,PAWN  ,PAWN  ,PAWN ,PAWN,PAWN  ,PAWN  ,PAWN},
     {},
     {},
     {},
     {},
     {PAWN,PAWN  ,PAWN  ,PAWN ,PAWN,PAWN  ,PAWN  ,PAWN},
     {ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}};
  public static final Team[][] t=
  {{WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE},
   {WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE},
   {},
   {},
   {},
   {},
   {BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK},
   {BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK}};
	private static final int BOARD_HIGHT = 8;
	private static final int BOARD_WIDTH = 8;
	
	private Peice[][] board= new Peice[BOARD_HIGHT][BOARD_WIDTH];  //[rank][file]
	private int lastPawnMoveFile;
	private boolean whiteCanCastelLeft;
	private boolean whiteCanCastelRight;
	private boolean blackCanCastelLeft;
	private boolean blackCanCastelRight;
	
	public ChessBoard(){
		for(int rank =0; rank<u.length; rank++){
			for(int file=0; file<u[rank].length; file++){
				board[rank][file]=new Peice(u[rank][file],t[rank][file]);
			}
		}
	}

	public Team isCheck() {
		// TODO Auto-generated method stub
    List<Move> reachableMoves = getAllReachable();
    for (Move m: reachableMoves) {
      if (attacksKing(m))
        return board[m.newRank()][m.newFile()].getTeam();
    }
    return null;
	}

	public void move(Move move) {
		// TODO Auto-generated method stub
	}

	public List<Move> getListOfMoves() {
		// TODO Auto-generated method stub
    List<Move> moves = new ArrayList<Move>();
    List<Move> reachableMoves = getAllReachable();
    for (Move m : reachableMoves) {
      if (!attacksKing(m))
        moves.add(m);
    }
    return moves;
  }

  public List<Move> getAllReachable() {
    List<Move> moves = new ArrayList<Move>();
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if (board[i][j]!=null) {
          moves.addAll(getReachable(board[i][j],i,j));
        }
      }
    }
    return moves;
  }

  public List<Move> getReachable(Peice p, int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    Unit u = p.getUnit();
    switch (u) {
    case PAWN: if (p.getTeam()==WHITE) {
        
      } else

        break;
    case KNIGHT: if (p.getTeam()==WHITE) {

      } else

        break;
    case BISHOP: if (p.getTeam()==WHITE) {

      } else

        break;
    case ROOK: if (p.getTeam()==WHITE) {

      } else

        break;
    case QUEEN: if (p.getTeam()==WHITE) {

      } else

        break;
    case KING: if (p.getTeam()==WHITE) {

      } else

        break;
    }
    return moves;
  }

  public boolean attacksKing(Move m) {
    if (board[m.newRank()][m.newFile()].getUnit()==KING)
      return true;
    else
      return false;
  }

	public boolean hasNextMove() {
		// TODO Auto-generated method stub
		return true;
	}
}
