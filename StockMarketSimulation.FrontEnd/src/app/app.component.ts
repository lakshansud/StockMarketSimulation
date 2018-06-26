import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { RouterModule, Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
    title = 'app';
    constructor(private router: Router, ) {
      
    }

    ngOnInit() {
        if (!localStorage.getItem('isLogin')) {
            this.router.navigate(['login']);
        }
    }
   
}
