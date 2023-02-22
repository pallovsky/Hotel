export class Offer {
  id: string
  type: string
  name: string
  price: number
  costs: number;
  active: boolean
  workHours: number

  constructor(id: string, type: string, name: string, price: number, costs: number, active: boolean, workHours: number) {
    this.id = id
    this.type = type
    this.name = name
    this.price = price
    this.costs = costs
    this.active = active
    this.workHours = workHours
  }
}
