import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrokerComponent } from './broker.component';

const routes: Routes = [
    { path: 'broker', pathMatch: 'full', component: BrokerComponent}
];

export const BrokerRouting: ModuleWithProviders = RouterModule.forRoot(routes);
