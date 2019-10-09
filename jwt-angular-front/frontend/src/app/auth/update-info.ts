export class UpdateInfo {
    id: number;
    firstname: string;
    lastname: string;
    username: string;
    email: string;
    password: string;
    newPassword: string;
 
    constructor(id: number, firstname: string,lastname: string, username: string, email: string, password: string, newPassword: string) {
        this.id=id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }
}