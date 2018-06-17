import { Component, OnInit } from '@angular/core';
import { Login } from '../models/login';
import { BankAccount, LoginResponce } from '../models/bankaccount';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { BankAccountService } from '../shared/services/bankaccount.service';

@Component({
    selector: 'login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    logForm: FormGroup;
    login: Login = new Login();
    submitted: boolean = false;
    error: string = "";
    constructor(private fb: FormBuilder, private bankAccountService: BankAccountService) {

    }

    ngOnInit() {
        this.logForm = this.fb.group({
            username: ['', [Validators.required as any]],
            password: ['', [Validators.required as any]],
        });

    }

    loginUser(isValid: boolean) {
        this.error = "";
        this.submitted = true;
        if (isValid) {
            this.bankAccountService.login(this.login.UserName, this.login.Password)
                .subscribe((data: LoginResponce) => {
                    localStorage.setItem('isLogin', "1");
                    localStorage.setItem('BrokerId', data.BrokerId.toString());
                    localStorage.setItem('loginUserId', data.BankAccountId.toString());
                },
                (error: any) => {
                    if (error.status === 400) {
                        this.error = error._body;
                        window.scrollTo(0, 0)
                    } else {
                        window.scrollTo(0, 0)
                        this.error = error.statusText;
                    }
                });
        }
    }
}
