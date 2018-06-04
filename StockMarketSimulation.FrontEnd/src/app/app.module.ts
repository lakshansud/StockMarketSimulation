import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { appRoutingProviders, routing } from './app.routing';
import { NavComponent } from './nav/nav.component';
import { DashBoardComponent } from './dashboard/dashboard.component';
import { DashboardRouting } from './dashboard/dashboard.routing';
import { BrokerComponent } from './broker/broker.component';
import { BrokerRouting } from './broker/broker.routing';
import { AnalystComponent } from './analyst/analyst.component';
import { AnalystRouting } from './analyst/analyst.routing';
import { RegisterComponent } from './register/register.component';
import { RegisterRouting  } from './register/register.routing';

@NgModule({
    declarations: [
        DashBoardComponent,
        AnalystComponent,
        RegisterComponent,
        NavComponent,
        BrokerComponent,
    AppComponent
  ],
  imports: [
      routing,
      BrokerRouting,
      RegisterRouting,
      AnalystRouting,
      DashboardRouting,
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
