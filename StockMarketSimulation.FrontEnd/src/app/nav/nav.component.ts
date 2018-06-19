import { Component } from '@angular/core';
import { SecurityService } from '../shared/services/security.service';

@Component({
  selector: 'navbar',
  templateUrl: './nav.component.html'
})
export class NavComponent {

    constructor( private securityService: SecurityService) {

    }
}
