export class Company {
  id: string
  name: string
  mission: string

  constructor(id: string, name: string, mission: string) {
    this.id = id
    this.name = name
    this.mission = mission
  }

  static emptyCompany() {
    return new Company('', '', '')
  }
}
