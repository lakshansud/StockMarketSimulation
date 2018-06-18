import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class HttpSubjectService {
    public notificationSubject = new Subject();
    public http403Subject = new Subject();
    public http500Subject = new Subject();
    public overlaySubject = new Subject();
    public spinnerSubject = new Subject();

    constructor() { }

    //some Example methods we call in our CustomHttp Class
    public addNotification(resultJson: any): void {
        this.notificationSubject.next(resultJson);
    }

    public addHttp403(result: any): void {
        this.http403Subject.next(result);
    }

    public addHttp500(result: any): void {
        this.http500Subject.next(result);
    }

    public removeOverlay(): void {
        this.overlaySubject.next(0);
    }

    public addSpinner(): void {
        this.spinnerSubject.next(true);
    }

    public removeSpinner(): void {
        this.spinnerSubject.next(false);
    }

}