package me.lsantana.RestAssuredNewExample.util;

import java.util.Random;
import java.util.UUID;

public class Data {
	
	public static String getRandomUsername() {
		String[] prefixes = {"User", "Guest"};
		Random random = new Random();
        String prefix = prefixes[random.nextInt(prefixes.length)];
        int number = random.nextInt(10000);
        return prefix + number;
	}
	
	public static String getRandomPassword() {
		UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
	}

}
