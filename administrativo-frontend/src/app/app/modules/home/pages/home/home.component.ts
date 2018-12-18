import {Component, OnInit} from '@angular/core';
import {TokenStorage} from '../../../../core/storage/token.storage';
import {RolesStorage} from '../../../../core/storage/roles.storage';
import {AuthenticationService} from '../../../../core/authentication/authentication.service';
import {Messages} from '../../../../core/enum/messages.enum';
import {Router} from '@angular/router';

@Component({
  selector: 'ot-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private token: TokenStorage,
    private roles: RolesStorage) {
  }

  ngOnInit() {
    console.warn(this.token.getToken(),"ok token salir");
    if (this.token.getToken() != null && this.token.getToken().length > 0) {
      this.router.navigate(['/admin']);
    }
  }

  username: string;
  password: string;
  error: boolean = false;
  error_msg: string;
  flagLoading=false;

  pressKeyPassword(ev){
    if(ev.keyCode===13 && this.username.trim().length>0 && this.password.trim().length>0)
      this.login();
  }

  login(): void {
    this.error=false; 
    this.flagLoading=true;
    this.authService.attemptAuth(this.username, this.password).subscribe(
      data => {
        console.log(data);
        this.token.saveID(data.userPrincipal.id);
        this.token.saveToken(data.accessToken);
        this.roles.saveRolesMap(data.userPrincipal.rolesMap.roles);
        this.router.navigate(['admin']);
        this.flagLoading=false;
      },
      err => {
        this.error=true; 
        this.error_msg=Messages.NO_ACCESS;
        this.flagLoading=false;
      }
    );
  }

}
