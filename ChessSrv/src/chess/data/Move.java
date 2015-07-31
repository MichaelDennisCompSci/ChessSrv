package chess.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chess.enums.Unit;

public class Move {
	private String s;
	private Piece p;
	private int oldRank;
	private int oldFile;
	private int newRank;
	private int newFile;
	private boolean takes;
	private boolean isCastling;
	private boolean isKingSide;
	private boolean isPromo;
	private Unit promo;
	
	public Move(String sIn) {
		s=sIn;
	}
	
	public Move(Piece pIn, boolean isKingSideIn){
		p=pIn;
		isCastling=true;
		isKingSide=isKingSideIn;
		s=(isKingSide)?"O-O":"O-O-O";
	}
	
	public Move(Piece pIn, int oldRankIn, int oldFileIn, int newRankIn, int newFileIn, boolean takesIn, Unit promoIn){
		this(pIn,oldRankIn,oldFileIn,newRankIn,newFileIn,takesIn);
		promo=promoIn;
		isPromo=true;
		
	}
	
	public Move(Piece pIn, int oldRankIn, int oldFileIn, int newRankIn, int newFileIn, boolean takesIn){
		p=pIn;
		oldRank=oldRankIn;
		oldFile=oldFileIn;
		newRank=newRankIn;
		newFile=newFileIn;
		takes=takesIn;
		
		s="";
		switch(p.getUnit()){
		case ROOK:
			s+="R";
		break;
		case KNIGHT:
			s+="N";
		break;
		case BISHOP:
			s+="B";
		break;
		case QUEEN:
			s+="Q";
		break;
		case KING:
			s+="K";
		break;
		case PAWN:
		break;
		}
		
		s+=rankString(oldRank)+'a';
		s+=fileString(oldFile);
		s=(takes)?"x":"";
		s+=rankString(newRank)+'a';
		s+=fileString(newFile)+'a';
	}

  public static String fileString(int file) {
    String files = "abcdefgh";
    return ""+files.charAt(7-file);
  }

  public static String rankString(int rank) {
    return ""+(rank+1);
  }
	
	public boolean isCastling(){
		return isCastling;
	}
	
	public boolean isKingSide(){
		return isKingSide;
	}
	
	public Piece getPiece(){
		return p;
	}
	
	public int newRank(){
		return newRank;
	}
	
	public int newFile(){
		return newFile;
	}
	
	public int oldRank(){
		return oldRank;
	}
	
	public int oldFile(){
		return oldFile;
	}
	
	public Unit getPromo(){
		return promo;
	}
	
	public boolean takes(){
		return takes;
	}
	
	public String toString(){
		return s;
	}
	
	public boolean isPromo(){
		return isPromo;
	}
	
	public static List<String> printMoveList(List<Move> moves){ //Array list in same order as moves
		
		List<String> result= new ArrayList<String>();
		
		Piece[][] destinations= new Piece[8][8];
		Set<Piece> ambiguos = new HashSet<Piece>();
		
		for(Move move:moves){
			if(destinations[move.newRank][move.newFile]!=null){
				ambiguos.add(move.p);
				ambiguos.add(destinations[move.newRank][move.newFile]);
			}	
			destinations[move.newRank][move.newFile]=move.p;
		}
		
		for(Move move:moves){
			StringBuilder sb= new StringBuilder();
			if(ambiguos.contains(move)){
				move.toString();
			}else{
				if(move.isCastling){
					if(move.isKingSide){
						sb.append("O-O");
					}else{
						sb.append("O-O-O");
					}
				}else{
					switch(move.p.getUnit()){
					case ROOK:
						sb.append("R");
					break;
					case KNIGHT:
						sb.append("N");
					break;
					case BISHOP:
						sb.append("B");
					break;
					case QUEEN:
						sb.append("Q");
					break;
					case KING:
						sb.append("K");
					break;
					case PAWN:
					break;
					}
					
					if(move.takes){
						sb.append("x");
					}
					
					}
					
					sb.append(fileString(move.newFile));
					sb.append(rankString(move.newRank));
					
					if(move.isPromo()){
						sb.append("=");
						switch(move.getPromo()){
						case QUEEN:
							sb.append("Q");
							break;
						case KNIGHT:
							sb.append("N");
							break;
						case BISHOP:
							sb.append("B");
							break;
						case ROOK:
							sb.append("R");
							break;
						default:
							throw null;
						}
					
					
				}
			}
			
			result.add(sb.toString());
		}
		
		return result;
	}
}
