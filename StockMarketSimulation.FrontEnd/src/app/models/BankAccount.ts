export class BankAccount {
    Id: number;
    PlayerName: string;
    AccountNumber: number;
    Balance: number;
    UserName: string;
    Password: string;
    Password2: string;
}

export class LoginResponce {
    BankAccountId: number;
    BrokerId: number;
}

export class CurrentBankInfo {
    CurrentBaniBalance: number;
    TotalBoughtItem: number;
    TotalSoldItem: number;
}