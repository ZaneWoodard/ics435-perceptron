package edu.hawaii.ics435;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.InputMismatchException;

public class PerceptronChart extends JFrame {

    private final JFreeChart chart;
    private final HashMap<String, Integer> lineIndexes = new HashMap<>();
    private Integer maxLineIndex = 0;
    public PerceptronChart(String title, PerceptronDataSet pds) {
        super(title);
        XYDataset xy = addInitialPoints(pds.initialPoints, pds.initialLabels);
        this.chart = ChartFactory.createScatterPlot(title, "X", "Y", xy);
        this.chart.getXYPlot().getDomainAxis().setRange(0.0, pds.maxX);
        this.chart.getXYPlot().getRangeAxis().setRange(0.0, pds.maxY);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500,500));
        setContentPane(chartPanel);
    }

    private XYDataset addInitialPoints(Point[] points, Byte[] labels) {

        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries red = new XYSeries("-1");
        XYSeries blue = new XYSeries("1");
        XYSeries learnedRed = new XYSeries("Learned -1");
        XYSeries learnedBlue = new XYSeries("Learned 1");

        for(int i = 0; i < points.length; i++) {
            Byte label = labels[i];
            Point p = points[i];
            if(label==-1) {
                red.add(p.x, p.y);
            } else if(label==1) {
                blue.add(p.x, p.y);
            } else {
                throw new InputMismatchException("Unrecognized label: " + label);
            }
        }
        result.addSeries(red);
        result.addSeries(blue);
        result.addSeries(learnedRed);
        result.addSeries(learnedBlue);

        return result;
    }

    protected void addPoint(Point p, Byte label) {

        XYSeriesCollection result = (XYSeriesCollection) chart.getXYPlot().getDataset();
        if(label==-1) {
            result.getSeries("-1").add(p.x, p.y);
        } else if(label==1) {
            result.getSeries("1").add(p.x, p.y);
        } else {
            throw new InputMismatchException("Unrecognized label: " + label);
        }
        this.chart.getXYPlot().setDataset(result);
    }

    protected void addLearnedPoint(Point p, Byte label) {

        XYSeriesCollection result = (XYSeriesCollection) chart.getXYPlot().getDataset();
        if(label==-1) {
            result.getSeries("Learned -1").add(p.x, p.y);
        } else if(label==1) {
            result.getSeries("Learned 1").add(p.x, p.y);
        } else {
            throw new InputMismatchException("Unrecognized label: " + label);
        }
        this.chart.getXYPlot().setDataset(result);

        try {
            Thread.sleep(Main.WAIT_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void addLine(Point2D.Double p1, Point2D.Double p2, String name) {

        XYItemRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);

        XYSeriesCollection lineDataSet = (XYSeriesCollection) this.chart.getXYPlot().getDataset(1);
        if(lineDataSet==null) {
            lineDataSet = new XYSeriesCollection();
        }

        XYSeries series;
        try {
            series = lineDataSet.getSeries(name);
            series.clear();
            series.add(p1.x, p1.y);
            series.add(p2.x, p2.y);
        } catch(UnknownKeyException e) {
            series = new XYSeries(name);
            series.add(p1.x, p1.y);
            series.add(p2.x, p2.y);
            lineDataSet.addSeries(series);
        }


        this.chart.getXYPlot().setRenderer(1, lineRenderer);
        this.chart.getXYPlot().setDataset(1, lineDataSet);
        try {
            Thread.sleep(Main.WAIT_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
