package chess.game;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import chess.data.Move;

public class Player {
	private Socket s;
	private Scanner sc;
	private PrintStream os;
	
	public Player(Socket sIn) throws IOException{
		s=sIn;
		sc= new Scanner(s.getInputStream());
		os= new PrintStream(s.getOutputStream());

		tell("Welcome!");
		tell("Waiting for game to start...");
	}
	
	public void tell(Object o){
		os.print(((o==null)?"null":o.toString())+"\n\r");
	}
	
	public void tell(){
		tell("");
	}

	// public Move getMove() {		
	// 	return new Move(sc.nextLine());
	// }

	public String getMoveString() {		
		return sc.nextLine();
	}

}
