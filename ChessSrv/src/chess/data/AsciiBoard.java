package chess.data;

import java.util.HashMap;
import java.util.Arrays;

public class AsciiBoard {
  
  private String[][] board;
	private int lastPawnMoveFile;
	private boolean whiteCanCastelLeft;
	private boolean whiteCanCastelRight;
	private boolean blackCanCastelLeft;
	private boolean blackCanCastelRight;
  public static final String EDGE = "|";
  public static final String WHITEEDGE = " |";
  public static final String BLACKEDGE = "^|";
  
  public static final String ENDLINE = "           +-----------------------------------------------------------------------------------------------+";
  //public static final String BOTLINE = " +-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+";
  //public static final String LINE0 = " |-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------|";
  public static final String MIDLINE = "           |-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------|";  

  public static final String FILE = "               ___          ___         _____       ___         ____       ____       _____       __ __\n\r"+
		  							"              / _ |        / _ )       / ___/      / _ \\       / __/      / __/      / ___/      / // /\n\r"+
		  							"             / __ |       / _  |      / /__       / // /      / _/       / _/       / (_ /      / _  /\n\r"+
		  							"            /_/ |_|      /____/       \\___/      /____/      /___/      /_/         \\___/      /_//_/";

  public static final HashMap<String,String> RANK = new HashMap<String,String>() {{
      put("81","    _|    ");
      put("82","  _|_|    ");
      put("83","    _|    ");
      put("84","    _|    ");
      put("85","    _|    ");
      put("71","   _|_|   ");
      put("72"," _|    _| ");
      put("73","     _|   ");
      put("74","   _|     ");
      put("75"," _|_|_|_| ");
      put("61"," _|_|_|   ");
      put("62","       _| ");
      put("63","   _|_|   ");
      put("64","       _| ");
      put("65"," _|_|_|   ");
      put("51"," _|  _|   ");
      put("52"," _|  _|   ");
      put("53"," _|_|_|_| ");
      put("54","     _|   ");
      put("55","     _|   ");
      put("41"," _|_|_|_| ");
      put("42"," _|       ");
      put("43"," _|_|_|   ");
      put("44","       _| ");
      put("45"," _|_|_|   ");
      put("31","   _|_|_| ");
      put("32"," _|       ");
      put("33"," _|_|_|   ");
      put("34"," _|    _| ");
      put("35","   _|_|   ");
      put("21"," _|_|_|_| ");
      put("22","      _|  ");
      put("23","     _|   ");
      put("24","    _|    ");
      put("25","   _|     ");
      put("11","   _|_|   ");
      put("12"," _|    _| ");
      put("13","   _|_|   ");
      put("14"," _|    _| ");
      put("15","   _|_|   ");
  }};

  public static final HashMap<String,String> SMALLASCII = new HashMap<String,String>() {{
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

  public static final HashMap<String,String[]> BIGASCII = new HashMap<String, String[]>() {{
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
      {"BR","BN","BB","BK","BQ","BB","BN","BR"},
      {"BP","BP","BP","BP","BP","BP","BP","BP"},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"","","","","","","",""},
      {"WP","WP","WP","WP","WP","WP","WP","WP"},
      {"WR","WN","WB","WK","WQ","WB","WN","WR"}
    };
    lastPawnMoveFile = -1;
    whiteCanCastelLeft = true;
    whiteCanCastelRight = true;
    blackCanCastelLeft = true;
    blackCanCastelRight = true;
  }

  public AsciiBoard(ChessBoard cb) {
    board = new String[8][8];
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if (cb.getBoard()[7-i][7-j]!=null) {
          board[i][j]=cb.getBoard()[7-i][7-j].toString();
        } else {
          board[i][j]="";
        }
      }
    }
    lastPawnMoveFile = cb.getLastPawnMoveFile();
    whiteCanCastelLeft = cb.getWhiteCanCastelLeft();
    whiteCanCastelRight = cb.getWhiteCanCastelRight();
    blackCanCastelLeft = cb.getBlackCanCastelLeft();
    blackCanCastelRight = cb.getBlackCanCastelRight();
  }

  public void printSmall() {
    System.out.println(this.toSmallString());
  }

  public void printBig() {
    System.out.println(this.toBigString());
  }
  
  public String botString() {
    String bs = "";
    bs += "BOARD";
    for (String[] row : board) {
      for (String piece : row) {
        if (piece.length()>0) {
          bs += piece+",";
        } else {
          bs += "null"+",";
        }
      }
      bs = bs.substring(0,bs.length()-1) + ";";
    }
    bs += lastPawnMoveFile+","+whiteCanCastelLeft+","+whiteCanCastelRight+","+blackCanCastelLeft+","+blackCanCastelRight;
    return bs;
  }

  public String toSmallString() {
    String boardString = "";
    for (int i=0; i<8; i++) {
      boardString += (i+1)+"  |";
      for (int j=0; j<8; j++) {
        boardString += toSmallAscii(board[i][j],i,j) + EDGE;
      }
      boardString += "\n\r";
    }
    boardString += "\n\r    A  B  C  D  E  F  G  H\n\r";
    return boardString;
  }

  public String toBigString() {
    String boardString = ENDLINE+"\n\r";
    for (int i=0; i<40; i++) {
      boardString += RANK.get((i/5+1)+""+(i%5+1));
      boardString += WHITEEDGE;
      for (int j=0; j<8; j++) {
        if ((i/5)%2!=j%2) {boardString += toBigAscii(board[i/5][j],i,j) + BLACKEDGE;}
        else {boardString += toBigAscii(board[i/5][j],i,j) + WHITEEDGE;}
      }
      if (i%5==4) {
        if ((i/5)%2==0) {boardString += "\n\r"+MIDLINE+"\n\r";}
        else {
          if (i==39) {boardString += "\n\r"+ENDLINE;}
          else {boardString += "\n\r"+MIDLINE+"\n\r";}
        }
      } else {
        boardString += "\n\r";
      }
    }
    boardString += "\n\r"+FILE;
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
