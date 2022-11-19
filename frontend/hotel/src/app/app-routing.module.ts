import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {MissionComponent} from "./mission/mission.component";
import {EmailComponent} from "./email/email.component";
import {CompanyNameComponent} from "./company-name/company-name.component";
import {OfferComponent} from "./offer/offer.component";
import {InvestmentsComponent} from "./investments/investments.component";
import {FinanceComponent} from "./finance/finance.component";
import {ErrorsComponent} from "./errors/errors.component";

const routes: Routes = [
  { path: "#", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "mission", component: MissionComponent },
  { path: "email", component: EmailComponent },
  { path: "company-name", component: CompanyNameComponent },
  { path: "offer", component: OfferComponent },
  { path: "investments", component: InvestmentsComponent },
  { path: "finance", component: FinanceComponent },
  { path: "errors", component: ErrorsComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
