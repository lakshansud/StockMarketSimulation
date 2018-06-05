export class StockTransaction {
    Id: number;
    Price: number;
    Type: number;
    TurnId: number;
    BankAccountId: number; 
    StockId: number;
    Quantity: number;
    IsCheck: boolean;
}

export class StockTransactionFull {
    Id: number;
    Price: number;
    Type: number;
    TurnNo: number;
    BankAccountName: string;
    StockName: string;
    SectorName: string;
    Quantity: number;
    IsCheck: boolean;
}