import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { appRoutingProviders, routing } from './app.routing';
import { NavComponent } from './nav/nav.component';
import { DashBoardComponent } from './Dashboard/dashboard.component';
import { dashboardRouting } from './Dashboard/dashboard.routing';

@NgModule({
    declarations: [
        DashBoardComponent,
        NavComponent,
    AppComponent
  ],
  imports: [
      routing,
      dashboardRouting,
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
