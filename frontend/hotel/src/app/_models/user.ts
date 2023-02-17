export class User {
  id: string
  username: string
  role: string
  games_count: number

  constructor(id: string, username: string, role: string, games_count: number) {
    this.id = id
    this.username = username
    this.role = role
    this.games_count = games_count
  }
}

