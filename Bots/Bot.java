/******************************************************************************
 *  Compilation:  javac EchoClient.java In.java Out.java
 *  Execution:    java EchoClient name host
 *
 *  Connects to host server on port 4444, sends text, and prints out
 *  whatever the server sends back.
 *
 *  
 *  % java EchoClient wayne localhost
 *  Connected to localhost on port 4444
 *  this is a test
 *  [wayne]: this is a test
 *  it works
 *  [wayne]: it works
 *  <Ctrl-d>                 
 *  Closing connection to localhost
 *
 *  Windows users: replace <Ctrl-d> with <Ctrl-z>
 *  
 ******************************************************************************/

import java.net.Socket;
import java.util.Arrays;
import chess.data.ChessBoard;
import chess.data.AsciiBoard;
import chess.data.Piece;

public class Bot {

  public static String getMove(String[] moves, ChessBoard board) {return moves[0];}

  public static void main(String[] args) throws Exception {
    //String screenName = args[0];
    String host       = args[0];
    int port          = 5490;

    // connect to server and open up IO streams
    Socket socket = new Socket(host, port);
    In     stdin  = new In();
    In     in     = new In(socket);
    Out    out    = new Out(socket);
    System.err.println("Connected to " + host + " on port " + port);

    String line = in.readLine();
    boolean done = false;
    while (!done) {
      String moves = "";
      String boardString = "";
      while (!line.contains("Are you a human?") && !line.contains("wins") && !line.contains("draw") && !line.contains("Enter a valid move")) {
        if (line.length()>0) {
            if (line.charAt(0)=='[') {
              moves = line;
            }
            if (line.substring(0,5).equals("BOARD")) {
              boardString = line.substring(5,line.length());
            }
        }
        line = in.readLine();
      }
      if (line.contains("Are you a human?")) {
        out.println("no");
      } else if (line.contains("Enter a valid move")) {
        String[] moveArr = moves.substring(1,moves.length()-1).split(", ");
        ChessBoard board = new ChessBoard(boardString);
        AsciiBoard ab = new AsciiBoard(board);
        ab.printBig();
        String move = getMove(moveArr,board);
        out.println(move);
      } else {
        System.out.println(line);
        done = true;
      }
      line = in.readLine();
    }

    // close IO streams, then socket
    System.err.println("Closing connection to " + host);
    out.close();
    in.close();
    socket.close();
  }
}
