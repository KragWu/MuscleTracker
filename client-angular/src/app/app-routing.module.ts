import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuardGuard } from './shared/auth-guard.guard';

const routes: Routes = [
  { path: 'home', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule), canActivate: [authGuardGuard] },
  { path: 'register', loadChildren: () => import('./modules/register/register.module').then(m => m.RegisterModule) },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', loadChildren: () => import('./modules/login/login.module').then(m => m.LoginModule) },
  { path: 'logout', loadChildren: () => import('./modules/logout/logout.module').then(m => m.LogoutModule) },
  { path: '**', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
