import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnalystComponent } from './analyst.component';

const routes: Routes = [
    { path: 'analyst', pathMatch: 'full', component: AnalystComponent}
];

export const AnalystRouting: ModuleWithProviders = RouterModule.forRoot(routes);
