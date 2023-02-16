export class LoginRequest {
  username: string
  password: string
  remember: boolean

  constructor(username: string, password: string, remember: boolean) {
    this.username = username
    this.password = password
    this.remember = remember
  }

  toFormData(): FormData {
    const formData: any = new FormData();
    formData.append("username", this.username);
    formData.append("password", this.password);
    formData.append("remember", this.remember);

    return formData
  }
}
