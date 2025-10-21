package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    Map<String, TimeSeries> nGramMap = new HashMap<>();
    TimeSeries countTs = new TimeSeries();    /**

     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.

        In inWord = new In(wordsFilename);
        while (inWord.hasNextLine()) {
            String nextLine = inWord.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double count = Double.parseDouble(splitLine[2]);
            TimeSeries newTS = new TimeSeries();
            newTS.put(year, count);
            nGramMap.merge(word, newTS, (existingTS, newTSValue) -> existingTS.plus(newTSValue));
        }

        In inCount = new In(countsFilename);
        while (inCount.hasNextLine()) {
            String nextLine = inCount.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double count = Double.parseDouble(splitLine[1]);
            countTs.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        return new TimeSeries(nGramMap.get(word), startYear, endYear);

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(nGramMap.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {

        return new TimeSeries(countTs, MIN_YEAR, MAX_YEAR);

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        if (nGramMap.get(word) == null) {
            return new TimeSeries();
        }
        TimeSeries weightCount = countHistory(word, startYear, endYear).dividedBy(totalCountHistory());
        return weightCount;

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {

        if (nGramMap.get(word) == null) {
            return new TimeSeries();
        }
        TimeSeries weightCount = countHistory(word).dividedBy(totalCountHistory());
        return weightCount;

    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {

        TimeSeries summedTs = new TimeSeries();
        for (String word : words) {
            summedTs = summedTs.plus(weightHistory(word, startYear, endYear));
        }
        return summedTs;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedTs = new TimeSeries();
        for (String word : words) {
            summedTs = summedTs.plus(weightHistory(word));
        }
        return summedTs;
    }


}
