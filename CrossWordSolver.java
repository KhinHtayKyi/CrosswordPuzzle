package name;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.regex.Pattern;
import java.util.regex.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import contest.ContestLogicInterface;
import contest.FilePathList;


public class CrossWordSolver implements ContestLogicInterface {
	
	/*Implementing ReadTemplaeSize
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#ReadTemplateSize(java.lang.String)
	 * 
	 * This method is used to count total line numbers of N_01_template.txt.
	 * All CrossWord Template File for this contest is squared Matrix, We only count lines  for creating CrossWord Puzzle Board.
	 * 
	 */
	public long ReadTemplateSize(String FILENAME)
	{
		
		long lines=0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			
			lines=br.lines().count();
			br.close();	
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return lines;
		
	}
	


	/*Implementing ReadTemplate Abstract Method for  reading Template and identifying Black Squared and White Squared
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#ReadTemplate(long, java.lang.String)
	 * 
	 * Creating CrossWord Puzzle Template based on the size of Template.
	 * As unknown character is detected only at first character position strangely (May be: I don't know how to identify that character), I just read starting from second character for first line of Template.
	 * Other lines are read from starting from first character.
	 * 
	 * It assigns int value 0 for black squared character.
	 * It assigns int value 1 for white squared character and other displayed integer character 
	 * 
	 * 
	 */
		public long[][] ReadTemplate(long TemplateSize,String FILENAME){
			
				
			long [][] TemplateOfCrossWord=new long[(int)TemplateSize][(int)TemplateSize];
			int LineCount=0;
			int i;
			
			

			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

					String sCurrentLine;
					char uniBlackChar = '\u25A0';   //Defining Black Character
					char uniWhiteChar = '\u25A1';   //Defining White Character				
					
									
					while ((sCurrentLine = br.readLine()) != null) {				
						
						//Copying only string starting from 2nd position if it is first line of template.txt
						if(LineCount==0)
						{
							sCurrentLine=sCurrentLine.substring(1);
						}
						
						
						
						for(i=0;i<sCurrentLine.length();i++)
						{
							//Checking whitesquare or not
							if(sCurrentLine.charAt(i)==uniWhiteChar){
								
								TemplateOfCrossWord[LineCount][i]=1;								
							}
								
							//Checking blackSquare
							else if(sCurrentLine.charAt(i)==uniBlackChar){
								
								TemplateOfCrossWord[LineCount][i]=0;
													
							}
							//Checking integer value of template.txt for arranging answer
							else{
								TemplateOfCrossWord[LineCount][i]=1;						
											
							}																				
										
					}
						LineCount++;	

				} 
					
							
			
					
					br.close(); 
					
				}catch (IOException e) {
					e.printStackTrace();
				}
			
			
			return TemplateOfCrossWord;
			}
		
		
		/*Implementing TateSize Abstract Method for getting Size of YOKO
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#TateSize()
		 * 
		 * This method returns size of N_02_tate.txt.
		 * 
		 */
		
		 public long TateSize(String FILENAME){
			 long lines=0;
				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					
					lines=br.lines().count();

					
					
				br.close();	
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return lines;
						 
		 }


			/*Implementing YOKOSize Abstract Method for getting Size of YOKO
			 * (non-Javadoc)
			 * @see contest.ContestLogicInterface#YOKOSize(java.lang.String)
			 * 
			 * This method returns size of N_03_yoko.txt.
			 */
			
				
			 public long YOKOSize(String FILENAME){				 
			
					long lines=0;
					try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
						
					lines=br.lines().count();
					br.close();	
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					
					return lines;					
				 
			 }
		
		
		/*Implementing TateGRID Abstract Method for  reading Template and identifying TateGRID
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#TateGrid(long, long[][])
		 * 
		 * This method returns Tate Grid locations where we need to fill Tate Words.
		 * Return int array structure is as follows. (5 column)
		 * Column Index:	StartRowIndex:	EndRowIndex:	LengthToFill:	Mark:
		 * 
		 * 
		 */
		public int[][] TateGrid(long Tatesize,long[][] TemplateOfCrossWord){
						
					int [][] TateGrid=new int[(int)Tatesize][5];
					int GridIndex=0;
										
					int TemplateSize=TemplateOfCrossWord.length;
					
					int FillMark=0;  // for marking whether it has already filled or not at this Tate Grid position
					for(int j=0;j<TemplateOfCrossWord.length;j++)
					{
						int WordCountLength=0;
						int ColIndex=0;
						int StartRowIndex=0;
						int EndRowIndex=0;
						
						
						for(int i=0;i<(TemplateOfCrossWord.length)-1;i++)
												
					{
					  if((TemplateOfCrossWord[i][j]==1)&&(TemplateOfCrossWord[i+1][j]==1)){
						  
						  WordCountLength++;  
						  StartRowIndex=i;
						  ColIndex=j;
						  
						  for(i++;i<TemplateSize&&TemplateOfCrossWord[i][j]==1;i++)
						  {
							  WordCountLength++;
							  						  
						  }
						  
						  EndRowIndex=StartRowIndex+WordCountLength-1;
					 
						  				 
							TateGrid[GridIndex][0]=ColIndex;
							TateGrid[GridIndex][1]=StartRowIndex;
							TateGrid[GridIndex][2]=EndRowIndex;
							TateGrid[GridIndex][3]=WordCountLength;
							TateGrid[GridIndex][4]=FillMark;
							GridIndex++;
							 WordCountLength=0;
						 }
						  
						  
					  }
						  				  
						  			  
					}		
					
					
					return TateGrid;			
				 }
		
		
		/*Implementing YoKoGRID Abstract Method for  reading Template and identifying YokoGRID
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#YokoGrid(long, long[][])
		 * 
		 * This method returns Yoko Grid locations where we need to fill Yoko Words.
		 * Return int array structure is as follows. (5 columns)
		 * Row Index:	StartColumIndex:	EndColumnIndex:	LengthToFill:	Mark:
		 */
				public int[][] YokoGrid(long Yokosize,long[][] TemplateOfCrossWord){
							
							int [][] YokoGrid=new int[(int)Yokosize][5];
							int GridIndex=0;
							
							int TemplateSize=TemplateOfCrossWord.length;
							int FillMark=0;  // for marking whether it has already filled or not at this Yoko Grid positon
							for(int i=0;i<TemplateOfCrossWord.length;i++)
							{
								int WordCountLength=0;
								int RowIndex=0;
								int StartColIndex=0;
								int EndColIndex=0;
								
								
								for(int j=0;j<(TemplateOfCrossWord.length)-1;j++)
														
							{
									
							  if((TemplateOfCrossWord[i][j]==1)&&(TemplateOfCrossWord[i][j+1]==1)){
								  
								  WordCountLength++;  
								  StartColIndex=j;
								  RowIndex=i;
								  
								  for(j++;j<TemplateSize&&TemplateOfCrossWord[i][j]==1;j++)
								  {
									  WordCountLength++;
									  						  
								  }
								  
								  EndColIndex=StartColIndex+WordCountLength-1;
								 
								  				 
									YokoGrid[GridIndex][0]=RowIndex;
									YokoGrid[GridIndex][1]=StartColIndex;
									YokoGrid[GridIndex][2]=EndColIndex;
									YokoGrid[GridIndex][3]=WordCountLength;
									YokoGrid[GridIndex][4]=FillMark;
									GridIndex++;
								    WordCountLength=0;
									 
								 }
								  
								  
							  }
								  				  
								  			  
							}		
							
							
							return YokoGrid;			
						 }
				

					 
										
	/*Implementing abstract TateWordList method for reading N_02_tate.txt.
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#TateWordList(long, java.lang.String)
	 * 
	 * This method return String Array Tate Word List from N_02_tate.txt.
	 * Structure is as follows.(2 Columns)
	 * 
	 * TateWord:	Adding "V" for identifying whether that word has already used or not:
	 * 
	 */
	public String[][] TateWordList(long TateLine,String FILENAME){
		
		 String [][] TateWords=new String[(int)TateLine][2];
		 int LineCount=0;

			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

					String sCurrentLine;
																		
					
					while ((sCurrentLine = br.readLine()) != null) {	
						
						
						if(LineCount==0)
						{
							sCurrentLine=sCurrentLine.substring(1);
						}
						
				         TateWords[LineCount][0]=sCurrentLine.trim();
				         TateWords[LineCount][1]="V";  // V means Valid of this words
				    								
						LineCount++;
											
					}
						
						br.close();
						/*Arrays.sort(TateWords,new java.util.Comparator<String>(){
							@Override
							public int compare(String s1,String s2){
						return s2.length()-s1.length();
		           }
						});*/
					
						
			} 
					
										
			catch (IOException e) {
					e.printStackTrace();
				}
						
			return TateWords;
			}

	
		
	/*Implementing abstract TateWordList method for reading Yoko Words for N_03_yoko.txt.
	 * 	 * 
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#YokoWordList(long, java.lang.String)
	 * 
	 * This method return String Array Yoko Word List from N_03_yoko.txt.
	 * Structure is as follows.(2 Columns)
	 * 
	 * YokoWord:	Adding "V" for identifying whether that word has already used or not:
	 * 
	 */
		public String[][] YokoWordList(long YokoLine,String FILENAME){
			
			 String [][] YokoWords=new String[(int)YokoLine][2];
			 int LineCount=0;

				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

						String sCurrentLine;
																			
						
						while ((sCurrentLine = br.readLine()) != null) {								
							
							if(LineCount==0)
							{
								sCurrentLine=sCurrentLine.substring(1);
							}
							
					       YokoWords[LineCount][0]=sCurrentLine.trim();
					       YokoWords[LineCount][1]="V";  // V for Valid for this words
    																
							LineCount++;						
							
						}
							
							br.close();
							
					/*	Arrays.sort(YokoWords,new java.util.Comparator<String>(){
								@Override
								public int compare(String s1,String s2){
							return s2.length()-s1.length();
			           }
							});*/
							
				} 
						
				
				//System.out.println(LineCount);
			
										
				catch (IOException e) {
						e.printStackTrace();
					}
				
				
				return YokoWords;
				}
		
		
		
	/*Implementing FirstMaxGridLocation Abstract method for counting all longest Tate Grid Position.
	 * 	(non-Javadoc)
	 * @see contest.ContestLogicInterface#FirstMaxGridLocation(int[][])
	 * 
	 * This method return all the all longest Tate Grid Positions for choosing first TATE GRID Location.
	 * 
	 */
		public ArrayList FirstMaxGridLocation(int[][]TATEGRID){		
			
			ArrayList<Integer> FirstMaxGrid=new ArrayList<Integer>();
			int GridLength=TATEGRID.length;
			
				
	
			int Largest=0;
			
			for(int i=0;i<GridLength;i++)
			{
				if(TATEGRID[i][3]>Largest){
					Largest=TATEGRID[i][3];

				}
			}
			
			
			
			/*Find the repeated maximum Number*/
						
			for(int i=0;i<GridLength;i++)
			{
				if(TATEGRID[i][3]==Largest){
					
					FirstMaxGrid.add(i);			
					
				}
			}
			
			
		
			return FirstMaxGrid;
				}
			
			
				
		
		
	
	/*Implementing TateWordFit For selecting suitable TateWord from TateWordList. 
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#TateWordFit(char[], java.lang.String[][], java.lang.String[][])
	 * 
	 * This method returns "true" if selected TateWord has same length and not been used yet.
	 * If previous filled Tate Words at that TATE GRID position is less than 2, We will not choose that TATE GRID position.
	 * 
	 */
	 
		
	public boolean TateWordFit(char []FilledWord,String[][] TateWordChoice,String[][]TateWordList){
		

		if(TateWordChoice[0][0].length()!=FilledWord.length||(TateWordChoice[0][1]!="V")){
			
			return false;
		}
		int CountFilledWords=0;
		
		if(TateWordChoice[0][0].length()==FilledWord.length){
					
				for(int i=0;i<FilledWord.length;i++){		
					
					if(FilledWord[i]!=0&&(FilledWord[i]!=TateWordChoice[0][0].charAt(i)))
						
						return false;
					if((FilledWord[i]!=0))
						CountFilledWords++;
					
					
				}		
							
		}
		
		int countWords=0;
		for(int i=0;i<TateWordList.length;i++)
		{
			
			
			if(TateWordList[i][0].length()==TateWordChoice[0][0].length()&&(TateWordList[i][0].charAt(0)==TateWordChoice[0][0].charAt(0))){
							
					countWords++;
					
					
					if(countWords>=2&&CountFilledWords<=1)	{
						
						return false;
					}
					
				}
				
			}
			
		return true;
	}
		
	
	
	
	/* Implementing TateWordFit For selecting YokoWord. 
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#YokoWordFit(char[], java.lang.String[][], java.lang.String[][])
	 * 
	 * This method returns "true" if selected YokoWord has same length and not been used yet.
	 * If previous filled Yoko Words at that YOKO GRID position is less than 2, We will not choose that YOKO GRID position.
	 */
	 
		
	public boolean YokoWordFit(char []FilledYokoWord,String[][] YokoWordChoice,String[][]YokoWordList){
		
		if(YokoWordChoice[0][0].length()!=FilledYokoWord.length||(YokoWordChoice[0][1]!="V")){
			
			return false;
		}
		
			int CountFilledWords=0;   //if Filled Words is less than or equal 1 for same starting character,return false also
		
		if(YokoWordChoice[0][0].length()==FilledYokoWord.length){
					
				for(int i=0;i<FilledYokoWord.length;i++){		
					
					if(FilledYokoWord[i]!=0&&(FilledYokoWord[i]!=YokoWordChoice[0][0].charAt(i)))
						
						return false;
					
					/*Checking whether there are same length and same starting words in YOKOWord list
					 * If there are more than one word, just leave and not filled
					 * 
					 */
					if((FilledYokoWord[i]!=0))
						CountFilledWords++;
					
					
					
				}	
				
				int countWords=0;
				for(int i=0;i<YokoWordList.length;i++)
				{
					
					
					if(YokoWordList[i][0].length()==YokoWordChoice[0][0].length()&&(YokoWordList[i][0].charAt(0)==YokoWordChoice[0][0].charAt(0))){
						
						countWords++;
							
							if(countWords>=2&&CountFilledWords<=1)					
							return false;
						}
						
					}
							
		}
		
		
		//}
		
		return true;
	}
		
	/*Implementing TateFilledCharPickUp method for picking up filled char at TATE GRID position or YOKO GRID position.
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#TateFilledCharPickUp(int, int[][], char[][])
	 * 
	 * This method returns all previously filled characters at that selected TATE GridLocation for finding same words from TateWordList.
	 */
	
	 
	public char[] TateFilledCharPickUp(int GridLocation,int[][]TATEGRID,char [][]FilledCrossWord){
		
		int ColumnIndex=TATEGRID[GridLocation][0];
		int StartRowIndex=TATEGRID[GridLocation][1];
		int EndRowIndex=TATEGRID[GridLocation][2];
		int WordLength=TATEGRID[GridLocation][3];
		//TATEGRID[GridLocation][4]=1;    //Mark Tate Index as 1 for already used
		char []FilledWord=new char[WordLength];
		int index=0;
		
		//Deciding position of the filled words
		for(int i=StartRowIndex;i<=EndRowIndex;i++){
			
			if(FilledCrossWord[i][ColumnIndex]!=0){
				
				FilledWord[index]=FilledCrossWord[i][ColumnIndex];
				//System.out.println("There is mutual character at this Tate position");
				
			}
				
			/*else
			{
				
				
				//System.out.print("There is no character at this Tate position");
				
				
				
			}*/
			index++;
			
		}
				
		
		return FilledWord;
		
		
	}
	
