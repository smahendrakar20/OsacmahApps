import { Component, OnInit, Input, Output } from '@angular/core';
import * as data from '../../data.json';


@Component({
  selector: 'app-Card',
  templateUrl: './Card.component.html',
  styleUrls: ['./Card.component.css']
})
export class CardComponent implements OnInit {
  @Input() Inside: any;

  constructor() { }

  ngOnInit() {
  }

}
