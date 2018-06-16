import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register.component';
import { LoginComponent } from './login.component';

const routes: Routes = [
    { path: 'login/register', pathMatch: 'full', component: RegisterComponent },
    { path: 'login', pathMatch: 'full', component: LoginComponent }
];

export const RegisterRouting: ModuleWithProviders = RouterModule.forRoot(routes);
