import { Component, OnInit } from '@angular/core';
import * as data from '../../data.json';

@Component({
  selector: 'app-List',
  templateUrl: './List.component.html',
  styleUrls: ['./List.component.css']
})
export class ListComponent implements OnInit {

  Section: any = (data as any).default;

  constructor() { }

  ngOnInit() {
    console.log(this.Section);
  }

}
