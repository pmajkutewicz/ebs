import { Component, OnInit, OnChanges, ViewChild, ElementRef, Input, ViewEncapsulation } from '@angular/core';
import * as d3 from 'd3';
import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";

@Component({
  selector: 'ebs-scatterplot',
  templateUrl: './scatterplot.component.html',
  styleUrls: ['./scatterplot.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ScatterplotComponent implements OnInit {
  @ViewChild('chart') private chartContainer: ElementRef;
  @Input() private data: ChartData<number>;
  private margin: any = { top: 20, bottom: 20, left: 40, right: 20};
  private chart: any;
  private width: number;
  private height: number;
  private xScale: any;
  private yScale: any;
  private colors: any;
  private xAxis: any;
  private yAxis: any;

  constructor() { }

  ngOnInit() {
    this.createChart();
    if (this.data) {
      this.updateChart();
    }
  }

  ngOnChanges() {
    if (this.chart) {
      this.updateChart();
    }
  }

  createChart() {
    console.log("Create chart for: " + JSON.stringify(this.data));
    let element = this.chartContainer.nativeElement;
    let wys = element.offsetWidth;
    let szer = element.offsetHeight;

    this.width = wys - this.margin.left - this.margin.right;
    this.height = szer - this.margin.top - this.margin.bottom;
    let svg = d3.select(element).append('svg')
      .attr('width', wys + this.margin.left + this.margin.right)
      .attr('height', szer + this.margin.top + this.margin.bottom);

    // define X & Y domains
    let xDomain = [d3.min(this.data.dataPoints, d => d.x), d3.max(this.data.dataPoints, d => d.x)];
    let yDomain = [d3.min(this.data.dataPoints, d => d.y), d3.max(this.data.dataPoints, d => d.y)];

    this.xScale = d3.scaleLinear().domain(xDomain).range([0, this.width]);
    this.yScale = d3.scaleLinear().domain(yDomain).range([this.height, 0]);
    this.xAxis = d3.axisBottom(this.xScale);
    this.yAxis = d3.axisLeft(this.yScale);

    var perfectEstimatorLine = d3.line()
      .x(function(d) {
        return xScaler(d['x']);
      })
      .y(function(d) {
        return yScaler(d['x']);
      });

    var line = d3.line()
      .x(function(d) {
        return xScaler(d['x']);
      })
      .y(function(d) {
        return yScaler(d['yhat']);
      });

    // chart plot area
    this.chart = svg.append('g')
      .attr('class', 'bars')
      .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`);

    this.chart.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," +this.height + ")")
      .call(this.xAxis)
      .append("text")
      .attr("class", "label")
      .attr("x", this.width)
      .attr("y", -6)
      .style("text-anchor", "end")
      .text("Estimated time (minutes)");

    this.chart.append("g")
      .attr("class", "y axis")
      .call(this.yAxis)
      .append("text")
      .attr("class", "label")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Actual time (minutes)");

    let xScaler = this.xScale;
    let yScaler = this.yScale;
    this.chart.selectAll(".dot")
      .data(this.data.dataPoints)
      .enter().append("circle")
      .attr("class", "dot")
      .attr("r", 3.5)
      .attr("cx", function(d) { return xScaler(d.x) })
      .attr("cy", function(d) { return yScaler(d.y) });

    this.chart.append("path")
      .datum(this.data.dataPoints)
      .attr("class", "line")
      .attr("d", line);

    this.chart.append("path")
      .datum(this.data.dataPoints)
      .attr("d", perfectEstimatorLine)
      .attr('stroke-width', 2)
      .attr('stroke', 'green');
  }


  updateChart() {
    let element = this.chartContainer.nativeElement;
    d3.select(element).select('svg').remove();
    this.createChart();
  }
}
