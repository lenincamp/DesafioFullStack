import { Component, OnInit, ViewChild} from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { IconsService } from '../../../../core/services/icons.service';
import { UserService } from '../../../../core/services/user.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';
import { Messages } from '../../../../core/enum/messages.enum';
import { User } from '../../../../core/models/user';

declare var jquery:any;
declare var $ :any;
import * as _ from 'lodash';

@Component({
  selector: 'ot-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  displayedColumns = ['fullName', 'role', 'email', 'action'];
  dataSource: MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

	moduleName:string = "USERS";
	currentIdDelete="";
  currentUserEdit:User=new User();

  constructor(
    public roles: RolesStorage,
    public snackBar: MatSnackBar,
    public icon_svr: IconsService,
    public user_srv: UserService,
    public response_svr: ResponseServerService
    ) {

    this.getAllUsers();
    let self =this;
    $(document).on('hide.bs.modal','#editmodal', function () {
        self.getAllUsers();
    });
  }

  ngOnInit() {
    this.currentUserEdit = new User();
  }
  

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  openModalDelete(id){
    this.currentIdDelete=id;
    $('#deletemodal').modal('show');
  }

  openModalEdit(user){
    this.currentUserEdit=user;
    this.currentUserEdit.role=this.currentUserEdit.roles[0].name;
    $('#editmodal').modal('show');
  }

  deleteUser(id){
    this.deleteBDDUser(id);
  }

  /*DATA BASE ACTIONS*/
  getAllUsers(){
    this.user_srv.list().subscribe(
        data => {
          _.remove(data.dataCol,{id:1})
          this.dataSource = new MatTableDataSource(data.dataCol);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        err => {
          let message = this.response_svr.getErrorMessage(err);
          this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
          duration: 6000,
        });
        }
      );
  }

  deleteBDDUser(id){
      this.user_srv.delete(id).subscribe(
        data => {
        $('#prc_'+id).remove();
        $('#deletemodal').modal('hide');
        this.snackBar.open(Messages.SUCCESS_DELETE, Messages.EMPTY, {
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

  getRoles(roles){
    let rolesList="";
    let separador="";
    roles.forEach(rol=>{
      rolesList+=separador+rol.name;
      separador=", ";
    });
    return rolesList;
  }
}