import {Component, OnInit} from '@angular/core';
import {Game} from "../_models/game";
import {GameService} from "../_service/game.service";
import {Router} from "@angular/router";
import {AuthService} from "../_service/auth.service";
import {BehaviorSubject, Observable} from "rxjs";
import {RoundService} from "../_service/round.service";

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.scss']
})
export class GamesComponent implements OnInit {

  isAdmin$: Observable<boolean> = new BehaviorSubject<boolean>(false);
  token: string = ''
  games: Game[] = [];

  constructor(
    private authService: AuthService,
    private gameService: GameService,
    private roundService: RoundService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const localToken = localStorage.getItem('token')
    this.isAdmin$ = this.authService.isAdmin

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

  onPlay(gameId: string) {
    this.router.navigate(['games/' + gameId]).then(
      _ => window.location.reload()
    )
  }

  onNextRound(gameId: string) {
    this.roundService.moveGameToNextRound(localStorage.getItem('token')!, gameId).subscribe(
      _ => window.location.reload()
    )
  }
}
