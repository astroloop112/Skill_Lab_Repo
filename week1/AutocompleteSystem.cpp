/*
Question 1: Global Autocomplete System (Trie)

Build a Trie-based system that stores up to 1 million strings.
Implement a function that returns the top 5 most frequent suggestions
for a given prefix.

Complexity Requirement:
Prefix search must be O(L), where L is the length of the prefix.
*/

#include <bits/stdc++.h>
using namespace std;

class AutocompleteSystem {
private:
    struct TrieNode {
        unordered_map<char, TrieNode*> children;
        vector<string> topFiveWords;
    };

    TrieNode* root;
    unordered_map<string, int> wordFrequency;

    void updateTopFive(TrieNode* node, const string& word) {
        if (find(node->topFiveWords.begin(),
                 node->topFiveWords.end(),
                 word) == node->topFiveWords.end()) {
            node->topFiveWords.push_back(word);
        }

        sort(node->topFiveWords.begin(),
             node->topFiveWords.end(),
             [&](const string& a, const string& b) {
                 if (wordFrequency[a] == wordFrequency[b])
                     return a < b;
                 return wordFrequency[a] > wordFrequency[b];
             });

        if (node->topFiveWords.size() > 5)
            node->topFiveWords.pop_back();
    }

public:
    AutocompleteSystem() {
        root = new TrieNode();
    }

    void insertWord(const string& word) {
        wordFrequency[word]++;

        TrieNode* current = root;
        for (char ch : word) {
            if (!current->children.count(ch))
                current->children[ch] = new TrieNode();

            current = current->children[ch];
            updateTopFive(current, word);
        }
    }

    vector<string> searchPrefix(const string& prefix) {
        TrieNode* current = root;

        for (char ch : prefix) {
            if (!current->children.count(ch))
                return {};
            current = current->children[ch];
        }

        return current->topFiveWords;
    }
};
