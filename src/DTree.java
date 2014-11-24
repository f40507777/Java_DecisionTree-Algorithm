import java.io.*;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class DTree {
	static long time;
	private static String [][]data=new String[9][208];
	private static String [][]category=new String[208][];
	private static BufferedReader br;
	static DefaultMutableTreeNode DTree=new DefaultMutableTreeNode();
	static ArrayList<Integer> lastcat = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		clockstart();
		readData();
		Root();
		clockend();
	}
	public static void readData() throws IOException{
		String []path={"./data.txt","./labels.txt"};
		for(int i=0;i<2;i++){
			FileReader fr=new FileReader(path[i]);
			br = new BufferedReader(fr);
			String s;
			int count=0;
			while((s=br.readLine())!=null){
				String []strarray=s.split(",");
				if(i==0){
					data[count]=strarray;
				}else{
					category[count]=strarray;
				}
				count++;	
			}
		}
	}
	public static void Root(){
		for(int i=0;i<category.length-1;i++){
			lastcat.add(i);
		}
		int[] child=CountEntropy(207,0);
		DefaultMutableTreeNode Root=new DefaultMutableTreeNode(child[1]);
		DTree.getRoot();
		DTree=Root;
		lastcat.remove((int)child[1]);
		if(child[0]==0){
			System.out.println("Root="+DTree.getRoot());		
		}else{
			Control();
		}
	}
	public static void Control(){
		
		int parent= Integer.parseInt(DTree.getUserObject().toString());
		for(int i=0;i<category[parent].length;i++){
			int[] child=CountEntropy(parent,i);
			DefaultMutableTreeNode node=new DefaultMutableTreeNode(child[1]);
			DTree.add(node);
			lastcat.remove((int)child[1]);
			if(child[0]==0){
				printTreeset();
				return;
			}
			
		}
	}
	public static int[] CountEntropy(int parent,int child){
		double low=1,temp;
		int smallest[]=new int[2];
		for(int i=0;i<lastcat.size();i++){
			
			int [][]backentropy=new int[2][category[i].length];
			for(int j=0;j<data.length;j++){
				if(parent==207| category[parent][child].equals(data[j][parent])){
					int index=SearchKey(category[i],data[j][i]);
					backentropy[Integer.valueOf(data[j][207])][index]++;//Good or Bad
				}
			}
			if((temp=CalculatorEntroy(backentropy))<low){
				low=temp;
				smallest[0]=1;
				smallest[1]=i;
				
				if(low==0){
					smallest[0]=0;
					//break;
				}
			}

			System.out.println(i+"=	"+temp);
		}
		return smallest;
	}
	public static double CalculatorEntroy(int [][]entropydata){
		double Entroy=0,Entroytaltoal=0;
		for(int i=0;i<entropydata[0].length;i++){
			if(entropydata[0][i]==0|entropydata[1][i]==0){
				Entroy=0;
			}else{
				Entroy=(-(entropydata[0][i]/((float)entropydata[0][i]+entropydata[1][i])))*log(entropydata[0][i]/((float)entropydata[0][i]+entropydata[1][i]))
						-(entropydata[1][i]/((float)entropydata[0][i]+entropydata[1][i]))*log(entropydata[1][i]/((float)entropydata[0][i]+entropydata[1][i]));
			}
			Entroytaltoal=Entroytaltoal+(Entroy*(entropydata[0][i]+entropydata[1][i])/(float)data.length);
		}
		return Entroytaltoal;
	}
	public static int SearchKey(String []book,String keywoard){
		for(int i=0;i<book.length;i++){
			if(book[i].equals(keywoard)){
				return i;
			}
		}
		return -1;
	}
	public static void printTreeset(){
		DTree.getRoot();
		String level="-";
		int count=0;
		while(DTree.getUserObject()!=null){
			System.out.println("Root="+DTree);
			if( DTree.getChildCount()==0){
				return;
			}
			DTree=(DefaultMutableTreeNode) DTree.getChildAt(count);
			count++;
		}
	}

	public static double log(float value) {//自訂底為2的Log 
		return Math.log(value) / Math.log(2); 
	}
	public static void clockstart(){
		time=System.currentTimeMillis();//計時開始
	}
	public static void clockend(){
		System.out.println("執行耗時= "+(System.currentTimeMillis()-time)/1000f+" 秒 ");//結束的時間	
	}
}
