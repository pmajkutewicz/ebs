import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {MainComponent} from "./components/main/main.component";
import {HeaderComponent} from "./components/header/header.component";
import {PersonsComponent} from "./components/persons/persons.component";
import {TasksComponent} from "./components/tasks/tasks.component";
import {DropdownModule} from "ng2-bootstrap";
import {routing} from "./app.routing";

@NgModule({
  declarations: [
    AppComponent,
    PersonsComponent,
    HeaderComponent,
    MainComponent,
    TasksComponent
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
