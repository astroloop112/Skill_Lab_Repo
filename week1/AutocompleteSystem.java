/*
Question 1: Global Autocomplete System (Trie)

Build a Trie-based system that stores up to 1 million strings.
Implement a function that returns the top 5 most frequent suggestions
for a given prefix.

Complexity Requirement:
Prefix search must be O(L), where L is the length of the prefix.
*/

import java.util.*;

class AutocompleteSystem {

    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> topFiveWords = new ArrayList<>();
    }

    private TrieNode rootNode = new TrieNode();
    private Map<String, Integer> wordFrequency = new HashMap<>();

    public void insertWord(String word) {
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);

        TrieNode currentNode = rootNode;

        for (char character : word.toCharArray()) {
            currentNode.children.putIfAbsent(character, new TrieNode());
            currentNode = currentNode.children.get(character);
            updateTopFive(currentNode, word);
        }
    }

    private void updateTopFive(TrieNode node, String word) {

        if (!node.topFiveWords.contains(word)) {
            node.topFiveWords.add(word);
        }

        node.topFiveWords.sort((firstWord, secondWord) -> {
            int frequencyDifference =
                    wordFrequency.get(secondWord) - wordFrequency.get(firstWord);

            if (frequencyDifference == 0)
                return firstWord.compareTo(secondWord);

            return frequencyDifference;
        });

        if (node.topFiveWords.size() > 5) {
            node.topFiveWords.remove(node.topFiveWords.size() - 1);
        }
    }

    public List<String> searchPrefix(String prefix) {

        TrieNode currentNode = rootNode;

        for (char character : prefix.toCharArray()) {
            if (!currentNode.children.containsKey(character)) {
                return new ArrayList<>();
            }
            currentNode = currentNode.children.get(character);
        }

        return currentNode.topFiveWords;
    }
}
