package components.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Files {
	private Files(){}
	
	public static void rename(File file, File destFile) {
		if(!file.exists())
			return;
		
		if(file.isFile()) {
			file.renameTo(destFile);
			return;
		}
		
		String filePath = file.getAbsolutePath();
		
		LinkedList<File> list = new LinkedList<>();
		list.add(file);
		
		File next = null;
		
		while(!list.isEmpty()) {
			next = list.getLast();
			if(!next.exists()) {
				list.removeLast();
				continue;
			}
			
			List<File> dirs = getDirecetorys(next.getAbsolutePath());
			
			if(dirs.isEmpty()) {
				List<File> files = getFiles(next.getAbsolutePath());
				for(File f : files) {
					String path = f.getAbsolutePath();
					path = path.substring(filePath.length(), path.length());
					
					new File(destFile, path).getParentFile().mkdirs();
					
					if(f.exists()) {
						if(!f.renameTo(new File(destFile, path)))
							return;
					}
				}
				
				list.removeLast();
				next.delete();
			} else
				list.addAll(dirs);
		}
	}
	
	public static void delete(File file) {
		if(!file.exists())
			return;
		
		if(file.isFile()) {
			file.delete();
			return;
		}
		
		LinkedList<File> list = new LinkedList<>();
		list.add(file);
		
		File next = null;
		
		while(!list.isEmpty()) {
			next = list.getLast();
			if(!next.exists()) {
				list.removeLast();
				continue;
			}
			
			List<File> dirs = getDirecetorys(next.getAbsolutePath());
			
			if(dirs.isEmpty()) {
				List<File> files = getFiles(next.getAbsolutePath());
				for(File f : files)
					if(f.exists())f.delete();
				
				list.removeLast();
				next.delete();
			} else
				list.addAll(dirs);
		}
	}
	
	public static List<File> getFiles(String pfad){
		List<File> file = new ArrayList<File>();
		
		File dir = new File(pfad);
		File[] files = dir.listFiles();
		if(files != null){
			for(File f : files){
				if(!f.isDirectory())
					file.add(f);
			}
		}
		
		return file;
	}
	
	public static List<File> getDirecetorys(String pfad){
		List<File> file = new ArrayList<File>();
		
		File dir = new File(pfad);
		File[] files = dir.listFiles();
		if(files != null){
			for(File f : files){
				if(f.isDirectory()){
					file.add(f);
				}
			}
		}
		
		return file;
	}
	
	public static List<File> getAllFiles(String pfad){
		List<File> file = new ArrayList<File>();
		
		File dir = new File(pfad);
		File[] files = dir.listFiles();
		if(files != null){
			for(File f : files)file.add(f);
		}
		
		return file;
	}
	
	public static void extractFile(InputStream inputStream, File outputFile) {
		outputFile.getParentFile().mkdirs();
		BufferedReader reader = null;
		
		try{
			outputFile.createNewFile();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			
        	String sCurrentLine;
        	while((sCurrentLine = reader.readLine()) != null){
        		sCurrentLine += "\n";
        		
    			try {
            	    java.nio.file.Files.write(Paths.get(outputFile.toURI()), sCurrentLine.getBytes(), StandardOpenOption.APPEND);
            	}catch (IOException e) {
            		e.printStackTrace();
            	}
        	}
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
        	if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static String readFile(String file){
        BufferedReader br = null;
        
        try{
        	String sCurrentLine;
        	br = new BufferedReader(new FileReader(file));

        	String s = null;        	
        	while((sCurrentLine = br.readLine()) != null){
        		if(s == null)
        			s = sCurrentLine;
        		else
        			s += "\n" + sCurrentLine;
        	}

        	return s;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
        		if(br != null)br.close();
        	}catch(IOException ex){}
        }
        
        return null;
	}
	
	public static void writeFile(String file, String text) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null)writer.close();
			} catch (IOException e) {}
		}
	}
	
	public static void writeBytesToFile(String pfad, byte[] bytes) {
		try {
			FileOutputStream writer = new FileOutputStream(pfad);
			writer.write(bytes);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readBytesFromFile(String pfad) {
		File file = new File(pfad);
		
		byte[] bytesArray = new byte[(int) file.length()]; 

		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytesArray);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bytesArray;
	}
	
	public static byte[] readAllBytesFromFile(String pfad) {
		try {
			byte[] bytes = java.nio.file.Files.readAllBytes(Paths.get(pfad));
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readFile(File file){
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        
        try{
        	String sCurrentLine;
        	br = new BufferedReader(new FileReader(file));
   	
        	while((sCurrentLine = br.readLine()) != null){
        		if(first){
        			first = false;
        			builder.append(sCurrentLine);
        		}
        		else builder.append("\n" + sCurrentLine);
        	}

        	return builder.toString();
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
        		if(br != null)br.close();
        	}catch(IOException ex){}
        }
        
        return null;
	}
}