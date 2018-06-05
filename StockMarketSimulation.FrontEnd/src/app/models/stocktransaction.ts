import { BankAccount } from './bankaccount';
import { Stock } from './stock';
import { Turn } from './turn';

export class StockTransaction {
    Id: number;
    Price: number;
    Type: number;
    Turn: Turn;
    BankAccount: BankAccount; 
    Stock: Stock;
    Quantity: number;
    IsCheck: boolean;

    constructor() {
        this.BankAccount = new BankAccount();
        this.Stock = new Stock();
        this.Turn = new Turn();
    }
}