import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import * as moment from 'moment';

@Injectable()
export class SecurityService {
    loggedIn = false;
    loggedUser: string = localStorage.getItem('loginUserId');

    constructor( private http: Http, private router: Router) {
        this.loggedIn = !!localStorage.getItem('isLogin');
        this.loggedUser = localStorage.getItem('loginUserId');
    }

    public logout() {
        localStorage.clear();
        this.loggedIn = false;
        this.router.navigate(['login']);
    }

    isLoggedIn() {
        return this.loggedIn;
    }

}