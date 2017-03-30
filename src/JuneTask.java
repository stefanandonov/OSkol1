import java.io.*;
import java.util.*;
public class JuneTask {
	
	static void manage(String in, String out) throws IOException{
		File source = new File (in);
		
		if (!source.exists()){
			System.out.println("ne postoi");
			return;
		}
		
		File destination = new File(out);
		
		if (destination.exists()){
			File [] files = destination.listFiles();
			for (File f : files)
				f.delete();
		}
		
		File [] files = source.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				File f = new File(dir,name);
				return dir.isDirectory() || f.getName().endsWith(".dat");
			}
			
		});
		
		for (File f : files){
			if (f.isDirectory()){
				manage(f.getAbsolutePath(),out);
			}
			else {
				if (f.canWrite()){
					System.out.println("pomestuvam" + f.getAbsolutePath());
					f.renameTo(new File (out+"\\"+f.getName()));
				}
				else if (!f.canWrite()){
					File res = new File("\\resources\\writable-content.txt");
					res.createNewFile();
					
					FileInputStream fis = new FileInputStream(f);
					FileOutputStream fos = new FileOutputStream(res);
					try {
						
						int c = fis.read();
						while (c!=-1){
							fos.write(c);
						}
						
					}
					finally{
						if (fis!=null)
							fis.close();
						if (fos!=null)
							fos.close();
					}
					
					System.out.println("dopisuvam "+f.getAbsolutePath());
					
				}
				else if (f.isHidden()){
					String path = f.getAbsolutePath();
					
					f.delete();
					
					System.out.println("zbunet sum "+path);
				}
			}
		}
	}
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String in = sc.nextLine();
		String out = sc.nextLine();
		
		try {
			manage(in,out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
