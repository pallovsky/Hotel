export class Investment {
  id: string
  offerName: string
  name: string
  cost: number
  priceChange: number
  costsChange: number
  active: boolean
  finished: boolean

  constructor(id: string, offerName: string, name: string, costs: number, priceChange: number, costsChange: number, active: boolean, finished: boolean
  ) {
    this.id = id
    this.offerName = offerName
    this.name = name
    this.cost = costs
    this.priceChange = priceChange
    this.costsChange = costsChange
    this.active = active
    this.finished = finished
  }
}
