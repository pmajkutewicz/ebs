import {Routes, RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";
import {PersonsComponent} from "./components/persons/persons.component";
import {MainComponent} from "./components/main/main.component";

const EBS_ROUTES: Routes = [
  {path: 'person', component: PersonsComponent},
  {path: '', component: MainComponent}
];

export const routing = RouterModule.forRoot(EBS_ROUTES);
