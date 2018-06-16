import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../models/bankaccount';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { BankAccountService } from '../shared/services/bankaccount.service';

@Component({
    selector: 'register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
    regForm: FormGroup;
    logForm: FormGroup;
    bankAccount: BankAccount = new BankAccount();

    constructor(private fb: FormBuilder, private bankAccountService: BankAccountService) {

    }

    ngOnInit() {
        this.regForm = this.fb.group({
            name: ['', [Validators.required as any]],
            address: [''],
            contact: [''],
            accountno: ['', [Validators.required as any]],
            userName: ['', [Validators.required as any]],
            password: ['', [Validators.required as any]],
        });

        this.logForm = this.fb.group({
            username: ['', [Validators.required as any]],
            password: ['', [Validators.required as any]],
        });
        this.getMaxAccountNumber();
    }

    getMaxAccountNumber(): void {
        this.bankAccountService.getMaxAccountNumber()
            .subscribe((data: number) => {
                this.bankAccount.AccountNumber = data;
            },
            (error: Response) => {
            });
    }
}
