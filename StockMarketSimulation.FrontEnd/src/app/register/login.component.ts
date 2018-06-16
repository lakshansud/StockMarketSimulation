import { Component, OnInit } from '@angular/core';
import { Login } from '../models/login';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { BankAccountService } from '../shared/services/bankaccount.service';

@Component({
    selector: 'login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    logForm: FormGroup;
    login: Login = new Login();

    constructor(private fb: FormBuilder, private bankAccountService: BankAccountService) {

    }

    ngOnInit() {
        this.logForm = this.fb.group({
            username: ['', [Validators.required as any]],
            password: ['', [Validators.required as any]],
        });

    }
}
