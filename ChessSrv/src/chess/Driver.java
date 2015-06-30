package chess;

import java.io.IOException;
import java.net.ServerSocket;
import chess.game.Game;
import chess.game.Player;

public class Driver {
	public static void main(String[] args){
		
		
		while(true){
			try(ServerSocket server=new ServerSocket(5490)){
				System.out.println("Waiting for a player.");
				Player p1= new Player(server.accept());
				System.out.println("Player 1 has connected.");
				System.out.println();
				
				System.out.println("Waiting for player 2");
				Player p2= new Player(server.accept());
				System.out.println("Player 2 connected");
				System.out.println();
				
				System.out.println("Starting game...");
				new Thread(new Game(p1,p2)).start();
				System.out.println("Game Started");
				System.out.println();

			} catch (IOException e) {
				System.err.println("An IO Error Has occured: "+ e.getMessage());
			}
		}
	}
}
