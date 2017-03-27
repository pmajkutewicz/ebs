import {RouterModule, Routes} from "@angular/router";
import {PersonsComponent} from "./components/persons/persons.component";
import {MainComponent} from "./components/main/main.component";
import {TasksComponent} from "./components/tasks/tasks.component";
import {EstimationsEstimateByTaskComponent} from "./components/estimations/estimations-estimate-by-task.component";
import {EstimationsEstimateByPersonComponent} from "./components/estimations/estimations-estimate-by-person.component";
import {EstimationsStatsComponent} from "./components/estimations/estimations-stats.component";

const EBS_ROUTES: Routes = [
  {path: 'estimations/stats', component: EstimationsStatsComponent},
  {path: 'estimations/estimate/person', component: EstimationsEstimateByPersonComponent},
  {path: 'estimations/estimate/task', component: EstimationsEstimateByTaskComponent},
  {path: 'tasks', component: TasksComponent},
  {path: 'team', component: PersonsComponent},
  {path: '', component: MainComponent}
];

export const routing = RouterModule.forRoot(EBS_ROUTES);
