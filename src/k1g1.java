import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class k1g1 {
	
	/* 
	 * Site tekstualni datoteki od imenikot from gi premestuva vo imenikot to
	 * Ako imenikot from ne postoi pecati "Ne postoi"
	 * Ako imenikot to ne postoi, go kreira.	 *   
	 */	
	
	public static void moveWritableTxtFiles(String from, String to) throws IOException {
		File source = new File(from);
		
		if (!source.exists()){
			System.out.println("Ne postoi");
			return;
		}
		
		File destination = new File(to);
		
		if (!destination.exists()){
			destination.mkdirs();
		}
		
		File[] files = source.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				File f = new File(name);
				String s = f.getName();
				return dir.isDirectory() || ( f.canWrite() && s.endsWith(".txt"));						
			}
		});
		
		for (File f : files){
			if (!f.isDirectory())
				f.renameTo(new File(destination.getAbsolutePath()+"\\"+f.getName()));
			else
				moveWritableTxtFiles(f.getAbsolutePath(),destination.getAbsolutePath());				
		}
			
		
	}
	
	/*
	 * Sodrzinata na datotekata source (koja ima ogromen broj na podatoci), sekoj od niv so ista dolzina,
	 * ja vcituva i ja vnesuva vo lista od niza od bajti.
	 */
	
	static void deserializeData(String source, List<byte[]> data, long elementLength) throws IOException{
		File izvor = new File(source);
		byte [] buffer = new byte [(int) elementLength];
		InputStream in = new FileInputStream(izvor);
		try{			
			//int readLen=0, leftToRead=(int) elementLength, offset=0;
			int i=0;
			while (true){
				Integer c = in.read();
				
				if (c==-1)
					break;
				buffer[i]=c.byteValue();
				
				
				if (i==elementLength-1){
					i=0;
					data.add(buffer);
					buffer=new byte[(int) elementLength];
					continue;
				}else {
					++i;
				}				
			}			
		}
		finally {
			if (in!=null){
				in.close();
			}
		}
		
		
	}

	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		
		String from = sc.nextLine();
		String to = sc.nextLine();
		
		moveWritableTxtFiles(from,to);
		
		List<byte[]> data = new ArrayList<>();
		
		String deserialize = sc.nextLine();
		deserializeData(deserialize,data,5);
		
		
		
		
		
		

	}

}
