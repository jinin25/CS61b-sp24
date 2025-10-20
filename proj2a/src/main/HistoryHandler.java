package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    private final NGramMap nmp;
    public HistoryHandler(NGramMap map) {
        nmp = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        List<String> labels = words;
        List<TimeSeries> weightList = new ArrayList<>();
        for (String word : words) {
            weightList.add(nmp.weightHistory(word, startYear, endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, weightList);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
