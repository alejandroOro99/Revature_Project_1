import { Component, OnInit } from '@angular/core';
import {Customer} from '../customer';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit{

  customer: Customer = {

    firstName : 'ale',
    lastName : 'orozco',
    bankAccId : 123456,
    password : 'hello',
    username : 'helloUser'
  };
  
  constructor() { }

  ngOnInit(): void {
  }

}
