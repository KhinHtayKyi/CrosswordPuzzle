package contest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import name.Logic;
//import name.CrossWord;
import name.CrossWordSolver;
/**
 * 本クラスは、主催者側のプログラムとなります。
 * プログラム作成時の確認では、23 行目の new Logic() 部分を
 * 自分の作成したクラスに変更して、動作確認してください。
 * @author jdaita
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {

		// プログラムリスト
		List<ContestLogicInterface> al = new ArrayList<ContestLogicInterface>();
		//al.add(new Logic());
		
		
		
		al.add(new CrossWordSolver());
			String KOTAE[]={"モモンガ","コウモリ","キカンシヤ","クーガー","カメレオン"};
	
	
	int ProblemCount=5;		
	
	FilePathList filepn = new FilePathList();
	
	Iterator<ContestLogicInterface> iter = al.iterator();

		while (iter.hasNext()) {
			ContestLogicInterface cli = iter.next();
		
	
			String name = cli.getAuthorName();			
			String className = cli.getClass().getName();
			//String kotae = cli.execute();
			
			//System.out.println("■" + name + "  クラス名：" + className + "　答え：" + kotae);
			
			System.out.println("■" + name + "  クラス名：" + className+"\n");
			
			
			ProblemLoop:
			for (int i = 1; i <=ProblemCount; i++) {
			     String FolderPath= filepn.HOME+i+"/";
			    String TemplateFileName=FolderPath+filepn.TEMPLATE;
			    // System.out.println(FolderPath);
			    // System.out.println(TemplateFileName);
			     String TateFileName=FolderPath+filepn.TATE;
			    
			    // System.out.println(TateFileName);
			     String YokoFileName=FolderPath+filepn.YOKO;    
			    // System.out.println(YokoFileName);
			     
			     /*Starting the CrossWordSolver*/
			    long TemplateSize=0;
			 	long TateLine=0;
			 	long YokoLine=0; 
			 	
			   TemplateSize= cli.ReadTemplateSize(TemplateFileName);
			
			//System.out.println(TemplateSize);
			long [][]TemplateOfCrossWord=new long[(int)TemplateSize][(int)TemplateSize];
			TemplateOfCrossWord=cli.ReadTemplate(TemplateSize,TemplateFileName);
			TateLine=cli.TateSize(TateFileName);
			YokoLine=cli.YOKOSize(YokoFileName);
					
			int[][] TATEGRID=new int[(int)TateLine][5];
			TATEGRID=cli.TateGrid(TateLine,TemplateOfCrossWord);
			
			
			int[][] YOKOGRID=new int[(int)YokoLine][5];
			YOKOGRID=cli.YokoGrid(YokoLine,TemplateOfCrossWord);
			
			//Reading Tate.txt and store words at String array as descending order
			String[][] TateWords=new String[(int)TateLine][2];
			TateWords=cli.TateWordList(TateLine,TateFileName);
						
			
			//Reading Yoko.txt and store words at String array as descending order
		
			String[][] YokoWords=new String[(int)YokoLine][2];
			YokoWords=cli.YokoWordList(YokoLine,YokoFileName);			
			
			String Answer=null;
		//Answer=cli.CrossWordSolver(TemplateOfCrossWord,TATEGRID,TateWordLength,YOKOGRID,YokoWordLength,TateWords,YokoWords,TemplateFileName);
			Answer=cli.CrossWordSolver(TemplateOfCrossWord,TATEGRID,YOKOGRID,TateWords,YokoWords,TemplateFileName);

		
		if(KOTAE[i-1].equals(Answer))
		{
			System.out.println("答え：For Problem "+i+":\t"+Answer+"\n");
			continue;
		}
		else 
		{
			System.out.println("Your CrossWord Solution for Problem"+i+" is Wrong ");
			break ProblemLoop;
		}
			
			
		}
		
	}
}
}
