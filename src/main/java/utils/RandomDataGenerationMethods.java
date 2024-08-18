package utils;

import java.util.Random;

public class RandomDataGenerationMethods {

    static Random random = new Random();

    //Generate a random string of specified length

    public static String generateRandomString (int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    public static String generateRandomCyrillicString (int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('б' + random.nextInt(30));
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    //Generate a random integer within the specified range

    public static int generateRandomInteger (int min, int max) {
        return random.nextInt(max - min) + min;
    }

    //Generate a random boolean value

    public static boolean generateRandomBoolean () {
        return random.nextBoolean();
    }

}
