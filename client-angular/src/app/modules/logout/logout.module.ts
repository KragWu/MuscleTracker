import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoutComponent } from './pages/logout.component';
import { LogoutRoutingModule } from './logout-routing.module';
import { Router } from '@angular/router';
import { LoginService } from '../../core/services/login.service';


@NgModule({
  declarations: [
    LogoutComponent
  ],
  imports: [
    CommonModule,
    LogoutRoutingModule
  ]
})
export class LogoutModule {}
