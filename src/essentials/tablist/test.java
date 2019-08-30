package essentials.tablist;

public class test {

	public static void main(String[] args) {
		System.out.println("start");
//		"Hello%do\\%rt%[hi] %test%hi \\%say hi\\% %da% hi &r 'was'%  \\%test%"
		TablistFormatterRegex.parseToString(null, "%dort%\\[hi] %test%");
		
//		TablistFormatterRegex.parseToString(null, "a1b a2b");
	}

}
