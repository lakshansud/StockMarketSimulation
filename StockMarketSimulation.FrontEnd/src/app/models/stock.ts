import { Sector } from './sector';
export class Stock {
    Id: number;
    Name: string;
    CurrentPrice: number;
    CurrentValue: number; 
    Sector: Sector;
    IsCheck: boolean;

    constructor() {
        this.Sector = new Sector();
    }
}