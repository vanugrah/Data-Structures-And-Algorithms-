import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> kmp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern cannot be null");
        } else if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        } else if (text.length() < pattern.length()) {
            return new ArrayList<Integer>();
        } else {
            List<Integer> indexList = new ArrayList<>();
            int patternLength = pattern.length();
            int textLength = text.length();
            int[] failure = buildFailureTable(pattern);
            int j = 0;
            int k = 0;
            int textChar = text.charAt(j);
            int patternChar = pattern.charAt(k);

            while (j < textLength) {
                if (textChar == patternChar) {
                    if (k == patternLength - 1) {
                        indexList.add(j - patternLength + 1);
                        k = -1;
                    }
                    j++;
                    k++;

                    if (j < textLength) {
                        textChar = text.charAt(j);
                    }

                    if (k < patternLength) {
                        patternChar = pattern.charAt(k);
                    }

                } else if (k > 0) {
                    k = failure[k - 1];

                    if (k < patternLength) {
                        patternChar = pattern.charAt(k);
                    }

                } else {
                    j++;

                    if (j < textLength) {
                        textChar = text.charAt(j);
                    }
                }
            }

            return indexList;
        }
    }

    @Override
    public int[] buildFailureTable(CharSequence text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        } else {
            int length = text.length();
            int[] failure = new int[length];
            failure[0] = 0;
            int i = 1;
            int j = 0;
            int textChari = 0;
            if (i < length) {
                textChari = text.charAt(i);
            }

            int textCharj = text.charAt(j);

            while (i < length) {
                if (textChari == textCharj) {
                    failure[i] = j + 1;
                    i++;
                    j++;

                    if (i < length) {
                        textChari = text.charAt(i);
                    }

                    if (j < length) {
                        textCharj = text.charAt(j);
                    }

                } else if (j > 0) {
                    j = failure[j - 1];

                    if (j < length) {
                        textCharj = text.charAt(j);
                    }
                } else {
                    failure[i] = 0;
                    i++;
                    if (i < length) {
                        textChari = text.charAt(i);
                    }
                }
            }
            return failure;
        }
    }

    @Override
    public List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
        List<Integer> matchInteger = new ArrayList<>();
        if (pattern == null || pattern.length() == 0 || text == null) {
            throw new IllegalArgumentException("Either patter or text is null or pattern is empty");
        }
        if (text.length() == 0) {
            return matchInteger;
        }
        int[] charMap = buildLastTable(pattern);
        int patternLength = pattern.length();
        int textLength = text.length();
        int textIndex = patternLength - 1;
        int patternIndex = patternLength - 1;
        while (textIndex < textLength) {
            char patternChar = pattern.charAt(patternIndex);
            char textChar = text.charAt(textIndex);
            if (patternChar == textChar) {
                patternIndex--;
                textIndex--;
                if (patternIndex == -1) {
                    matchInteger.add(textIndex + 1);
                    patternIndex = patternLength - 1;
                    textIndex = textIndex + patternLength + 1;
                }
            } else {
                int l = charMap[textChar];
                textIndex = textIndex + patternLength
                        - Math.min(patternIndex, l + 1);
                patternIndex = patternLength - 1;
            }
        }
        return matchInteger;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        int maxSize = Character.MAX_VALUE + 1;
        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            charMap.put(pattern.charAt(i), i);
        }
        int[] patternMapArray = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            patternMapArray[i] = -1;
        }
        for (Character aChar : charMap.keySet()) {
            patternMapArray[aChar] = charMap.get(aChar);
        }
        return patternMapArray;
    }

    @Override
    public int generateHash(CharSequence current, int length) {
        if (current == null) {
            throw new IllegalArgumentException("current string is null");
        }
        if (length < 0 || length > current.length()) {
            throw new IllegalArgumentException("the length is either negative or greater than string lenght");
        }
        int sum = 0;
        int power = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                power = power * BASE;
            }
            sum = sum + current.charAt(i) * power;
            power = 1;
        }
        return sum;
    }

    @Override
    public int updateHash(int oldHash, int length, char oldChar, char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative");
        }

        int newSum = oldHash;
        int oldRemove = 1;
        for (int i = 0; i < length - 1; i++) {
            oldRemove = oldRemove * BASE;
        }
        oldRemove = oldChar * oldRemove;
        newSum = (newSum - oldRemove) * BASE + newChar;
        return newSum;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is either null or empty");
        }
        if (text == null) {
            throw new IllegalArgumentException("text is null");
        }
        List<Integer> matchList = new ArrayList<>();
        if (text.length() == 0) {
            return matchList;
        }
        int patternHash = generateHash(pattern, pattern.length());
        StringBuilder aBuilder = new StringBuilder();
        for (int i = 0; i < pattern.length(); i++) {
            aBuilder.append(text.charAt(i));
        }
        int textHash = generateHash(aBuilder, pattern.length());
        for (int i = 0; i < text.length() - pattern.length(); i++) {
            if (patternHash == textHash) {
                matchList.add(i);
            }
            textHash = updateHash(textHash, pattern.length(), text.charAt(i),
                    text.charAt(pattern.length() + i));
        }
        if (patternHash == textHash) {
            matchList.add(text.length() - pattern.length());
        }
        return matchList;
    }
}
