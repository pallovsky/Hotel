export class TokenResponse {
  value: string
  validUntil: Date
  role: string

  constructor(value: string, validUntil: Date, role: string) {
    this.value = value
    this.validUntil = validUntil
    this.role = role
  }
}
