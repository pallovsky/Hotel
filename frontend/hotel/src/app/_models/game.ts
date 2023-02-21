export class Game {
  id: string
  name: string
  type: string
  usersCount: number
  globalRound: number
  roundLimit: number
  userRound: number

  constructor(id: string, name: string, type: string, usersCount: number, globalRound: number, roundLimit: number, userRound: number) {
    this.id = id
    this.name = name
    this.type = type
    this.usersCount = usersCount
    this.globalRound = globalRound
    this.roundLimit = roundLimit
    this.userRound = userRound
  }

  static emptyGame() {
    return new Game('', '', '', 0, 0, 0, 0)
  }
}
