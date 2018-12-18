import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'delete-confirm',
  templateUrl: './delete-confirm.component.html',
  styleUrls: ['./delete-confirm.component.css']
})
export class DeleteConfirmComponent implements OnInit {

	@Output() onClicConfirm: EventEmitter<any> = new EventEmitter();
	
	constructor() { }

	ngOnInit() {
	}

	executeDelete(){
		this.onClicConfirm.emit();
	}

}
