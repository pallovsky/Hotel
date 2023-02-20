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
import {GamesComponent} from "./games/games.component";
import {UsersComponent} from "./users/users.component";
import {AddUserComponent} from "./users/add-user/add-user.component";
import {AddGameComponent} from "./games/add-game/add-game.component";
import {GameHomeComponent} from "./games/game-home/game-home.component";

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "users", component: UsersComponent },
  { path: "users/add", component: AddUserComponent },
  { path: "games", component: GamesComponent },
  { path: "games/add", component: AddGameComponent },
  { path: "games/:gameId", component: GameHomeComponent },
  { path: "games/:gameId/mission", component: MissionComponent },
  { path: "games/:gameId/email", component: EmailComponent },
  { path: "games/:gameId/company-name", component: CompanyNameComponent },
  { path: "games/:gameId/offer", component: OfferComponent },
  { path: "games/:gameId/investments", component: InvestmentsComponent },
  { path: "games/:gameId/finance", component: FinanceComponent },
  { path: "games/:gameId/errors", component: ErrorsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
