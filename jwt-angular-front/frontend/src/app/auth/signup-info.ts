export class SignUpInfo {
    firstname: string;
    lastname: string;
    username: string;
    email: string;
    role: string[];
    password: string;
 
    constructor(firstname: string,lastname: string, username: string, email: string, password: string) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = ['user'];
    }
}