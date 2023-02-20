import {Component, OnInit} from '@angular/core';
import {GameService} from "../../_service/game.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Game} from "../../_models/game";

@Component({
  selector: 'app-game-home',
  templateUrl: './game-home.component.html',
  styleUrls: ['./game-home.component.scss']
})
export class GameHomeComponent implements OnInit {

  game: Game = new Game('', '', '', 0, 0)
  token: string = ''

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {
  }

  ngOnInit(): void {
    const gameId = this.route.snapshot.paramMap.get('gameId')!;
    const localToken = localStorage.getItem('token')

    if (localToken == null) {
      this.router.navigate(['/login'])
    } else {
      this.token = localToken
    }

    if (gameId == null) {
      this.router.navigate(['/games'])
    }

    this.gameService.getGame(this.token, gameId).subscribe(
      response => this.game = response,
      _ => this.router.navigate(['/login'])
    )
  }

}
