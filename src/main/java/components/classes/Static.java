package components.classes;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Static {
	private Static(){}
	
	public static String getJarPath(){
		final CodeSource source = Static.class.getProtectionDomain().getCodeSource();
		if (source != null) {
			try {
				String quelle = source.getLocation().toURI().getPath();
				
				int idx = quelle.lastIndexOf('/', quelle.length());
				quelle = quelle.substring(0, idx);
				
				return quelle;
			} catch (URISyntaxException e) {
			}
		}
		return "";
	}
	
	public static void setUIFont(FontUIResource font) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object fontUIResource = UIManager.get(key);
			if(fontUIResource instanceof FontUIResource)UIManager.put(key, font);
		}
	}
	
	public static String getMouseText(int mouse) {
		if(mouse == MouseEvent.BUTTON1)return "Linke Mousetaste";
		else if(mouse == MouseEvent.BUTTON2)return "Mausrad";
		else if(mouse == MouseEvent.BUTTON3)return "Rechte Maustaste";
		return "Error";
	}
	
	public static String replaceString(String main, String insert, int start) {
		if(main.length() == 0)return insert;
		
		if(start < main.length()) {
			int length = insert.length();
			String tmp = "";
			if(start != 0)tmp = main.substring(0, start - 1);
			tmp += insert;
			if(start + length < main.length())tmp += main.substring(start + length, main.length());
			return tmp;
		}else {
			int space = start - main.length();
			for(int i = 0; i < space; i++)main += " ";
			main += insert;
			return main;
		}
	}
	
	private static MessageDigest md = null;
	public static String md5(String message) {
		if(message == null) return "";
		
		if(md == null) {
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
		if(md != null) {
			md.update(message.getBytes());
			return new BigInteger(1,md.digest()).toString(16);
		}
		return "";
	}
	
	public static String sha_512(String input) {
		if(input == null) return null;
		
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return null;
    }
	
	public static boolean isHeigherVersion(String v1, String v2) {
		if(v1.contains("*") || v2.contains("*")) {
			String[] splitV1 = v1.split("\\.");
			String[] splitV2 = v2.split("\\.");
			int min = Math.min(splitV1.length, splitV2.length);
			
			String ver1 = "";
			String ver2 = "";
			
			for(int i = 0; i < min; i++) {
				if(ver1.length() == 0)ver1 = splitV1[i];
				else ver1 += "." + splitV1[i];
				
				if(ver2.length() == 0)ver2 = splitV2[i];
				else ver2 += "." + splitV2[i];
				
				System.out.println(ver1 + " " + v2);
				
				if((ver1 + ".*").equals(v2) || (ver2 + ".*").equals(v1))return false;
			}
		
			if(v1.compareTo(v2) < 0)return true;
		}
		else if(v1.compareTo(v2) < 0)return true;
		
		return false;
	}
	
	public static boolean compareVersion(String v1, String v2) {
		if(v1.equals(v2))return true;
		else if(v1.contains("*") || v2.contains("*")) {
			String[] splitV1 = v1.split("\\.");
			String[] splitV2 = v2.split("\\.");
			int min = Math.min(splitV1.length, splitV2.length);
			
			String ver1 = "";
			String ver2 = "";
			
			for(int i = 0; i < min; i++) {
				if(ver1.length() == 0)ver1 = splitV1[i];
				else ver1 += "." + splitV1[i];
				
				if(ver2.length() == 0)ver2 = splitV2[i];
				else ver2 += "." + splitV2[i];
				
				if((ver1 + ".*").equals(v2) || (ver2 + ".*").equals(v1))return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	public static int isForOtherWord(String w1, String w2) {
		int l1 = 0;
		int l2 = 0;
		int maxLength = Math.max(w1.length(), w2.length());
		
		for(int i = 0; i < maxLength; i++) {
			char c1 = Character.toLowerCase(w1.charAt(i));
			char c2 = Character.toLowerCase(w2.charAt(i));
			
			if(c1 == c2)continue;
			
			for(int y = 0; y < 2; y++) {
				int value;
				switch(c1) {
					case '!':
						value = 1;
						break;
					case '"':
						value = 2;
						break;
					case '§':
						value = 3;
						break;
					case '$':
						value = 4;
						break;
					case '%':
						value = 5;
						break;
					case '&':
						value = 6;
						break;
					case '/':
						value = 7;
						break;
					case '(':
						value = 8;
						break;
					case ')':
						value = 9;
						break;
					case '=':
						value = 10;
						break;
					case '?':
						value = 11;
						break;
					case '²':
						value = 12;
						break;
					case '\\':
						value = 13;
						break;
					case '³':
						value = 14;
						break;
					case '}':
						value = 15;
						break;
					case ']':
						value = 16;
						break;
					case '[':
						value = 17;
						break;
					case '{':
						value = 18;
						break;
					case '1':
						value = 19;
						break;
					case '2':
						value = 20;
						break;
					case '3':
						value = 21;
						break;
					case '4':
						value = 22;
						break;
					case '5':
						value = 23;
						break;
					case '6':
						value = 24;
						break;
					case '7':
						value = 25;
						break;
					case '8':
						value = 26;
						break;
					case '9':
						value = 27;
						break;
					case '@':
						value = 28;
						break;
					case '€':
						value = 29;
						break;
					case '<':
						value = 30;
						break;
					case '>':
						value = 31;
						break;
					case '|':
						value = 32;
						break;
					case ',':
						value = 33;
						break;
					case ';':
						value = 34;
						break;
					case ':':
						value = 35;
						break;
					case '.':
						value = 36;
						break;
					case '-':
						value = 37;
						break;
					case '#':
						value = 38;
						break;
					case '+':
						value = 39;
						break;
					case '*':
						value = 40;
						break;
					case '\'':
						value = 41;
						break;
					case '~':
						value = 45;
						break;
					case '^':
						value = 46;
						break;
					case 'a':
						value = 47;
						break;
					case 'b':
						value = 48;
						break;
					case 'c':
						value = 49;
						break;
					case 'd':
						value = 50;
						break;
					case 'e':
						value = 51;
						break;
					case 'f':
						value = 52;
						break;
					case 'g':
						value = 53;
						break;
					case 'h':
						value = 54;
						break;
					case 'i':
						value = 55;
						break;
					case 'j':
						value = 56;
						break;
					case 'k':
						value = 57;
						break;
					case 'l':
						value = 58;
						break;
					case 'm':
						value = 59;
						break;
					case 'n':
						value = 60;
						break;
					case 'o':
						value = 61;
						break;
					case 'p':
						value = 62;
						break;
					case 'q':
						value = 63;
						break;
					case 'r':
						value = 64;
						break;
					case 's':
						value = 65;
						break;
					case 't':
						value = 66;
						break;
					case 'u':
						value = 67;
						break;
					case 'v':
						value = 68;
						break;
					case 'w':
						value = 69;
						break;
					case 'x':
						value = 70;
						break;
					case 'y':
						value = 71;
						break;
					case 'z':
						value = 72;
						break;
					default:
						value = 73;
						break;
				}
				
				if(y == 0)l1 = value;
				else if(y == 1)l2 = value;
			}
		}
		
		if(l1 == l2) {
			if(l1 == 0) {
				int ll1 = w1.length();
				int ll2 = w2.length();
				if(ll1 == ll2)return 0;
				else if(ll1 > ll2)return 2;
				return 1;
			}
			return 0;
		}
		
		if(l1 < l2)return 1;
		return 2;
	}
	
	public static java.awt.Image getImageIcon() {
		return Toolkit.getDefaultToolkit().getImage(Static.getJarPath() + "/client/Main/Icon.png");
	}

	public static String toString(String[] args) {
		String tmp = null;
		for(String s : args) {
			if(tmp == null)tmp = s;
			else tmp += "\n" + s;
		}
		return tmp;
	}
	
	public static String[] toArray(String args) {
		return args.split("\n");
	}

	public static boolean checkContainsPermission(String per, List<String> permissions) {
		while(per.contains(" "))per = per.replace(" ", "");		
		if(per == null || per.length() <= 0 || per.equals(""))return true;
		
		if(!permissions.isEmpty()){
			if(permissions.contains("*"))return true;
			if(permissions.contains(per))return true;
			
			String pers[] = per.split("\\.");
			String tmp = "";
			for(int i = 0; i < pers.length; i++){
				if(tmp.length() > 0)tmp += "." + pers[i];
				else tmp += pers[i];
				
				if(permissions.contains(tmp + ".*"))return true;
			}
		}
		
		return false;
	}

	public static void run(Runnable run) {
		run(run, true);
	}
	
	public static void run(final Runnable run, boolean wait) {
		if(!wait) {
			new Thread(() -> {
				run.run();
			}).start();
			
			return;
		}
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		new Thread(() -> {
			run.run();
			latch.countDown();
		}).start();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void runWithTimeout(Callable<Object> callable, long timeout, String message) {
		try {
			runWithTimeout(callable, timeout, TimeUnit.SECONDS, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void runWithTimeout(Callable<Object> callable, long timeout, TimeUnit unit, boolean printStackTrace) throws InterruptedException, ExecutionException, TimeoutException {
		FutureTask<Object> timeoutTask = null;
		
		timeoutTask = new FutureTask<Object>(callable);
		Thread thread = new Thread(timeoutTask);
		thread.start();
		try {
			timeoutTask.get(timeout, unit);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			thread.stop();
			
			if(printStackTrace) {
				StringBuffer message = new StringBuffer();
				message.append("Timeout Thread:\n");
				
				boolean first = true;
				for(StackTraceElement element : thread.getStackTrace()) {
					if(!first)
						message.append("\n");
					else
						first = false;
						
					message.append("\tat " + element);
				}
				
				System.out.println(message.toString());
			}
			
			if(!(e instanceof InterruptedException))
				throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void runWithTimeout(Runnable run, long timeout, TimeUnit unit, boolean printStackTrace) throws InterruptedException, ExecutionException, TimeoutException {
		FutureTask<Object> timeoutTask = null;
		
		timeoutTask = new FutureTask<Object>(run, null);
		Thread thread = new Thread(timeoutTask);
		thread.start();
		try {
			timeoutTask.get(timeout, unit);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			thread.stop();
			
			if(printStackTrace) {
				StringBuffer message = new StringBuffer();
				message.append("Timeout Thread:\n");
				
				boolean first = true;
				for(StackTraceElement element : thread.getStackTrace()) {
					if(!first)
						message.append("\n");
					else
						first = false;
						
					message.append("\tat " + element);
				}
				
				System.out.println(message.toString());
			}
			
			if(!(e instanceof InterruptedException))
				throw e;
		}
	}
}