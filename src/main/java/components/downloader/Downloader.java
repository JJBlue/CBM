package components.downloader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Downloader {
	private Downloader() {}
	
	public static void delete(File file) {
		if(file == null || !file.exists())return;
		if(file.isFile()) {
			file.delete();
			return;
		}
		
		LinkedList<File> dfiles = new LinkedList<>();
		dfiles.add(file);
		
		while(!dfiles.isEmpty()) {
			File first = dfiles.getFirst();
			
			if(first.isDirectory()) {
				boolean addedSomething = false;
				
				for(File f : first.listFiles()) {
					if(!addedSomething)addedSomething = true;
					dfiles.addFirst(f);
				}
				
				if(addedSomething)
					continue;
			}
			
			dfiles.remove(first);
			first.delete();
		}
	}
	
	public static void copyAllFiles(File f, String dest) throws IOException{
		if(f == null || !f.exists())return;
		
		if(f.isFile())
			copy(f, new File(dest +"/"+ f.getName()));
		
		LinkedList<File> cfiles = new LinkedList<>();
		cfiles.add(f);
		int substringlength = f.getAbsolutePath().length();
		
		while(!cfiles.isEmpty()) {
			File first = cfiles.getFirst();
			
			if(first.isDirectory()) {
				first.mkdirs();
				cfiles.addAll(Arrays.asList(first.listFiles()));
			}
			else
				copy(first, new File(dest + "/" + first.getAbsolutePath().substring(substringlength)));
			
			cfiles.remove(first);
		}
	}
	
	public static void copy(String srFile, String dtFile) throws FileNotFoundException, IOException{
		copy(new File(srFile), new File(dtFile));
	}
	
	public static void copy(File srFile, File destFile) throws FileNotFoundException, IOException {
		InputStream in = new FileInputStream(srFile);		
		OutputStream out = new FileOutputStream(destFile);
		
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0)
			out.write(buf, 0, len);
		
		in.close();
		out.close();
	}
	
	public static void unzip(File zipFile, File destFile) throws IOException{
		unzip(zipFile, destFile, -1, null, null);
	}
	
	public static void unzip(File zipFile, File destFile, int BUFFER, OutputWriter writer) throws IOException{
		unzip(zipFile, destFile, BUFFER, writer, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static void unzip(File zipFile, File destFile, int BUFFER, OutputWriter writer, UnzipFile unzipfile) throws IOException{
		if(BUFFER <= 0)
			BUFFER = 2048;
			
		if(zipFile != null && destFile != null && zipFile.exists()) {
			ZipFile zipfile = new ZipFile(zipFile);
			
			try {
				Enumeration e = zipfile.entries();
				destFile.mkdir();
				
				while(e.hasMoreElements()){
					final ZipEntry entry = (ZipEntry) e.nextElement();
					if(writer != null)
						writer.write(destFile.getAbsolutePath() + ": Extracting: " + entry);
					
					File destEntry = unzipfile != null ? unzipfile.getdestFile(destFile.getAbsolutePath(), entry.getName(), entry.isDirectory()) : new File(destFile.getAbsolutePath() + "/" + entry.getName());
					if(destEntry == null) continue;
					destEntry.getParentFile().mkdirs();
					
					if(entry.isDirectory())
						destEntry.mkdir();
					else{
						destEntry.createNewFile();
						BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
						
						int count;
						byte[] data = new byte[BUFFER];
						
						FileOutputStream fos = new FileOutputStream(destEntry);
						BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
						
						try {
							while ((count = is.read(data, 0, BUFFER)) != -1)
								dest.write(data, 0, count);
						} catch(Exception e2) {
							throw e2;
						} finally {
							dest.flush();
							dest.close();
							is.close();
						}
					}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				zipfile.close();
			}
		}
	}
	
	public static void downloadFile(String URL, File file, int BUFFER, OutputWriter writer) throws MalformedURLException, IOException{
		downloadFile(URL, (connection, type) -> file, BUFFER, writer);
	}
	
	public static DownloaderResult downloadFile(String URL, getFileRunnable getFile, int BUFFER, OutputWriter writer) throws MalformedURLException, IOException {
		if(getFile == null) return null;
		
		long waiting = System.currentTimeMillis();
		
		URL url = new URL(URL);
		URLConnection conn = url.openConnection();
		conn.setDefaultUseCaches(false);
		
		long max = conn.getContentLengthLong();
		String type = conn.getContentType();
		InputStream is = conn.getInputStream();
		
		File file = getFile.getFile(conn, type);
		if(file == null) return null;
		
		if(writer != null) {
			if(max >= 0)
				writer.write(file.getAbsolutePath() + ": Downloding file... Update Size(compressed): "+ max +" Bytes");
			else
				writer.write("Downloading file...\nLength is unknown");
		}
		
		file.getParentFile().mkdirs();
		BufferedOutputStream fOut = new BufferedOutputStream(new FileOutputStream(file));
		byte[] buffer = BUFFER > 0 ? new byte[BUFFER] : new byte[32 * 1024];
		int bytesRead = 0;
		int in = 0;
		while ((bytesRead = is.read(buffer)) != -1) {
			in += bytesRead;
			fOut.write(buffer, 0, bytesRead);
			
			if(max != conn.getContentLengthLong()) {
				max = conn.getContentLengthLong();
				writer.write("\n" + file.getAbsolutePath() + ": Downloding file... Update Size(compressed): "+ max +" Bytes");
			}
			
			if(System.currentTimeMillis() - waiting >= 3000){
				if(writer != null)
					writer.write("\n" + file.getAbsolutePath() + ": Downloaded until now.... " + in + " bytes");	
				
				waiting = System.currentTimeMillis();
			}
		}
		fOut.flush();
		fOut.close();
		is.close();
		
		if(writer != null)
			writer.write("\n" + file.getAbsolutePath() + ": Download Complete!");
		
		return new DownloaderResult(file, type, max);
	}

	public static String getString(String sURL) {
		try {
			URL url = new URL(sURL);
			
			URLConnection request = url.openConnection();
	        request.connect();

	        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
	        StringBuilder builder = new StringBuilder();
	        
	        String line = null;
	        while((line = reader.readLine()) != null) {
	        	builder.append("\n").append(line);
	        }
	        
	        return builder.toString();
		} catch (IOException e) {
		}

		return null;
	}
}