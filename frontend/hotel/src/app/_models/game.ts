export class Game {
  id: string
  name: string
  type: string
  usersCount: number

  constructor(id: string, name: string, type: string, usersCount: number) {
    this.id = id
    this.name = name
    this.type = type
    this.usersCount = usersCount
  }
}
