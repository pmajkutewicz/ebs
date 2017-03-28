import {Component, ElementRef, Input, OnChanges, OnInit, ViewChild, ViewEncapsulation} from "@angular/core";
import * as d3 from "d3";
import {LineChartData} from "../../model/chart/LineChartData";

@Component({
  selector: 'ebs-linechart',
  templateUrl: './linechart.component.html',
  styleUrls: ['./linechart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LinechartComponent implements OnInit, OnChanges {
  @ViewChild('chart') private chartContainer: ElementRef;
  @Input() private data: LineChartData;
  private margin: any = {top: 20, bottom: 20, left: 30, right: 80};
  private chart: any;
  private width: number;
  private height: number;
  private xScale: any;
  private yScale: any;
  private colors: any;
  private xAxis: any;
  private yAxis: any;

  constructor() {
  }

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
    //https://bl.ocks.org/mbostock/3884955
    let element = this.chartContainer.nativeElement;
    this.width = element.offsetWidth - this.margin.left - this.margin.right;
    this.height = element.offsetHeight - this.margin.top - this.margin.bottom;
    let svg = d3.select(element).append('svg')
      .attr('width', element.offsetWidth)
      .attr('height', element.offsetHeight);

    // chart plot area
    this.chart = svg.append('g')
      .attr('class', 'bars')
      .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`);

    let parseTime = d3.timeParse("%Y%m%d");

    let maxY = Math.max.apply(Math, this.data.dataPoints.map(function (o) {
      return Math.max(o.estimatedTime, o.actualTime);
    }));
    let minY = Math.min.apply(Math, this.data.dataPoints.map(function (o) {
      return Math.min(o.estimatedTime, o.actualTime);
    }));

    let x = d3.scaleLinear().range([0, this.width]);
    let y = d3.scaleLinear().range([this.height, 0]);
    let z = d3.scaleOrdinal(d3.schemeCategory10);

    let line = d3.line()
    //.curve(d3.curveBasisOpen)
      .x(function (d) {
        return x(d['x']);
      })
      .y(function (d) {
        return y(d['y']);
      });

    let localData = this.data;
    let estimatedLine = localData.dataPoints.map(function (dataRow) {
        return {
          id: "Estimated",
          values: localData.dataPoints.map(function (d) {
            return {x: d.estimationTimestamp, y: d.estimatedTime};
          })
        }
      }
    );

    let actualLine = localData.dataPoints.map(function (dataRow) {
        return {
          id: "Actual",
          values: localData.dataPoints.map(function (d) {
            return {x: d.estimationTimestamp, y: d.actualTime};
          })
        }
      }
    );

    x.domain(d3.extent(this.data.dataPoints, function (d) {
      return d.estimationTimestamp;
    }));
    y.domain([minY, maxY]);
    z.domain(estimatedLine.map(function (c) {
      return c.id;
    }));

    this.chart.append("g")
      .attr("class", "axis axis--x")
      .attr("transform", "translate(0," + this.height + ")")
      .call(d3.axisBottom(x));

    this.chart.append("g")
      .attr("class", "axis axis--y")
      .call(d3.axisLeft(y))
      .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", "0.71em")
      .attr("fill", "#000")
      .text("Time, (minutes)");

    let cityChart = this.chart.selectAll(".city")
      .data(actualLine)
      .enter().append("g")
      .attr("class", "city");
    this.addLineToChart(cityChart, actualLine, x, y, z, line);
    this.addLineToChart(cityChart, estimatedLine, x, y, z, line);
  }

  addLineToChart(cityChart, lineData, x, y, z, line) {
    this.chart.selectAll(".city")
      .data(lineData)
      .enter().append("g")
      .attr("class", "city");

    cityChart.append("path")
      .attr("class", "line")
      .attr("d", function (d) {
        return line(d.values);
      })
      .style("stroke", function (d) {
        return z(d.id);
      });

    cityChart.append("text")
      .datum(function (d) {
        return {id: d.id, value: d.values[d.values.length - 1]};
      })
      .attr("transform", function (d) {
        return "translate(" + x(d.value.x) + "," + y(d.value.y) + ")";
      })
      .attr("x", 3)
      .attr("dy", "0.35em")
      .style("font", "10px sans-serif")
      .text(function (d) {
        return d.id;
      });
  }

  updateChart() {
  }
}
