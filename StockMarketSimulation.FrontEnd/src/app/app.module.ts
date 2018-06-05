import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ChartsModule,BaseChartDirective } from 'ng2-charts/ng2-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxChartsModule } from "@swimlane/ngx-charts";
import { CustomHttp } from './shared/custom.http';
import { HttpModule, Http, XHRBackend, RequestOptions, ConnectionBackend } from '@angular/http';
import { Constants } from './app.constants';
import { StockTransactionService } from './shared/services/stocktransaction.service';

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
      FormsModule,
      HttpModule,
      NgxChartsModule,
      BrowserAnimationsModule,
      BrokerRouting,
      RegisterRouting,
      AnalystRouting,
      ChartsModule,
      DashboardRouting,
    BrowserModule
  ],
  providers: [StockTransactionService, Constants,{
      provide: Http,
      useFactory: (backend: XHRBackend, defaultOptions: RequestOptions) => {
          return new CustomHttp(backend, defaultOptions);
      },
      deps: [XHRBackend, RequestOptions]
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
