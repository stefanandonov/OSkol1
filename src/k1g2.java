import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

class FilterClass implements FilenameFilter{
	
	long fileSize;
	
	FilterClass(long fs){
		fileSize=fs;
	}

	@Override
	public boolean accept(File dir, String name) {
		File f = new File(dir.getAbsolutePath()+"\\"+name);		
		return f.isDirectory() || (name.endsWith(".txt") && f.length()>fileSize);
	}
	
}

public class k1g2 {	
	static void copyLargeTxtFiles(String from, String to, long size) throws IOException{
		File source = new File(from);
		File destination = new File(to);
		
		if (!source.exists()){
			System.out.println("Ne postoi");
			return;
		}
		
		if (!destination.exists()){
			destination.mkdirs();
		}
		
		File[] files = source.listFiles(new FilterClass(size));
		
		for (File file : files){
			
			File dest = new File(to+"\\"+file.getName());
			
			if (!file.isDirectory()){
				dest.createNewFile();
				Files.copy(Paths.get(from+"\\"+file.getName()), new FileOutputStream(dest));
				
			}
			else {
				copyLargeTxtFiles(file.getAbsolutePath(),to,size);
			}
		}		
	}

	

	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		
		String from = sc.nextLine();
		String to = sc.nextLine();
		long size = sc.nextLong();
		
		try {
			copyLargeTxtFiles(from,to,size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

	}

}
