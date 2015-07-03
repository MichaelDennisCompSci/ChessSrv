package chess.data;

import java.util.HashMap;

public class AsciiBoard {
  
  String[][] board;
  public static final String EDGE = "|";
  public static final String WHITEEDGE = " |";
  public static final String BLACKEDGE = "^|";
  
  public static final String ENDLINE = " +-----------------------------------------------------------------------------------------------+";
  //public static final String BOTLINE = " +-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+";
  //public static final String LINE0 = " |-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------|";
  public static final String MIDLINE = " |-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------|";  

  public static final HashMap<String,String> SMALLASCII = new HashMap() {{
      put("WP","\u2659 ");
      put("WR","\u2656 ");
      put("WN","\u2658 ");
      put("WB","\u2657 ");
      put("WQ","\u2655 ");
      put("WK","\u2654 ");
      put("BP","\u265F ");
      put("BR","\u265C ");
      put("BN","\u265E ");
      put("BB","\u265D ");
      put("BQ","\u265B ");
      put("BK","\u265A ");
  }};

  public static final HashMap<String,String[]> BIGASCII = new HashMap() {{
    put("WK",new String[] {"   .::.   ",
                         "   _::_   ",
                         " _/____\\_ ",
                         " \\______/ "});

    put("WQ",new String[] {"   _()_   ",
                         " _/____\\_ ",
                         " \\      / ",
                         "  \\____/  "});

    put("WB",new String[] {"   _O     ",
                         "  / //\\   ",
                         " {     }  ",
                         "  \\___/   "});

    put("WN",new String[] {"  |\\__    ",
                         " /  - \\_  ",
                         " |    __< ",
                         " |___\\    "});

    put("WR",new String[] {" _  _  _  ",
                         "| || || | ",
                         "|_______| ",
                         " \\_ __ /  "});

    put("WP",new String[] {"    _     ",
                         "   (_)    ",
                         "  (___)   ",
                         "  _|_|_   "});

    put("BK",new String[] {"   .::.   ",
                         "   _::_   ",
                         " _/XXXX\\_ ",
                         " \\XXXXXX/ "});

    put("BQ",new String[] {"   _()_   ",
                         " _/XXXX\\_ ",
                         " \\XXXXXX/ ",
                         "  \\XXXX/  "});

    put("BB",new String[] {"   _O     ",
                         "  /X//\\   ",
                         " {XXXXX}  ",
                         "  \\XXX/   "});

    put("BN",new String[] {"  |\\__    ",
                         " /XX-X\\_  ",
                         " |XXXXXX< ",
                         " |XXX\\    "});

    put("BR",new String[] {" _  _  _  ",
                         "|X||X||X| ",
                         "|XXXXXXX| ",
                         " \\XXXXX/  "});

    put("BP",new String[] {"    _     ",
                          "   (X)    ",
                          "  (XXX)   ",
                          "  _|X|_   "});
  }};
  
  public AsciiBoard() {
    board = new String[][] {
      {"BR","BN","BB","BQ","BK","BB","BN","BR"},
      {"BP","BP","BP","BP","BP","BP","BP","BP"},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"WP","WP","WP","WP","WP","WP","WP","WP"},
      {"WR","WN","WB","WQ","WK","WB","WN","WR"}
    };
  }

  public AsciiBoard(ChessBoard cb) {
    board = new String[8][8];
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if (cb.getBoard()[7-i][7-j]!=null)
          board[i][j]=cb.getBoard()[7-i][7-j].toString();
        else
          board[i][j]="";
      }
    }
  }

  public void printSmall() {
    System.out.println(this.toSmallString());
  }

  public void printBig() {
    System.out.println(this.toBigString());
  }

  public String toSmallString() {
    String boardString = "";
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        boardString += toSmallAscii(board[i][j],i,j) + EDGE;
      }
      boardString += "\n";
    }
    return boardString;
  }

  public String toBigString() {
    String boardString = ENDLINE+"\n";
    for (int i=0; i<40; i++) {
      boardString += WHITEEDGE;
      for (int j=0; j<8; j++) {
        if ((i/5)%2!=j%2) {boardString += toBigAscii(board[i/5][j],i,j) + BLACKEDGE;}
        else {boardString += toBigAscii(board[i/5][j],i,j) + WHITEEDGE;}
      }
      if (i%5==4) {
        if ((i/5)%2==0) {boardString += "\n"+MIDLINE+"\n";}
        else {
          if (i==39) {boardString += "\n"+ENDLINE;}
          else {boardString += "\n"+MIDLINE+"\n";}
        }
      } else {
        boardString += "\n";
      }
    }
    return boardString;
  }

  public static String toSmallAscii(String s, int i, int j) {
    if (s.equals("")) {
      if (i%2==j%2) {return "XX";}
      else {return "  ";}
    } else {
      return SMALLASCII.get(s);
    }
  }

  public static String toBigAscii(String s, int i, int j) {
    if (s.equals("")) {
      if ((i/5)%2!=j%2) {return "^^^^^^^^^^";}
      else {return "          ";}
    } else {
      if (i%5>3) {
        if ((i/5)%2!=j%2) {return "^^^^^^^^^^";}        
        else {return "          ";}
      } else {
        return BIGASCII.get(s)[i%5];
      }
    }
  }
 
}
