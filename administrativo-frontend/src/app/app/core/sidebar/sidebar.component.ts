import {Component, OnInit} from '@angular/core';
import {RolesStorage} from '../storage/roles.storage';
import {Icons} from '../enum/icons.enum';
import {TokenStorage} from '../storage/token.storage';
import {Router} from '@angular/router';

@Component({
  selector: 'ot-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  modulos:any;
  constructor(
    private roles: RolesStorage, 
    private token: TokenStorage, 
    private router: Router) {
    this.modulos = roles.getRolesMap()[0].modules;
  }

  getIcon(id): string {
    return Icons[id];
  }

  ngOnInit() {
  }

  logout = () => {
    this.token.signOut();
    this.router.navigate(['/home']);
  }
}
