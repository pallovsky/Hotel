import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RoundService} from "../../_service/round.service";

@Component({
  selector: 'app-navbar-game',
  templateUrl: './navbar-game.component.html',
  styleUrls: ['./navbar-game.component.scss']
})
export class NavbarGameComponent implements OnInit {

  gameId = ''

  constructor(
    private router: Router,
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

  }
}
