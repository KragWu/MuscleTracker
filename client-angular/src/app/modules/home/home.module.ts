import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './pages/home.component';
import { PrepareComponent } from './components/prepare/prepare.component';
import { FollowComponent } from './components/follow/follow.component';
import { PlanComponent } from './components/plan/plan.component';


@NgModule({
  declarations: [
    HomeComponent,
    PrepareComponent,
    FollowComponent,
    PlanComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
