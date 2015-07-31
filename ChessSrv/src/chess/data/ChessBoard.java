package chess.data;
import static chess.enums.Team.BLACK;
import static chess.enums.Team.WHITE;
import static chess.enums.Unit.BISHOP;
import static chess.enums.Unit.KING;
import static chess.enums.Unit.KNIGHT;
import static chess.enums.Unit.PAWN;
import static chess.enums.Unit.QUEEN;
import static chess.enums.Unit.ROOK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.enums.Team;
import chess.enums.Unit;

public class ChessBoard {
	public static final Unit[][] u = new Unit[][]
    {{ROOK,KNIGHT,BISHOP,KING,QUEEN,BISHOP,KNIGHT,ROOK},
     {PAWN,PAWN  ,PAWN  ,PAWN ,PAWN,PAWN  ,PAWN  ,PAWN},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {null,null  ,null  ,null ,null,null  ,null  ,null},
     {PAWN,PAWN  ,PAWN  ,PAWN ,PAWN,PAWN  ,PAWN  ,PAWN},
     {ROOK,KNIGHT,BISHOP,KING,QUEEN,BISHOP,KNIGHT,ROOK}};
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
	
	private Piece[][] board= new Piece[BOARD_HIGHT][BOARD_WIDTH];  //[rank][file]
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
          board[rank][file]=new Piece(u[rank][file],t[rank][file]);
			}
		}
		lastPawnMoveFile=-1;
		whiteCanCastelLeft=true;
		whiteCanCastelRight=true;
		blackCanCastelLeft=true;
		blackCanCastelRight=true;
	}
	
	public ChessBoard(ChessBoard cb){
		this();
		board=cb.copy();
		lastPawnMoveFile=cb.lastPawnMoveFile;
		whiteCanCastelLeft=cb.whiteCanCastelLeft;
		whiteCanCastelRight=cb.whiteCanCastelRight;
		blackCanCastelLeft=cb.blackCanCastelLeft;
		blackCanCastelRight=cb.blackCanCastelRight;
	}

  public Piece[][] getBoard() {
    return board;
  }

  public Piece[][] copy() {
    Piece[][] newBoard = new Piece[8][8];
    for (int i=0; i<7; i++) {
      for (int j=0; j<7; j++) {
        newBoard[i][j] = board[i][j];
      }
    }
    return newBoard;
  }

	public boolean isCheck(Team t) {
		for (Move m: getAllReachable()) {
			if(m.getPiece().getTeam()!=t){
				if(board[m.newRank()][m.newFile()]!=null
				 &&board[m.newRank()][m.newFile()].getUnit()==KING
				 &&board[m.newRank()][m.newFile()].getTeam()==t){
						return true;
				}
			}
		}
		return false;
	}

	public void move(Move move) {
	lastPawnMoveFile=-1;
	if(move.isCastling()){
		int rank=0;
		if(move.getPiece().getTeam()==WHITE){
			whiteCanCastelLeft=false;
			whiteCanCastelRight=false;
			rank=0;
		}else{
			blackCanCastelLeft=false;
			blackCanCastelRight=false;
			rank=7;
		}
		
		if(move.isKingSide()){
			board[rank][2]=board[rank][0];
			board[rank][1]=board[rank][3];
			board[rank][3]=null;
			board[rank][0]=null;
			return;
		}else{
			board[rank][4]=board[rank][7];
			board[rank][5]=board[rank][3];
			board[rank][3]=null;
			board[rank][7]=null;
			return;
		}
	}
	if(move.getPiece().getUnit()==KING){
		if(move.getPiece().getTeam()==WHITE){
			whiteCanCastelLeft=false;
			whiteCanCastelRight=false;
		}else{
			blackCanCastelLeft=false;
			blackCanCastelRight=false;
		}
	}
	
	if(move.getPiece().getUnit()==ROOK){
		if(move.oldRank()==0){
				if(move.oldFile()==0){
					whiteCanCastelRight=false;
				}else{
					whiteCanCastelLeft=false;
				}
		}else if(move.oldRank()==7){
			if(move.oldFile()==0){
				blackCanCastelRight=false;
			}else{
				blackCanCastelLeft=false;
			}
		}
	}
	
	if(move.getPiece().getUnit()==PAWN){
		if(Math.abs(move.oldRank()-move.newRank())==2){
			lastPawnMoveFile=move.newFile();
		}
		if(move.oldFile()!=move.newFile()&& board[move.newRank()][move.newFile()]==null){
			if(move.getPiece().getTeam()==WHITE){
				board[move.newRank()-1][move.newFile()]=null;
			}else{
				board[move.newRank()+1][move.newFile()]=null;
			}
		}
	}
	
	Piece p=move.getPiece();
	if(move.isPromo()){
		p=new Piece(move.getPromo(), move.getPiece().getTeam());
	}
	
    board[move.oldRank()][move.oldFile()]=null;
    board[move.newRank()][move.newFile()]=p;
	}

	public List<Move> getListOfMoves(Team turn) {
    List<Move> moves = new ArrayList<Move>();
    for (Move m : getAllReachable()) {
      ChessBoard copy = new ChessBoard(this);
      copy.move(m);
      if (!copy.isCheck(turn) && m.getPiece().getTeam()==turn)
        moves.add(m);
    }

    moves.addAll(castel(turn));
    moves.addAll(enpassant(turn));
    return moves;
  }

  private List<Move> castel(Team turn) {
	  List<Move> result = new ArrayList<Move>();
	  if(this.isCheck(turn)){
		  return result;
	  }
		if(turn==WHITE){
			if(whiteCanCastelLeft){
				if(board[0][1]==null&&board[0][2]==null){
					ChessBoard copy1 = new ChessBoard(this);
					ChessBoard copy2 = new ChessBoard(this);
					copy1.move(new Move(board[0][3],0,3,0,1,false));
					copy2.move(new Move(board[0][3],0,3,0,2,false));
					if(!copy1.isCheck(turn)&&!copy2.isCheck(turn)){
						result.add(new Move(board[0][3],true));
					}
				}
			}
			if(whiteCanCastelRight){
				if(board[0][5]==null&&board[0][6]==null){
					ChessBoard copy0 = new ChessBoard(this);
					ChessBoard copy1 = new ChessBoard(this);
					ChessBoard copy2 = new ChessBoard(this);
					copy0.move(new Move(board[0][3],0,3,0,4,false));
					copy1.move(new Move(board[0][3],0,3,0,5,false));
					copy2.move(new Move(board[0][3],0,3,0,6,false));
					if(!copy0.isCheck(turn)&& !copy1.isCheck(turn)&&!copy2.isCheck(turn)){
						result.add(new Move(board[0][3],false));
					}
				}
			}
		}else{
			if(blackCanCastelLeft){
				if(board[7][1]==null&&board[7][2]==null){
					ChessBoard copy1 = new ChessBoard(this);
					ChessBoard copy2 = new ChessBoard(this);
					copy1.move(new Move(board[7][3],7,3,7,1,false));
					copy2.move(new Move(board[7][3],7,3,7,2,false));
					if(!copy1.isCheck(turn)&&!copy2.isCheck(turn)){
						result.add(new Move(board[7][3],true));
					}
				}
			}
			if(blackCanCastelRight){
				if(board[7][5]==null&&board[7][6]==null){
					ChessBoard copy0 = new ChessBoard(this);
					ChessBoard copy1 = new ChessBoard(this);
					ChessBoard copy2 = new ChessBoard(this);
					copy0.move(new Move(board[7][3],7,3,7,4,false));
					copy1.move(new Move(board[7][3],7,3,7,5,false));
					copy2.move(new Move(board[7][3],7,3,7,6,false));
					if(!copy0.isCheck(turn)&& !copy1.isCheck(turn)&&!copy2.isCheck(turn)){
						result.add(new Move(board[7][3],false));
					}
				}
			}
		}
		return result;
	}
  
