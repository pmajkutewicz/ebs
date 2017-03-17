import {RouterModule, Routes} from "@angular/router";
import {PersonsComponent} from "./components/persons/persons.component";
import {MainComponent} from "./components/main/main.component";
import {TasksComponent} from "./components/tasks/tasks.component";

const EBS_ROUTES: Routes = [
  {path: 'tasks', component: TasksComponent},
  {path: 'team', component: PersonsComponent},
  {path: '', component: MainComponent}
];

export const routing = RouterModule.forRoot(EBS_ROUTES);
