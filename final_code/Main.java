import java.util.ArrayDeque;
import java.util.Deque;

class Shovel {
    int id;
    int loadingTime;
    int cycleTime;

    Shovel(int id, int loadingTime, int cycleTime) {
        this.id = id;
        this.loadingTime = loadingTime;
        this.cycleTime = cycleTime;
    }

    double calculateMatchingFactor(Dumper dumper) {
        return 1.0 / (cycleTime + dumper.cycleTime);
    }
}

class Dumper {
    int id;
    int loadingTime;
    int cycleTime;

    Dumper(int id, int loadingTime, int cycleTime) {
        this.id = id;
        this.loadingTime = loadingTime;
        this.cycleTime = cycleTime;
    }
}

public class Main {
    static class Pair<F, S> {
        F first;
        S second;

        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

    // Function to find the pair with the highest matching factor
    static Pair<Shovel, Dumper> findHighestMatchingPair(Deque<Shovel> shovels, Deque<Dumper> dumpers) {
        double maxMatchingFactor = 0;
        Pair<Shovel, Dumper> highestMatchingPair = null;

        for (Shovel shovel : shovels) {
            for (Dumper dumper : dumpers) {
                double currentMatchingFactor = shovel.calculateMatchingFactor(dumper);
                if (currentMatchingFactor > maxMatchingFactor) {
                    maxMatchingFactor = currentMatchingFactor;
                    highestMatchingPair = new Pair<>(shovel, dumper);
                }
            }
        }

        return highestMatchingPair;
    }

    public static void main(String[] args) {
        Deque<Shovel> shovels = new ArrayDeque<>();
        shovels.add(new Shovel(1, 5, 10));
        shovels.add(new Shovel(2, 3, 8));
        shovels.add(new Shovel(3, 7, 12));

        Deque<Dumper> dumpers = new ArrayDeque<>();
        dumpers.add(new Dumper(1, 10, 5));
        dumpers.add(new Dumper(2, 6, 8));
        dumpers.add(new Dumper(3, 2, 10));

        Pair<Shovel, Dumper> highestMatchingPair = findHighestMatchingPair(shovels, dumpers);

        if (highestMatchingPair != null) {
            System.out.println("Highest Matching Pair - Shovel ID: " + highestMatchingPair.first.id +
                    ", Dumper ID: " + highestMatchingPair.second.id +
                    ", Matching Factor: " + highestMatchingPair.first.calculateMatchingFactor(highestMatchingPair.second));
        } else {
            System.out.println("No Matching Factor Pair found.");
        }
    }
}