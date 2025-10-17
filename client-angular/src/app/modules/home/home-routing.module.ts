import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home.component';
import { PrepareComponent } from './components/prepare/prepare.component';
import { FollowComponent } from './components/follow/follow.component';
import { PlanComponent } from './components/plan/plan.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: 'prepare', component: PrepareComponent },
      { path: 'follow', component: FollowComponent },
      { path: 'plan', component: PlanComponent },
      { path: '', redirectTo: 'prepare', pathMatch: 'full' } // /home -> /home/prepare
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
