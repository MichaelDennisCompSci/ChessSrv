package chess.game;

import static chess.enums.Team.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.data.ChessBoard;
import chess.data.Move;
import chess.data.AsciiBoard;
import chess.enums.Team;


public class Game implements Runnable{
	private static final int MAX_WRONG_MOVES = 10;
	private Player p1;
	private Player p2;
	private ChessBoard cb;
	
	public Game(Player p1In, Player p2In){
		p1=p1In;		
		p2=p2In;
		
		cb= new ChessBoard();
	}
	
	@Override
	public void run() {		
		p1.tell("The Game is starting.");
		p2.tell("The Game is starting.");
		p1.tell("You are White.");
		p2.tell("You are Black.");
		p1.tell();
		p2.tell();
		
		Team curTeam  =WHITE;
		Player current=p1;
		Player other  =p2;
		
		List<Move> moves;
		while((moves= cb.getListOfMoves(curTeam)).size()>0){
			List<String> strings= Move.printMoveList(moves);
			
			current.tell(strings);
			
			AsciiBoard ab = new AsciiBoard(cb);
			p1.tell(ab.toBigString());
			p2.tell(ab.toBigString());
			
			Map<String,Move> stringToMove= new HashMap<String,Move>();
			
			for(int i=0; i<moves.size(); i++){
				stringToMove.put(strings.get(i), moves.get(i));
			}
			
			String moveString;
			int count=0;
			do{
				current.tell("Enter a valid move: ");
				if(count>MAX_WRONG_MOVES){
					current.tell("You lose, too many wrong moves.");
					other.tell("Other Player made too many wrong moves.");
					
					String s=((curTeam==WHITE)?"BLACK":"WHITE")+" team Wins";
					current.tell(s);
					other.tell(s);
					return;
				}
				count++;
				moveString=current.getMoveString();
			}while(!stringToMove.containsKey(moveString));
			cb.move(stringToMove.get(moveString));
			other.tell("The other player moved: "+moveString);
			
			Player temp= current;
			current=other;
			other=temp;
			
			if(curTeam==WHITE){
				curTeam=BLACK;
			}else{
				curTeam=WHITE;
			}
		}
		
		AsciiBoard ab = new AsciiBoard(cb);
		p1.tell(ab.toBigString());
		p2.tell(ab.toBigString());
		
		Team loser=(cb.isCheck(WHITE))?WHITE:((cb.isCheck(BLACK))?BLACK:null);
		if(loser==null){
			p1.tell("draw");
			p2.tell("draw");
		}
		else if(loser==BLACK){
			p1.tell("WHITE wins");
			p2.tell("WHITE wins");
		}else{
			p1.tell("BLACK wins");
			p2.tell("BLACK wins");
		}
	}
}
