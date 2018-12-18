import { Component, OnInit, Input } from '@angular/core';
import { Messages } from '../../../../core/enum/messages.enum';
import { Process } from '../../../../core/models/process';
import { Status } from '../../../../core/models/status';
import { Observation } from '../../../../core/models/observation';
import { TokenStorage } from '../../../../core/storage/token.storage';
import { IconsService } from '../../../../core/services/icons.service';
import { ObservationService } from '../../../../core/services/observation.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

declare var jquery:any;
declare var $ :any;
import * as _ from 'lodash';

@Component({
  selector: 'observations',
  templateUrl: './observations.component.html',
  styleUrls: ['./observations.component.css']
})
export class ObservationsComponent implements OnInit {

	@Input() process:Process;
	observation:Observation;
	@Input() observationsOlder:Observation[] = [];
	@Input() addObsrv:boolean = true;

	constructor(
	public token: TokenStorage,
	public snackBar: MatSnackBar,
	public icon_svr: IconsService,
	public observation_srv: ObservationService,
	public response_svr: ResponseServerService
	) {
		this.process = new Process();
		this.observation = new Observation();
	}

  ngOnInit() {
  	this.process = new Process();
  }

  saveObservation(){
  	this.prepareObservation();
    this.observation_srv.save(this.observation).subscribe(
      data => {
        this.reset();
        $('#observationModal').modal('hide');
        this.snackBar.open(Messages.SUCCESS_SAVE, Messages.EMPTY, {
          duration: 4000,
        });
      },
      err => {
        let message = this.response_svr.getErrorMessage(err);
        this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
        duration: 6000,
      });
      }
    );
  }

  prepareObservation(){
  	this.observation.statusId=this.process.executions[0].statuses[0].id;
  	this.observation.userId = +this.token.getID();
  }

  reset(){
  	this.observation = new Observation();
  	this.process = new Process();
  }

  getUsernameObsr(userId){
  	return _.find(this.process.executions[0].statuses[0].users,{id:userId}).userName;
  }

}
