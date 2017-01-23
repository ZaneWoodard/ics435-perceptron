package edu.hawaii.ics435;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by zane on 1/22/17.
 */
public class PerceptronChart extends JFrame {

    private final JFreeChart chart;
    public PerceptronChart(String title, Point[] points, Byte[] labels) {
        super(title);
        XYDataset xy = addInitialPoints(points, labels);
        this.chart = ChartFactory.createScatterPlot(title, "X", "Y", xy);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500,500));
        setContentPane(chartPanel);
    }

    private XYDataset addInitialPoints(Point[] points, Byte[] labels) {

        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries red = new XYSeries("-1");
        XYSeries blue = new XYSeries("1");
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
        return result;
    }

    protected void addPoint(Point p, Byte label) {

        XYSeriesCollection result = (XYSeriesCollection) chart.getXYPlot().getDataset();
        if(label==-1) {
            result.getSeries("-1").add(p.x, p.y);
        } else if(label==1) {
            result.getSeries("-1").add(p.x, p.y);
        } else {
            throw new InputMismatchException("Unrecognized label: " + label);
        }
        this.chart.getXYPlot().setDataset(result);
    }

    protected void addLine(Point p1, Point p2, String name) {
        XYItemRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);
        XYSeriesCollection lineDataSet = new XYSeriesCollection();
        XYSeries series = new XYSeries(name);
        series.add(p1.x, p1.y);
        series.add(p2.x, p2.y);
        lineDataSet.addSeries(series);

        this.chart.getXYPlot().setRenderer(1, lineRenderer);
        this.chart.getXYPlot().setDataset(1, lineDataSet);
    }
}
