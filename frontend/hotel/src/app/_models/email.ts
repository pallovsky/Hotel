export class Email {
  id: string
  month: string
  from: string
  topic: string
  message: string
  opened: boolean

  constructor(id: string, month: string, from: string, topic: string, message: string, opened: boolean) {
    this.id = id
    this.month = month
    this.from = from
    this.topic = topic
    this.message = message
    this.opened = opened
  }
}

