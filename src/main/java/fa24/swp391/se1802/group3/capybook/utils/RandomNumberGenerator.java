package fa24.swp391.se1802.group3.capybook.utils;

import java.util.Random;

public class RandomNumberGenerator {
    public String generateNumber() {
        Random random = new Random();
        return 100_000 + random.nextInt(999_999)+"";
    }
}
