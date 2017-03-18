import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {MainComponent} from "./components/main/main.component";
import {HeaderComponent} from "./components/header/header.component";
import {PersonsComponent} from "./components/persons/persons.component";
import {TasksComponent} from "./components/tasks/tasks.component";
import {EstimationsEstimateByTaskComponent} from "./components/estimations/estimations-estimate-by-task.component";
import {EstimationsEstimateByPersonComponent} from "./components/estimations/estimations-estimate-by-person.component";
import {DropdownModule} from "ng2-bootstrap";
import {routing} from "./app.routing";
import { BarchartComponent } from './shared/barchart/barchart.component';
import { ScatterplotComponent } from './shared/scatterplot/scatterplot.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonsComponent,
    HeaderComponent,
    MainComponent,
    TasksComponent,
    EstimationsEstimateByTaskComponent,
    EstimationsEstimateByPersonComponent,
    BarchartComponent,
    ScatterplotComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing,
    DropdownModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
