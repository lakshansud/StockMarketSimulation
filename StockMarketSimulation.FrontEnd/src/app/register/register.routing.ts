import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register.component';

const routes: Routes = [
    { path: 'register', pathMatch: 'full', component: RegisterComponent,}
];

export const RegisterRouting: ModuleWithProviders = RouterModule.forRoot(routes);
