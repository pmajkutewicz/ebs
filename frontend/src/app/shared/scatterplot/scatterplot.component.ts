import { Component, OnInit, OnChanges, ViewChild, ElementRef, Input, ViewEncapsulation } from '@angular/core';
import * as d3 from 'd3';
import {ChartData} from "../../model/chart/ChartData";

@Component({
  selector: 'ebs-scatterplot',
  templateUrl: './scatterplot.component.html',
  styleUrls: ['./scatterplot.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ScatterplotComponent implements OnInit {
  @ViewChild('chart') private chartContainer: ElementRef;
  @Input() private data: ChartData;
  private margin: any = { top: 20, bottom: 20, left: 20, right: 20};
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
    wys = 399;
    szer = 399;
    this.width = wys - this.margin.left - this.margin.right;
    this.height = szer - this.margin.top - this.margin.bottom;
    let svg = d3.select(element).append('svg')
      .attr('width', wys)
      .attr('height', szer);

    // define X & Y domains
    let xDomain = [0, d3.max(this.data.dataPoints, d => d.x)];
    let yDomain = [0, d3.max(this.data.dataPoints, d => d.y)];

    this.xScale = d3.scaleLinear().domain(xDomain).range([0, this.width]);
    this.yScale = d3.scaleLinear().domain(yDomain).range([this.height, 0]);
    this.xAxis = d3.axisBottom(this.xScale);
    this.yAxis = d3.axisLeft(this.yScale);

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
      .text("X-Value");

    this.chart.append("g")
      .attr("class", "y axis")
      .call(this.yAxis)
      .append("text")
      .attr("class", "label")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Y-Value");

    let xScaler = this.xScale;
    let yScaler = this.yScale;
    this.chart.selectAll(".dot")
      .data(this.data.dataPoints)
      .enter().append("circle")
      .attr("class", "dot")
      .attr("r", 3.5)
      .attr("cx", function(d) { return xScaler(d.x) })
      .attr("cy", function(d) { return yScaler(d.y) });

    //TODO: draw line for all points not only first and last
    let samples = this.data.dataPoints.length-1;
    this.chart.append('line')
      .attr("class", "line")
      .attr('x1',xScaler(this.data.dataPoints[0].x))
      .attr('x2',xScaler(this.data.dataPoints[samples].x))
      .attr('y1',yScaler(this.data.dataPoints[0].yhat))
      .attr('y2',yScaler(this.data.dataPoints[samples].yhat))
  }


  updateChart() {
    // update scales & axis
    this.xScale.domain(this.data.dataPoints.map(d => d.x));
    this.yScale.domain([0, d3.max(this.data.dataPoints, d => d.y)]);
    //this.colors.domain([0, this.data.length]);
    //this.xAxis.transition().call(d3.axisBottom(this.xScale));
    //this.yAxis.transition().call(d3.axisLeft(this.yScale));

    let update = this.chart.selectAll('.dot')
      .data(this.data.dataPoints);

    // remove exiting bars
    update.exit().remove();

    let xScaler = this.xScale;
    let yScaler = this.yScale;
    // add new bars
    update.enter().append("circle")
      .attr("class", "dot")
      .attr("r", 3.5)
      .attr("cx", function(d) { return xScaler(d.x) })
      .attr("cy", function(d) { return yScaler(d.y) });
  }
}
