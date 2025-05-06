package me.lsantana.RestAssuredNewExample.util;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Data {

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

	public static String getRandomUsername() {
		String[] prefixes = {"User", "Guest"};
		Random random = new Random();
		String prefix = prefixes[random.nextInt(prefixes.length)];
		int number = random.nextInt(10000);
		return prefix + number;
	}

	public static String getRandomPassword() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static int getRandomPostId() {
		return ThreadLocalRandom.current().nextInt(1, 101);
	}

	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getRandomString() {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int indice = random.nextInt(CHARACTERS.length());
			stringBuilder.append(CHARACTERS.charAt(indice));
		}

		return stringBuilder.toString();
	}

	public static String getRandomPhrase(int phraseLength) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < phraseLength ; i ++) {
			stringBuilder.append(" ");
			stringBuilder.append(getRandomString());
		}

		return stringBuilder.toString().trim().concat(".");
	}
}
