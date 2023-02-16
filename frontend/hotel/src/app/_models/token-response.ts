export class TokenResponse {
  value: string
  validUntil: Date

  constructor(value: string, validUntil: Date) {
    this.value = value
    this.validUntil = validUntil
  }
}
