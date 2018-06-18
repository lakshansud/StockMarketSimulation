export class StockPriceHistory {
    Id: number;
    CreateDate: Date;
    PreviousValue: number;
    PreviousPrice: number; 
    name: string;
}

export class ValueChangeForYears {
    name: number;
    series: ValueChangeForYearsSeries[];
}

export class ValueChangeForYearsSeries {
    name: string;
    value: number;
}