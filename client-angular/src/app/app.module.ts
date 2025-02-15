import { importProvidersFrom, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { LoginService } from './core/services/login.service';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { CipherService } from './core/services/cipher.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [provideHttpClient(withInterceptorsFromDi()), {provide: LoginService}, {provide: CipherService}],
  bootstrap: [AppComponent]
})
export class AppModule { }
