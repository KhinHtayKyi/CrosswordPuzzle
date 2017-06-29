package contest;

import java.io.IOException;
import java.util.*;


/**
 * コンテスト参加者は、当該インターフェースを implements してください。
 * @author jdaita
 */
public interface ContestLogicInterface {

	/**
	 * 参加者の名前を返却してください。
	 * プログラミングコンテスト時に誰のプログラムか識別するために利用します。
	 * 性能測定時には、当メソッドを呼び出すことはありません。
	 *
	 * 例）代田　淳平
	 *
	 * @return 名前 
	 */
	public long ReadTemplateSize(String FileName);
	public long[][] ReadTemplate(long TemplateSize,String FileName);
	public long TateSize(String FileName);
	public long YOKOSize(String FileName);	
	public int[][] TateGrid(long Tatesize,long[][] TemplateOfCrossWord);	
	public int[][] YokoGrid(long Yokosize,long[][] TemplateOfCrossWord);	
	public String[][] TateWordList(long TateLine,String FileName);			
	public String[][] YokoWordList(long YokoLine,String FileName);	
	
	
	
	//Abstract methods for solving CrossWord Puzzle automatically
		
	public String CrossWordSolver(long [][]TemplateOfCrossWord,int[][] TATEGRID,int[][] YOKOGRID,String[][] TateWords,String[][] YokoWords,String FileName);	
	public ArrayList FirstMaxGridLocation(int[][]TATEGRID);	
	public boolean FillGrid(char[][] FilledCrossWord,int GridLocation,int[][]TATEGRID,int[][]YOKOGRID,String[][]TateWordList,String[][]YokoWordList);
	public char[] TateFilledCharPickUp(int GridLocation,int[][] TATEGRID,char [][]FilledCrossWord);
	public char[] YokoFilledCharPickUp(int GridLocation,int[][]TATEGRID,char [][]FilledCrossWord);
	public boolean TateWordFit(char []FilledWord,String[][] TateWordChoice,String[][]TateWordList);
	public boolean YokoWordFit(char []FilledYokoWord,String[][] YokoWordChoice,String[][]YokoWordList);
	
			
//Finding the correct next starting point XY from TATEGRID 
		public int ReadYokoGrid(int XCor,int YCor,int [][]YOKOGRID,int FilledCrossWordSize);	
		public int FindNextXY(int GridLocation,int [][]TATEGRID,String[][]TateWordList,char [][]FilledCrossWord);			
		public int FindLeftNextXY(int GridLocation,int [][]TATEGRID,String[][] TateWordList,char [][]FilledCrossWord);		
		public boolean  FindTATEvalid(int TATEGRIDindex,int[][] TATEGRID,String [][]TateWordList,char[][]FilledCrossWord);
		public boolean Solution(int[][]GRIDvalue);
		public void Reinitilization(int[][]GRIDvalue);			 
		public void ReWordList(String[][]WordList);
		
		
	
	//Abstract method for finding Answer From FilledCrossWord
	public String PickAnswer(long TemplateSize,char[][]FilledCrossWord,String FileName);	
	public String getAuthorName();


	/**
	 * 主催者側のプログラムから当メソッドを呼び出します。
	 * 答えを返却してください。
	 * @return 答え
	 */
	public String execute() throws IOException;
}
