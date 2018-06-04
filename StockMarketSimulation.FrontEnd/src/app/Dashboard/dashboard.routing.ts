import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashBoardComponent } from './dashboard.component';

const routes: Routes = [
    { path: 'dashboard', pathMatch: 'full', component: DashBoardComponent,}
];

export const DashboardRouting: ModuleWithProviders = RouterModule.forRoot(routes);
