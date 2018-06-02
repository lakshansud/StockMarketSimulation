import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashBoardComponent } from './dashboard.component';

const routes: Routes = [
    { path: 'Dashboard', pathMatch: 'full', component: DashBoardComponent,}
];

export const dashboardRouting: ModuleWithProviders = RouterModule.forRoot(routes);