/*Implementing YOKOFilledCharPickUp method * 
 * (non-Javadoc)
 * @see contest.ContestLogicInterface#YokoFilledCharPickUp(int, int[][], char[][])
 * 
 *This method returns all previously filled characters at that selected YOKO GridLocation for finding same words from YokoWordList.

 */
	
 
public char[] YokoFilledCharPickUp(int YOKOGridIndex,int[][]YOKOGRID,char [][]FilledCrossWord){
	
	int Yokoindex=0;
	
	int StartRow=YOKOGRID[YOKOGridIndex][0];
	int StarColumnIndex=YOKOGRID[YOKOGridIndex][1];
	int EndColumnIndex=YOKOGRID[YOKOGridIndex][2];
	int YokoWordLength=YOKOGRID[YOKOGridIndex][3];
	char []FilledYokoWord=new char[YokoWordLength];


	for(int j=StarColumnIndex;j<=EndColumnIndex;j++){
		
		if(FilledCrossWord[StartRow][j]!=0){
			
			FilledYokoWord[Yokoindex]=FilledCrossWord[StartRow][j];
			//System.out.println("There is mutual character at this YOKO position");
			
		}
			
		/*else
		{
					
			System.out.print("There is no character at this YOKO position");
			
		}*/
		Yokoindex++;
		
	}
		
	return FilledYokoWord;
		
		
	}
	
	
	
	
		
		/*Implementing FillGrid Abstract Method for filling the CorssWord
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#FillGrid(char[][], int, int[][], int[][], java.lang.String[][], java.lang.String[][])
		 * 
		 * 
		 * This method fills Tate words and yoko words for the selected GRID Position.
		 * 
		 */
		
	
	public boolean FillGrid(char[][] FilledCrossWord,int GridLocation,int[][]TATEGRID,int[][]YOKOGRID,String[][]TateWordList,String[][]YokoWordList) {	
			
			
			int ColumnIndex=TATEGRID[GridLocation][0];
			int StartRowIndex=TATEGRID[GridLocation][1];
			int EndRowIndex=TATEGRID[GridLocation][2];
			int WordLength=TATEGRID[GridLocation][3];
			
			
			
			/*
			 * Checking whether the characters at TATEGRID positions are already filled at FilledCorssWord board or Not 
			 * 
			 * For Starting TATEGRID column position, there is no problem
			 * 
			 * Starting from second onwards, We have to check there are filled words or not..
			 * 
			 * If there is filled words, find mutual words from TATEwords list and select that words to fill at that position
			 * 
			 * If there is no such words, return to FillGrid and start FillGrid again with new TATE GRID position.
			 * 
			 ** 
			 */
			
			
			/**
			 * Checking whether there are already filled character or not at FilledCorssWord of that TATEGRID POSITION
			 * 
			 */
					
		
			
			
		/*
		 * If FillFlag=false means there are not filled character at First maximum TATEGRID position:  Select TATE words which has the same length as TATEGRID length
		 * Or not, It means there are filled characters: Find words which has mutual words with Filled Characters
		 */	
		 
		
			
			char []FilledWord=new char[WordLength];
			FilledWord=TateFilledCharPickUp(GridLocation,TATEGRID,FilledCrossWord);
			
			
			/* Selecting TATE WORDS which has mutual character in the TateWords list 
			 * Call TateWordFit()
			 * If return is true, place that word on the filled Crossword
			 * If return is false,call TateWordFit() again for next word
			 */
			
			String TateWords=null;
			String [][] OptionalTateWord=new String[1][2];
			
			for(int i=0;i<TateWordList.length;i++){
				
				OptionalTateWord[0][0]=TateWordList[i][0];
				OptionalTateWord[0][1]=TateWordList[i][1];
				
				if(TateWordFit(FilledWord,OptionalTateWord,TateWordList)){
					
					TateWords=TateWordList[i][0];
					{
					TateWordList[i][1]="U";  //Assign TateWordList 2nd column to "U"
					break;
				}
				
			}
			}
			
				
			
			
		if(TateWords==null)
		
			return false;
		
			
			
			//Starting to Fill at CrossWordTemplate
			int charIndex=0;
			
			
			for(int i=StartRowIndex;i<=EndRowIndex;i++)
			{
			FilledCrossWord[i][ColumnIndex]=TateWords.charAt(charIndex);
				charIndex++;
			}
			
			//Filling Grid to one
			TATEGRID[GridLocation][4]=1;
			
			
			//Displaying TemplateOfCorssWord
			/*for(int i=0;i<FilledCrossWord.length;i++)
			{
				for(int j=0;j<FilledCrossWord.length;j++)
					
				{
					System.out.println("XCor is"+i+"YCor is"+j);
					System.out.println("After Filling First line"+FilledCrossWord[i][j]);
				
				}
			}
			
			*/
			int YOKOGridIndex=0;
			
			for(int i=StartRowIndex;i<=EndRowIndex;i++)
			{
				
				YOKOGridIndex=ReadYokoGrid(i,ColumnIndex,YOKOGRID,FilledCrossWord.length);
				
				if(YOKOGridIndex!=-1){
										
					
					
					int StartRow=YOKOGRID[YOKOGridIndex][0];
					int StarColumnIndex=YOKOGRID[YOKOGridIndex][1];
					int EndColumnIndex=YOKOGRID[YOKOGridIndex][2];
					int YokoWordLength=YOKOGRID[YOKOGridIndex][3];
					char []FilledYokoWord=new char[YokoWordLength];
					FilledYokoWord=YokoFilledCharPickUp(YOKOGridIndex,YOKOGRID,FilledCrossWord);

					
					
					/* Selecting Yoko WORDS which has mutual character in the YokoWords list 
					 * Call YokoWordFit()
					 * If return is true, place that word on the filled Crossword
					 * If return is false,call YokoWordFit() again for next word
					 */
					
					//char[] SelectedWords=null;
					
					String YokoWords=null;
					String [][] OptionalYokoWord=new String[1][2];
					int YokoCharIndex=0;
					
					for(int k=0;k<YokoWordList.length;k++){
						OptionalYokoWord[0][0]=YokoWordList[k][0];
						OptionalYokoWord[0][1]=YokoWordList[k][1];
						
						if(YokoWordFit(FilledYokoWord,OptionalYokoWord,YokoWordList)){
							YokoWords=YokoWordList[k][0];
							YokoWordList[k][1]="U";
							break;
						}
						
					}
					
					 if(YokoWords!=null){
					//Filling into the Template For YokoWords
					
					for(int l=StarColumnIndex;l<=EndColumnIndex;l++)
					{
						
						FilledCrossWord[StartRow][l]=YokoWords.charAt(YokoCharIndex);
						YokoCharIndex++;
					}
					YOKOGRID[YOKOGridIndex][4]=1;
				}
					 
							
				//else
					//System.out.println("NO Intersection OR Already Filled Slot at this Yoko Position");
				
				
				}
			}
			
			
			
			
			/*
			 * 
			 * Finding next starting point again using first selected location
			 * 
			 * 
			 */
			int NextTateGridXY=-1;
			int NextLeftTateGridXY=-1;
			
			
			
            NextTateGridXY=FindNextXY(GridLocation,TATEGRID,TateWordList,FilledCrossWord);    
		
			if(NextTateGridXY!=-1){
			
			     
                 FillGrid(FilledCrossWord,NextTateGridXY,TATEGRID,YOKOGRID,TateWordList,YokoWordList);
						
			
			}
			
			
			
			/* Checking left handed side TateGrid for filling
			 * 
			 */
			NextLeftTateGridXY=FindLeftNextXY(GridLocation,TATEGRID,TateWordList,FilledCrossWord);    
				 
					if(NextLeftTateGridXY!=-1){
					
					
		           
		           FillGrid(FilledCrossWord,NextLeftTateGridXY,TATEGRID,YOKOGRID,TateWordList,YokoWordList);
		            	
								
					
				}
		
					
					return true;
		}
	
	
	/*Implementing ReadYokoGrid abstract method for finding index of YokoGrid
	 * This method return index of YokoGrid for filling at that location
	 * if there is no intersection, already filled or no need to fill, return -1
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#ReadYokoGrid(int, int, int[][], int)
	 */
		
	public int ReadYokoGrid(int XCor,int YCor,int [][]YOKOGRID,int FilledCrossWordSize){
		
		//int YOKOGridLocation=0;
		
		for(int i=0;i<YOKOGRID.length;i++){
			
		if(YOKOGRID[i][0]==XCor&&YOKOGRID[i][4]!=1)
		{
			if(YOKOGRID[i][1]==YCor)
				return i;
			
			if(YOKOGRID[i][2]==YCor)
			return i;
			
			if(YCor>=YOKOGRID[i][1]&&YCor<=YOKOGRID[i][2])
				return i;
			
		}
		}
		
		return -1;
			
			
			
		}
		
		
		/*Implementing the FindLeftNextXY abstract method for finding next Starting point XY.
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#FindNextXY(int, int[][], char[][])
		 * 
		 * This method finds all left handed TATE Grid position based on first longest TATE GRID position.
		 */
			
			//Previous Correct Method
			public int FindLeftNextXY(int GridLocation,int [][]TATEGRID,String[][] TateWordList,char [][]FilledCrossWord){
				int NextStartingXY=-1;
				
				int PreviousColumnIndex=TATEGRID[GridLocation][0];
				int FindNextTateLocation=--PreviousColumnIndex;
				
				
				
			
				MainLoop:for(int j=FindNextTateLocation;j>=0;j--){	
					
				
			for(int i=0;i<TATEGRID.length;i++){
				
						
					if(TATEGRID[i][0]==j&&(TATEGRID[i][4])==0)
				     
						
					{ 
						//FINDING MUTUAL WORDS THAT WE ALREADY FILLED
						//CALL METHOD FOR FINDING THAT WE HAVE MUTUAL WORDS
						
					   if(FindTATEvalid(i,TATEGRID,TateWordList,FilledCrossWord)){
						   
						   NextStartingXY=i;
						  break MainLoop;
						 
					   }
					   					   
					}		
					
			}
				}
				
				
				
				return NextStartingXY;
			}
		
		
		
		
	/*Implementing the FindNextXY abstract method for finding next right Starting Tate point XY
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#FindNextXY(int, int[][], char[][])
	 * 
	 * This method finds all right handed TATE Grid position based on first longest TATE GRID position
	 */
		
		//Previous Correct Method
		public int FindNextXY(int GridLocation,int [][]TATEGRID,String[][] TateWordList,char [][]FilledCrossWord){
			int NextStartingXY=-1;
			
			int PreviousColumnIndex=TATEGRID[GridLocation][0];
						
			MainLoop:for(int j=PreviousColumnIndex;j<FilledCrossWord.length;j++){
				
				
		for(int i=0;i<TATEGRID.length;i++){
			
		
				if(TATEGRID[i][0]==j&&(TATEGRID[i][4])==0)
			     
					
				{ 
					
					//FINDING MUTUAL WORDS THAT WE ALREADY FILLED
					//CALL METHOD FOR FINDING THAT WE HAVE MUTUAL WORDS
					
				    				  
				   if(FindTATEvalid(i,TATEGRID,TateWordList,FilledCrossWord)){
					   
					   NextStartingXY=i;
					  break MainLoop;
					  
				   }
				   
				   
				}
				
					
		}
			}
			
			
			
			return NextStartingXY;
		}
		

		/* Finding That TateIndex is suitable to start again.
		 * 
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#FindTATEvalid(int, char[][])
		 */
		public boolean  FindTATEvalid(int TATEGRIDindex,int[][] TATEGRID,String [][]TateWordList,char[][]FilledCrossWord){
			
			
			//TATEGRIDindex=1;			
			int ColumnIndex=TATEGRID[TATEGRIDindex][0];
			int StartRowIndex=TATEGRID[TATEGRIDindex][1];
			int EndRowIndex=TATEGRID[TATEGRIDindex][2];
			int WordLength=TATEGRID[TATEGRIDindex][3];
			int valid=TATEGRID[TATEGRIDindex][4];
			
				
			char []FilledWord=new char[WordLength];
			FilledWord=TateFilledCharPickUp(TATEGRIDindex,TATEGRID,FilledCrossWord);
			
			int index=0;
			
			//Deciding position of the filled words
			for(int i=StartRowIndex;i<=EndRowIndex;i++){
				
				if(FilledCrossWord[i][ColumnIndex]!=0){
					
					//System.out.println("There is mutual character at this position For Second Tate Position");
					index++;
					if(index>=2)
					return true;
					
				}
				//else
					//System.out.print("There is no character at this second time Tate position");
			}
			
					
			
			String [][] OptionalTateWord=new String[1][2];
			
			for(int i=0;i<TateWordList.length;i++){
				
				OptionalTateWord[0][0]=TateWordList[i][0];
				OptionalTateWord[0][1]=TateWordList[i][1];
				
				if(TateWordFit(FilledWord,OptionalTateWord,TateWordList)){
					return true;
			}
			}
				
					
				return false;
				
			}
			
		
		
		
		/*Implementing whether all TATEGRID and YOKOGRID are equal to 1:
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#Solution(int[][])
		 * 
		 * This method checks whether all Tate Grid position and yoko Grid position has already been filled.
		 */
		public boolean Solution(int[][] GRIDvalue){
			
			 for(int i=0;i<GRIDvalue.length;i++) 
				 if(GRIDvalue[i][4]!=1)
					 return false;
			    return true;
			
		}
		
	/*Implementing reintilization for Mark Filled.
	 * (non-Javadoc)
	 * @see contest.ContestLogicInterface#Reinitilization(int[][])
	 * 
	 * This method reset Mark column of TATEGRID Or YOKOGRID Position
	 */
		
		public void Reinitilization(int[][]GRIDvalue){
			for(int i=0;i<GRIDvalue.length;i++)
				GRIDvalue[i][4]=0;
			
		}
		
		/*Implementing reintilization for WordList to Unused again.
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#ReWordList(java.lang.String[][])
		 * 
		 * This method resets second column of TateWordList or YokoWordList to "V" 
		 */
		
		 
		public void ReWordList(String[][]WordList){
			for(int i=0;i<WordList.length;i++)
				WordList[i][1]="V";
			
		}
		
		
			
		

		/*Implementing PickAnswerAbstract Method for reading Template and identifying Number of Answer Location and return answer.
		 * 
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#ReadAnswerLocation(long)
		 */
		public String PickAnswer(long TemplateSize,char[][]FilledCrossWord,String FILENAME){
		
		
				int LineCount=0;	
				
				ArrayList<Integer> AnswerIndex=new ArrayList<Integer>();
				

				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

						String sCurrentLine;
						char uniBlackChar = '\u25A0';   //Defining Black Characater
						char uniWhiteChar = '\u25A1';   //Defining White Characater
										
						
						
						while ((sCurrentLine = br.readLine()) != null) {				
							
							//Copying only string starting from 2nd position if it is first line of template.txt
							if(LineCount==0)
							{
								sCurrentLine=sCurrentLine.substring(1);
							}
							
														
							for(int i=0;i<sCurrentLine.length();i++)
							{
								//Checking whitesquare or not
								if(sCurrentLine.charAt(i)!=uniWhiteChar&&sCurrentLine.charAt(i)!=uniBlackChar){
									
									//First represent Row Index
									AnswerIndex.add(LineCount);
									
									//Second Column is for column index
									AnswerIndex.add(i);
									//Third is for storing integer value of Template.txt
									AnswerIndex.add(Character.getNumericValue(sCurrentLine.charAt(i)));
														
																					
								}	
									
						}
							LineCount++;
								
					} 
										
						
						br.close(); 
						
					}catch (IOException e) {
						e.printStackTrace();
					}
				//picking 
				
				
				int AnswerCount=AnswerIndex.size()/3;
				
				
				
				 char[] kotae=new char[AnswerCount];
				 int[][]IndexLocation=new int[AnswerCount][3];
				 
				 int RowIndex=0;
				 int ColumnIndex=0;
				 int index=0;
				 int AnswerOrder;

				for(int j=0;j<AnswerIndex.size();j+=3)
				{
					
					
					RowIndex=(int)AnswerIndex.get(j);
					ColumnIndex=(int)AnswerIndex.get(j+1);
					AnswerOrder=(int)AnswerIndex.get(j+2);
					
					IndexLocation[index][0]=RowIndex;
					IndexLocation[index][1]=ColumnIndex;
					IndexLocation[index][2]=AnswerOrder;
					
					
					//kotae[index]=FilledCrossWord[RowIndex][ColumnIndex];
					//System.out.println("Kotae is"+kotae[index]);
					index++;
				}
				
				 
