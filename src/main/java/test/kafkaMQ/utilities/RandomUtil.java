package test.kafkaMQ.utilities;

import java.util.Random;

public class RandomUtil {
	 public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 public static final String NUMBERCHAR = "0123456789";

	 public static String generateMixString(int length) {  
		 StringBuffer sb = new StringBuffer();  
		 Random random = new Random();

		 for (int i = 0; i < length; i++) {  
	        sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));  
	     }

	     return sb.toString();  
	 } 
}