private List<Move> enpassant(Team turn) {
	List<Move> result = new ArrayList<Move>();
	if(lastPawnMoveFile==-1){
		return result;
	}
	
	if(turn==WHITE){
		if(lastPawnMoveFile+1<board[4].length){
			Piece p= board[4][lastPawnMoveFile+1];
			
			if(p!=null && p.getUnit()==PAWN && p.getTeam()==WHITE){
				result.add(new Move(p, 4, lastPawnMoveFile+1, 5, lastPawnMoveFile, true));
			}
		}
		if(lastPawnMoveFile-1>=0){
			Piece p= board[4][lastPawnMoveFile-1];
			
			if(p!=null&& p.getUnit()==PAWN && p.getTeam()==WHITE){
				result.add(new Move(p, 4, lastPawnMoveFile-1, 5, lastPawnMoveFile, true));
			}
		}
	}else{
		if(lastPawnMoveFile+1<board[3].length){
			Piece p= board[3][lastPawnMoveFile+1];
			
			if(p!=null && p.getUnit()==PAWN && p.getTeam()==BLACK){
				result.add(new Move(p, 3, lastPawnMoveFile+1, 2, lastPawnMoveFile, true));
			}
		}
		if(lastPawnMoveFile-1>=0){
			Piece p= board[3][lastPawnMoveFile-1];
			
			if(p!=null && p.getUnit()==PAWN && p.getTeam()==BLACK){
				result.add(new Move(p, 3, lastPawnMoveFile-1, 2, lastPawnMoveFile, true));
			}
		}
	}
	return result;
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
    if(idir==2 && ((board[i][j].getTeam()==BLACK && i!=6) ||(board[i][j].getTeam()==WHITE && i!=1))){
    	return moves;
    }
    if (board[i][j].getTeam()==BLACK)
      idir=-idir;
    int row = i+idir;
    int col = j+jdir;
    if (row<8 && row>=0 && col<8 && col>=0) {
      if (board[row][col]!=null && jdir!=0) {
        if (board[row][col].getTeam()!=board[i][j].getTeam())
        	if((board[i][j].getTeam()==BLACK && row!=0)||(board[i][j].getTeam()==WHITE && row!=7)){
        		moves.add(new Move(board[i][j],i,j,row,col,true));
        	}else{
        		moves.add(new Move(board[i][j],i,j,row,col,true,ROOK));
        		moves.add(new Move(board[i][j],i,j,row,col,true,KNIGHT));
        		moves.add(new Move(board[i][j],i,j,row,col,true,BISHOP));
        		moves.add(new Move(board[i][j],i,j,row,col,true,QUEEN));
        	}
      }
      if (board[row][col]==null && jdir==0)
    	  if((board[i][j].getTeam()==BLACK && row!=0)||(board[i][j].getTeam()==WHITE && row!=7)){
    		  moves.add(new Move(board[i][j],i,j,row,col,false));
    	  }else{
    		  	moves.add(new Move(board[i][j],i,j,row,col,true,ROOK));
    		  	moves.add(new Move(board[i][j],i,j,row,col,true,KNIGHT));
    		  	moves.add(new Move(board[i][j],i,j,row,col,true,BISHOP));
    		  	moves.add(new Move(board[i][j],i,j,row,col,true,QUEEN));
    	  }
    }
    return moves;
  }
}
