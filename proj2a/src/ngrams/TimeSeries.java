package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for(Map.Entry<Integer, Double> entry : ts.entrySet()) {
            int key = entry.getKey();
            double value = entry.getValue();
            if(key <= endYear && key >= startYear) {
                this.put(key, value);
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Integer> yearsList = this.years();
        List<Double> dataList = new ArrayList<>();
        for(int key : yearsList) {
            dataList.add(this.get(key));
        }
        return dataList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        List<Integer> thisYearList = this.years();
        List<Integer> tsYearList = ts.years();
        TimeSeries plusTS = new TimeSeries();
        for(int key : thisYearList) {
            plusTS.put(key, this.get(key));
        }
        for(int key : tsYearList) {
            if (plusTS.containsKey(key)) {
                plusTS.replace(key, this.get(key), this.get(key) + ts.get(key));
            } else {
                plusTS.put(key, ts.get(key));
            }
        }
        return plusTS;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        List<Integer> tsYearList = ts.years();
        TimeSeries divideTS = new TimeSeries();
        for(int key : this.keySet()) {
            if (tsYearList.contains(key)) {
                divideTS.put(key, this.get(key) / ts.get(key));
            }else {
                throw new IllegalArgumentException();
            }
        }
        return divideTS;
    }

}
