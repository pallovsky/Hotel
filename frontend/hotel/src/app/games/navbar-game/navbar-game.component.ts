import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RoundService} from "../../_service/round.service";
import {Game} from "../../_models/game";
import {GameService} from "../../_service/game.service";

@Component({
  selector: 'app-navbar-game',
  templateUrl: './navbar-game.component.html',
  styleUrls: ['./navbar-game.component.scss']
})
export class NavbarGameComponent implements OnInit {

  gameId: string = ''

  constructor(
    private router: Router,
    private gameService: GameService,
    private roundService: RoundService
  ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
  }

  navigateTo(dst: string) {
    this.router.navigate(['games/' + this.gameId + '/' + dst])
  }

  onSend() {
    this.roundService.moveUserToNextRound(localStorage.getItem('token')!, this.gameId).subscribe(
      _ => this.router.navigate(['games']),
      _ => this.router.navigate(['login'])
    )
  }
}
