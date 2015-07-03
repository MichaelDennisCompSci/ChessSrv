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
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {PAWN,PAWN  ,PAWN  ,PAWN ,PAWN,PAWN  ,PAWN  ,PAWN},
     {ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}};
  public static final Team[][] t=
  {{WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE},
   {WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE},
   {null, null ,null ,null ,null ,null ,null ,null },
   {null, null ,null ,null ,null ,null ,null ,null },
   {null, null ,null ,null ,null ,null ,null ,null },
   {null, null ,null ,null ,null ,null ,null ,null },
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
		for(int rank=0; rank<u.length; rank++){
			for(int file=0; file<u[rank].length; file++){
        if (u[rank][file]==null && t[rank][file]==null)
          board[rank][file]=null;
        else
          board[rank][file]=new Peice(u[rank][file],t[rank][file]);
			}
		}
	}

  public Peice[][] getBoard() {
    return board;
  }

  public Peice[][] copy() {
    Peice[][] newBoard = new Peice[8][8];
    for (int i=0; i<7; i++) {
      for (int j=0; j<7; j++) {
        newBoard[i][j] = board[i][j];
      }
    }
    return newBoard;
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
    board[move.oldRank()][move.oldFile()]=null;
    board[move.newRank()][move.newFile()]=move.getPiece();
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

  //gets all "reachable" squares on the board, i.e. open spaces or opposing team
  public List<Move> getAllReachable() {
    List<Move> moves = new ArrayList<Move>();
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if (board[i][j]!=null) {
          moves.addAll(getReachable(i,j));
        }
      }
    }
    return moves;
  }

  public List<Move> getReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    Unit u = board[i][j].getUnit();
    switch (u) {
    case PAWN: moves.addAll(getPawnReachable(i,j));
      break;
    case KNIGHT: moves.addAll(getKnightReachable(i,j));
      break;
    case BISHOP: moves.addAll(getBishopReachable(i,j));
      break;
    case ROOK: moves.addAll(getRookReachable(i,j));
      break;
    case QUEEN: moves.addAll(getQueenReachable(i,j));
      break;
    case KING: moves.addAll(getKingReachable(i,j));
      break;
    }
    return moves;
  }

  public List<Move> getPawnReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(pawnReach(i,j,1,0));
    moves.addAll(pawnReach(i,j,2,0));
    moves.addAll(pawnReach(i,j,1,1));
    moves.addAll(pawnReach(i,j,1,-1));
    return moves;
  }
  public List<Move> getKnightReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(knightReach(i,j,1,2));
    moves.addAll(knightReach(i,j,1,-2));
    moves.addAll(knightReach(i,j,2,1));
    moves.addAll(knightReach(i,j,2,-1));
    moves.addAll(knightReach(i,j,-1,2));
    moves.addAll(knightReach(i,j,-1,-2));
    moves.addAll(knightReach(i,j,-2,1));
    moves.addAll(knightReach(i,j,-2,-1));
    return moves;
  }
  public List<Move> getBishopReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(diagonalReach(i,j,1,1));
    moves.addAll(diagonalReach(i,j,-1,1));
    moves.addAll(diagonalReach(i,j,1,-1));
    moves.addAll(diagonalReach(i,j,-1,-1));
    return moves;
  }
  public List<Move> getRookReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(verticalReach(i,j,1));
    moves.addAll(verticalReach(i,j,-1));
    moves.addAll(horizontalReach(i,j,1));
    moves.addAll(horizontalReach(i,j,-1));
    return moves;
  }
  public List<Move> getQueenReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(getRookReachable(i,j));
    moves.addAll(getBishopReachable(i,j));
    return moves;
  }
  public List<Move> getKingReachable(int i, int j) {
    List<Move> moves = new ArrayList<Move>();
    moves.addAll(kingReach(i,j,1,1));
    moves.addAll(kingReach(i,j,1,0));
    moves.addAll(kingReach(i,j,1,-1));
    moves.addAll(kingReach(i,j,-1,1));
    moves.addAll(kingReach(i,j,-1,0));
    moves.addAll(kingReach(i,j,-1,1));
    moves.addAll(kingReach(i,j,0,1));
    moves.addAll(kingReach(i,j,0,-1));
    return moves;
  }

  //gets reachable in column either positive or negative
  public List<Move> verticalReach(int i, int j, int dir) {
    List<Move> moves = new ArrayList<Move>();
    int row = i+dir;
    boolean blocked = false;
    while (row<8 && row>=0 && !blocked) {
      if (board[row][j]!=null) {
        blocked = true;
        if (board[row][j].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,row,j,true));
      } else 
        moves.add(new Move(board[i][j],i,j,row,j,false));
      row+=dir;
    }
    return moves;
  }

  //gets reachable in row either positive or negative
  public List<Move> horizontalReach(int i, int j, int dir) {
    List<Move> moves = new ArrayList<Move>();
    int col = j+dir;
    boolean blocked = false;
    while (col<8 && col>=0 && !blocked) {
      if (board[i][col]!=null) {
        blocked = true;
        if (board[i][col].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,i,col,true));
      } else
        moves.add(new Move(board[i][j],i,j,i,col,false));
      col+=dir;
    }
    return moves;
  }

  //gets reachable on diagonal in any of four directions
  public List<Move> diagonalReach(int i, int j, int idir, int jdir) {
    List<Move> moves = new ArrayList<Move>();
    int row = i+idir;
    int col = j+jdir;
    boolean blocked = false;
    while (col<8 && col>=0 && row<8 && row>=0 && !blocked) {
      if (board[row][col]!=null) {
        blocked = true;
        if (board[row][col].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,row,col,true));
      } else
        moves.add(new Move(board[i][j],i,j,row,col,false));
      row+=idir;
      col+=jdir;
    }
    return moves;
  }

  //gets reachable for knight jump in any of eight directions
  public List<Move> knightReach(int i, int j, int idir, int jdir) {
    List<Move> moves = new ArrayList<Move>();
    int row = i+idir;
    int col = j+jdir;
    if (row<8 && row>=0 && col<8 && col>=0) {
      if (board[row][col]!=null) {
        if (board[row][col].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,row,col,true));
      } else
        moves.add(new Move(board[i][j],i,j,row,col,false));
    }
    return moves;
  }

  //gets reachable for king move in any of eight directions
  public List<Move> kingReach(int i, int j, int idir, int jdir) {
    List<Move> moves = new ArrayList<Move>();
    int row = i+idir;
    int col = j+jdir;
    if (row<8 && row>=0 && col<8 && col>=0) {
      if (board[row][col]!=null) {
        if (board[row][col].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,row,col,true));
      } else
        moves.add(new Move(board[i][j],i,j,row,col,false));
    }
    return moves;
  }

  //gets reachable for pawn move in any of four places
  public List<Move> pawnReach(int i, int j, int idir, int jdir) {
    List<Move> moves = new ArrayList<Move>();
    if (board[i][j].getTeam()==BLACK)
      idir=-idir;
    int row = i+idir;
    int col = j+jdir;
    if (row<8 && row>=0 && col<8 && col>=0) {
      if (board[row][col]!=null && jdir!=0) {
        if (board[row][col].getTeam()!=board[i][j].getTeam())
          moves.add(new Move(board[i][j],i,j,row,col,true));
      }
      if (board[row][col]==null && jdir==0)
        moves.add(new Move(board[i][j],i,j,row,col,false));
    }
    return moves;
  }

  public boolean attacksKing(Move m) {
    if (board[m.newRank()][m.newFile()]!=null)
      if (board[m.newRank()][m.newFile()].getUnit()==KING)
        return true;
    return false;
  }

	public boolean hasNextMove() {
		// TODO Auto-generated method stub
		return true;
	}
}
