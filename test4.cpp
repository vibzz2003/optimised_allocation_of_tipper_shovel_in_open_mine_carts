#include <iostream>
#include <deque>
#include <utility> // for std::pair

struct Shovel {
    int id;
    int matchingFactor;

    Shovel(int id, int matchingFactor) : id(id), matchingFactor(matchingFactor) {}
};

struct Dumper {
    int id;
    int matchingFactor;

    Dumper(int id, int matchingFactor) : id(id), matchingFactor(matchingFactor) {}
};

// Function to find the pair with the highest matching factor
std::pair<Shovel, Dumper> findHighestMatchingPair(std::deque<Shovel>& shovels, std::deque<Dumper>& dumpers) {
    int maxMatchingFactor = 0;
    std::pair<Shovel, Dumper> highestMatchingPair(shovels.front(), dumpers.front()); // Initialize with the first shovel and dumper

    for (const Shovel& shovel : shovels) {
        for (const Dumper& dumper : dumpers) {
            int currentMatchingFactor = shovel.matchingFactor + dumper.matchingFactor;
            if (currentMatchingFactor > maxMatchingFactor) {
                maxMatchingFactor = currentMatchingFactor;
                highestMatchingPair = std::make_pair(shovel, dumper);
            }
        }
    }

    return highestMatchingPair;
}

int main() {
    std::deque<Shovel> shovels = {Shovel(1, 5), Shovel(2, 3), Shovel(3, 7)};
    std::deque<Dumper> dumpers = {Dumper(1, 4), Dumper(2, 6), Dumper(3, 2)};

    std::pair<Shovel, Dumper> highestMatchingPair = findHighestMatchingPair(shovels, dumpers);

    std::cout << "Highest Matching Pair - Shovel ID: " << highestMatchingPair.first.id
              << ", Dumper ID: " << highestMatchingPair.second.id
              << ", Matching Factor: " << highestMatchingPair.first.matchingFactor + highestMatchingPair.second.matchingFactor
              << std::endl;

    return 0;
}
