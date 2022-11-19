import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {MissionComponent} from "./mission/mission.component";
import {EmailComponent} from "./email/email.component";

const routes: Routes = [
  { path: "#", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "mission", component: MissionComponent },
  { path: "email", component: EmailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
