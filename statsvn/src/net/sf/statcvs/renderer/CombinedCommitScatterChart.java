/*
    StatCvs - CVS statistics generation 
    Copyright (C) 2002  Lukasz Pekacki <lukasz@pekacki.de>
    http://statcvs.sf.net/
    
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    
	Created on $Date: 2004/10/12 07:22:42 $ 
*/
package net.sf.statcvs.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.statcvs.Messages;
import net.sf.statcvs.model.Author;
import net.sf.statcvs.output.ConfigurationOptions;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author jentzsch
 */
public class CombinedCommitScatterChart extends Chart{
	private static Logger logger =
	Logger.getLogger("net.sf.statcvs.renderer.LOCChart");

	/**
	 * @param series_all
	 * @param authorSeriesMap 
	 * @param title the chart title
	 * @param fileName the filename where the chart will be saved
	 * @param width width of PNG in pixels
	 * @param height height of PNG in pixels
	 */
	public CombinedCommitScatterChart(TimeSeries series_all, Map authorSeriesMap, String title,
							  String fileName, int width,	int height) {
		super(title, fileName);

		createScatterChart(series_all, authorSeriesMap, title);
		createChart();
		saveChart(width, height);
	}

	private void createScatterChart(TimeSeries series_all, Map authorSeriesMap, String title) {
		logger.finer("creating scatter chart for " + title);

		String domain = Messages.getString("TIME_CSC_DOMAIN");
		String range = Messages.getString("TIME_CSC_RANGE");
		
		DateAxis timeAxis = new DateAxis(domain);
		timeAxis.setVerticalTickLabels(true);
		CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(timeAxis);
		
		JFreeChart chart = new JFreeChart(ConfigurationOptions.getProjectName(), JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, false);
		setChart(chart);
		
		XYDataset dataset_all = new TimeSeriesCollection(series_all);
		NumberAxis valueAxis_all = new NumberAxis("All (" + range + ")");
		valueAxis_all.setTickUnit(new NumberTickUnit(6.0, new DecimalFormat("0")));
		valueAxis_all.setAutoRangeIncludesZero(false);
		valueAxis_all.setRange(0.0, 24.0);
		valueAxis_all.setLabelFont(new Font("SansSerif", Font.PLAIN, 9));
		XYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES);
		renderer.setShape(new Rectangle(new Dimension(2, 2)));
		XYPlot plot_all = new XYPlot(dataset_all, null, valueAxis_all, renderer);
		combinedPlot.add(plot_all, 1);
		
		List authors = new ArrayList(authorSeriesMap.keySet());
		Collections.sort(authors);
		Iterator authorsIt = authors.iterator();
		while (authorsIt.hasNext()) {
			Author author = (Author) authorsIt.next();
			TimeSeries timeSeries = (TimeSeries) authorSeriesMap.get(author);
			
			XYDataset dataset = new TimeSeriesCollection(timeSeries);
			NumberAxis valueAxis = new NumberAxis(author.getName());
			valueAxis.setTickUnit(new NumberTickUnit(6.0, new DecimalFormat("0")));
			valueAxis.setAutoRangeIncludesZero(false);
			valueAxis.setRange(0.0, 24.0);
			valueAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 9));
			XYItemRenderer renderer1 = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES);
			renderer1.setShape(new Rectangle(new Dimension(2, 2)));
			XYPlot plot1 = new XYPlot(dataset, null, valueAxis, renderer1);
			plot1.getRenderer().setSeriesPaint(0, Color.red);
			combinedPlot.add(plot1, 1);
		}
		combinedPlot.setGap(10);
		setChart(chart);
	}
}