import { Injectable } from '@angular/core';
import { Http, Headers, ConnectionBackend, Request, RequestOptions, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import * as moment from 'moment';
import { Router } from '@angular/router';


@Injectable()
export class CustomHttp extends Http {
    timeoutLimit: number = 600000;
    constructor(backend: ConnectionBackend, defaultOptions: RequestOptions) {
        super(backend, defaultOptions);
        let headers = new Headers();
        headers.append('Access-Control-Allow-Headers', 'Content-Type');
        headers.append('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
        headers.append('Access-Control-Allow-Origin', '*');
        headers.append('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    }

    request(url: string | Request, options?: RequestOptionsArgs): Observable<any> {
        console.log('Before the request...');
        return super.request(url, options)
            .catch((err: any): any => {
                if (err.status === 401) {
                    return Observable.throw(err);
                } else if (err.status === 500) {
                    err.statusText = "Response with status: 500 Internal Server Error";
                    return Observable.throw(err);
                } else {
                    return Observable.throw(err);
                }
            })
            .finally(() => {
                console.log('After the request...');
            });
    }

    get(url: string, options?: RequestOptionsArgs): Observable<any> {
        console.log('Before the get request...');
        return super.get(url, options)
            .catch((err: any): any => {
                if (err.status === 401) {
                    return Observable.throw(err);
                } else if (err.status === 500) {
                    err.statusText = "Response with status: 500 Internal Server Error";
                    return Observable.throw(err);
                } else {
                    return Observable.throw(err);
                }
            })
            .finally(() => {
                console.log('After the get request...');
            });

    }

    post(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
        console.log('Before the post request...', url);
        return super.post(url, body, options)
            .catch((err: any): any => {
                if (err.status === 401) {
                    return Observable.throw(err);
                } else if (err.status === 500) {
                    err.statusText = "Response with status: 500 Internal Server Error";
                    return Observable.throw(err);
                } else if (err.status === 400) {
                    err.statusText = "Bad Request: General error when fulfilling the request would cause an invalid state. Domain validation errors, missing data, etc. are some examples.";
                    return Observable.throw(err);
                } else {
                    return Observable.throw(err);
                }
            })
            .finally(() => {
                console.log('After the post request...');
            });
    }

    put(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
        console.log('Before the put request...', url, options);
        return super.put(url, body, options)
            .catch((err: any): any => {
                if (err.status === 401) {
                    return Observable.throw(err);
                } else if (err.status === 500) {
                    err.statusText = "Response with status: 500 Internal Server Error";
                    return Observable.throw(err);
                } else {
                    return Observable.throw(err);
                }
            })
            .finally(() => {
                console.log('After the put request...');
            });
    }

    delete(url: string, options?: RequestOptionsArgs): Observable<any> {
        console.log('Before the delete request...');
        return super.delete(url, options)
            .catch((err: any): any => {
                if (err.status === 401) {
                 
                    return Observable.throw(err);
                } else if (err.status === 500) {
                    err.statusText = "Response with status: 500 Internal Server Error";
                  
                    return Observable.throw(err);
                } else {
                    return Observable.throw(err);
                }
            })
            .finally(() => {
             
                console.log('After the get request...');
            });
    }
}