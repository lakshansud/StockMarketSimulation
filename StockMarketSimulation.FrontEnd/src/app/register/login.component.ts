import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { Login } from '../models/login';
import { BankAccount, LoginResponce } from '../models/bankaccount';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { BankAccountService } from '../shared/services/bankaccount.service';
import { SecurityService } from '../shared/services/security.service';
import { RouterModule, Router } from '@angular/router';

@Component({
    selector: 'login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    logForm: FormGroup;
    login: Login = new Login();
    submitted: boolean = false;
    error: string = "";
    constructor(private router: Router, private fb: FormBuilder, private securityService: SecurityService, private spinner: NgxSpinnerService, private bankAccountService: BankAccountService) {

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
            this.spinner.show();
            this.bankAccountService.login(this.login.UserName, this.login.Password)
                .subscribe((data: LoginResponce) => {
                    localStorage.setItem('isLogin', "1");
                    localStorage.setItem('BrokerId', data.BrokerId.toString());
                    localStorage.setItem('loginUserId', data.BankAccountId.toString());
                    this.securityService.loggedIn = true;
                    this.router.navigate(['dashboard']);
                },
                (error: any) => {
                    this.securityService.loggedIn = false;
                    if (error.status === 400) {
                        this.error = error._body;
                        window.scrollTo(0, 0);
                        this.spinner.hide();
                    } else {
                        window.scrollTo(0, 0);
                        this.spinner.hide();
                        this.error = error.statusText;
                    }
                });
        }
    }
}