/*Sorting AnswerLocation*/
					int[][] temp=new int[1][3];
					for(int i=0;i<AnswerCount;i++){
						for(int j=i+1;j<AnswerCount;j++){
							if(IndexLocation[i][2]>IndexLocation[j][2]){
								for(int k=0;k<3;k++){
									temp[0][k]=IndexLocation[i][k];
									IndexLocation[i][k]=IndexLocation[j][k];
									IndexLocation[j][k]=temp[0][k];
									
								}
							}
								
								
								
							}
						}
					
				/*	
					System.out.println("Ascending Order");
					for(int i=0;i<AnswerCount;i++){
						for(int j=0;j<3;j++)
							System.out.println(IndexLocation[i][j]);
					}
						
		*/
					
					
/*Picking the Answer words*/
					
					for(int i=0;i<AnswerCount;i++){
						RowIndex=IndexLocation[i][0];
						ColumnIndex=IndexLocation[i][1];
						
						kotae[i]=FilledCrossWord[RowIndex][ColumnIndex];
						//System.out.println("Kotae is"+kotae[i]);
					}
					
			/*	
			 * Creating String to return
			 */
				
				String FinalAnswer=new String(kotae);
				//System.out.println(FinalAnswer);
								
				return FinalAnswer;				
	}
				
		
		
			
		/*Implementing Abstract CrossWordSolver method for solving CrossWord Puzzle automatically	
		 * 
		 * (non-Javadoc)
		 * @see contest.ContestLogicInterface#CrossWordSolver(long[][], int[][], int[][], java.lang.String[][], java.lang.String[][], java.lang.String)
		 */
		
		public String CrossWordSolver(long [][]TemplateOfCrossWord,int[][] TATEGRID,int[][] YOKOGRID,String[][] TateWords,String[][] YokoWords,String FileName){
						
			ArrayList<Integer> FirstMaxGrid=new ArrayList<Integer>();
			String FinalAnswer=null;		
				
			FirstMaxGrid=FirstMaxGridLocation(TATEGRID);
			int [] MaxGrid=new int[FirstMaxGrid.size()];
			
			int count=0;
			for(Integer n: FirstMaxGrid)
			{
				MaxGrid[count++]=n.intValue();
			}
			/*
			for(int i=0;i<FirstMaxGrid.size();i++)	
			{
			//System.out.println("Maximum Tate Grid"+FirstMaxGrid.get(i));
			System.out.println("Row Inxex from Tate Grid"+TATEGRID[MaxGrid[i]][0]);
					//return FirstGridLocation;
			}	
			System.out.println("Row Inxex from Tate Grid"+MaxGrid[0]);
			*/
			
			MainLoop:for(int i=0;i<FirstMaxGrid.size();i++)	
			{
			int FirstGridLocation=MaxGrid[i];
			char[][]FilledCrossWord=new char[TemplateOfCrossWord.length][TemplateOfCrossWord.length];		
			
			
		FillGrid(FilledCrossWord,FirstGridLocation,TATEGRID,YOKOGRID,TateWords,YokoWords);
		if(Solution(TATEGRID)&&Solution(YOKOGRID)){
			//if(Solution(YOKOGRID)){
			System.out.println("Solving Finished Successfully");
			FinalAnswer=PickAnswer(TemplateOfCrossWord.length,FilledCrossWord, FileName);
			break MainLoop;
			}
			

			
			else
			{
				Reinitilization(TATEGRID);
				Reinitilization(YOKOGRID);
				ReWordList(TateWords);
				ReWordList(YokoWords);
				
				System.out.println("Starting Point XY postion can not solve CrossWord Puzzle correctly: Trying Again After Choosing New XY");
		
			}
			}
			
			return FinalAnswer;
	}
		
		
		
	
	
	public String execute() throws IOException {
		
		return "コウモリ";
	}

	public String getAuthorName() {

		//return "代田　淳平";
		return "KHIN HTAY KYI (NUT)";
	}


}
