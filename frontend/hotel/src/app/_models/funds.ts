export class Funds {
  initialFunds: number
  funds: number
  estimatedProfits: number
  estimatedExpenses: number

  constructor(initialFunds: number, funds: number, estimatedProfits: number, estimatedExpenses: number) {
    this.initialFunds = initialFunds
    this.funds = funds
    this.estimatedExpenses = estimatedExpenses
    this.estimatedProfits = estimatedProfits
  }

  static emptyFunds() {
    return new Funds(
      0.0, 0.0, 0.0, 0.0
    );
  }
}
