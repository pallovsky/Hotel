import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { EmailComponent } from './email/email.component';
import { CompanyNameComponent } from './company-name/company-name.component';
import { MissionComponent } from './mission/mission.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OfferComponent } from './offer/offer.component';
import { InvestmentsComponent } from './investments/investments.component';
import { FinanceComponent } from './finance/finance.component';
import { ErrorsComponent } from './errors/errors.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { UsersComponent } from './users/users.component';
import { NavbarGameComponent } from './navbar-game/navbar-game.component';
import { GamesComponent } from './games/games.component';
import { AddUserComponent } from './users/add-user/add-user.component';
import { AddGameComponent } from './games/add-game/add-game.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    HomeComponent,
    EmailComponent,
    CompanyNameComponent,
    MissionComponent,
    OfferComponent,
    InvestmentsComponent,
    FinanceComponent,
    ErrorsComponent,
    UsersComponent,
    NavbarGameComponent,
    GamesComponent,
    AddUserComponent,
    AddGameComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
