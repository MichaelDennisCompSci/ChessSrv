package chess.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Move {
	private String s;
	private Peice p;
	private int oldRank;
	private int oldFile;
	private int newRank;
	private int newFile;
	private boolean takes;
	private boolean isCastling;
	private boolean isKingSide;
	
	public Move(String sIn) {
		s=sIn;
	}
	
	public Move(boolean isKingSideIn){
		isCastling=true;
		isKingSide=isKingSideIn;
		s=(isKingSide)?"O-O":"O-O-O";
	}
	
	public Move(Peice pIn, int oldRankIn, int oldFileIn, int newRankIn, int newFileIn, boolean takesIn){
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
    return ""+files.charAt(file);
  }

  public static String rankString(int rank) {
    return ""+(8-rank);
  }
	
	public boolean isCastling(){
		return isCastling;
	}
	
	public boolean isKingSide(){
		return isKingSide;
	}
	
	public Peice getPiece(){
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
	
	public boolean takes(){
		return takes;
	}
	
	public String toString(){
		return s;
	}
	
	public static List<String> printMoveList(List<Move> moves){ //Array list in same order as moves
		
		List<String> result= new ArrayList<String>();
		
		Peice[][] destinations= new Peice[8][8];
		Set<Peice> ambiguos = new HashSet<Peice>();
		
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
					
					sb.append(rankString(move.newRank)+'a');
					sb.append(fileString(move.newFile));
				}
			}
			
			result.add(sb.toString());
		}
		
		return result;
	}
}
