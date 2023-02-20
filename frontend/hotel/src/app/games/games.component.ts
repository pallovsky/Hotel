import {Component, OnInit} from '@angular/core';
import {Game} from "../_models/game";
import {GameService} from "../_service/game.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.scss']
})
export class GamesComponent implements OnInit {

  token: string = ''
  games: Game[] = [];

  constructor(
    private gameService: GameService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const localToken = localStorage.getItem('token')

    if (localToken == null) {
      this.router.navigate(['/login'])
    } else {
      this.token = localToken
    }

    this.gameService.getGames(this.token).subscribe(
      response => this.games = response,
      _ => this.router.navigate(['/login'])
    )
  }

  deleteGame(id: string) {
    this.gameService.deleteGame(id, this.token).subscribe(
      _ => window.location.reload()
    )
  }
}
