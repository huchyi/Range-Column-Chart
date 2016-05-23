/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.huangchuanyi.agraphicalviewdemo;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Temperature demo range chart.
 */
public class TemperatureChart extends AbstractDemoChart {

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Temperature range chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The monthly temperature (vertical range chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    double[] minValues = new double[] { 24, 19, 10, 1, 7, 12, 15, 14, 9, 1, 11, 16, 14, 9, 1, 11, 16,1, 11, 16,11, 16, 14, 9, 1, 11, 16,1, 11, 16 };
    double[] maxValues = new double[] { 117, 112, 24, 28, 33, 35, 37, 36, 28, 119, 111, 14, 36, 28, 119, 111, 14 , 119, 111, 14,111, 14, 36, 28, 119, 111, 14 , 119, 111, 14};

    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    RangeCategorySeries series = new RangeCategorySeries("Temperature");
    int length = minValues.length;
    for (int k = 0; k < length; k++) {
      series.add(minValues[k], maxValues[k]);
    }
    dataset.addSeries(series.toXYSeries());
    int[] colors = new int[] { Color.CYAN };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "", "", "", 0.5, 31.5,
            0, 120, Color.WHITE, Color.WHITE);
    renderer.setBarSpacing(0.5);
    renderer.setXLabels(0);
    renderer.setXLabelsColor(Color.TRANSPARENT);
    renderer.setBarWidth(7);
    renderer.setYLabels(6);
    // 设置横纵轴是否能放大
    renderer.setZoomEnabled(false, false);
    // 设置横纵轴是否能移动
    renderer.setPanEnabled(false, false);
    renderer.addXTextLabel(1, "1");
    renderer.addXTextLabel(6, "6");
    renderer.addXTextLabel(11, "11");
    renderer.addXTextLabel(16, "16");
    renderer.addXTextLabel(21, "21");
    renderer.addXTextLabel(26, "26");
    renderer.addXTextLabel(31, "31");
    renderer.setMargins(new int[]{30, 70, 10, 0});
    // 刻度线与刻度标注之间的相对位置关系（刻度线在标注的上方）
    renderer.setXLabelsAlign(Align.CENTER);
    // 刻度线在标注的右边
    renderer.setYLabelsAlign(Align.RIGHT);
    // 设置颜色
    renderer.setApplyBackgroundColor(true);
    // 坐标轴内的颜色
    renderer.setBackgroundColor(0xffffff);
    // 坐标轴外的颜色
    renderer.setMarginsColor(0xffffff);

    XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
    r.setDisplayChartValues(false);
    r.setChartValuesTextSize(12);
    r.setChartValuesSpacing(3);
    r.setGradientEnabled(false);
    r.setGradientStart(-20, Color.BLUE);
    r.setGradientStop(20, Color.GREEN);
    return ChartFactory.getRangeBarChartIntent(context, dataset, renderer, Type.DEFAULT,
        "Temperature range");
  }

}
