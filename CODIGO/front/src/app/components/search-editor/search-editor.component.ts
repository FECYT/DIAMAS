import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/user.interface";
import {ActivatedRoute} from "@angular/router";
import {ConfigBBDDService} from "../../services/configBBDD-service";
import {AuthService} from "../../services/authservice.service";
import {MessageService} from "primeng/api";
import {roles} from "../../utils/Roles";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserRepositoryService } from 'src/app/services/user-repository.service';

@Component({
  selector: 'app-search-editor',
  templateUrl: './search-editor.component.html',
  styleUrls: ['./search-editor.component.scss']
})
export class SearchEditorComponent implements OnInit {
  searchingTerm = "";
  userList: User[] = [];
  userListFiltered: User[] = [];
  generalToggle:Boolean = false;
  registeringUser = false;
  ascendingOrder: boolean = false;
  lastClickTime: number = 0;
  clickCount: number = 0;
  toggleTimer: any;
  selectedUser: User | undefined = undefined;
  userBeingEdited: User | undefined = undefined;
  selectedRoles: any = [];
  periodo: FormGroup;
  selectedStatus = 'Completo';

  constructor(private formBuilder: FormBuilder, private userRepositoryService: UserService, private messageService:MessageService ,private route:ActivatedRoute, private configBBDDService: ConfigBBDDService, private authService: AuthService) {

    this.route.queryParams.subscribe(params => {
      this.searchingTerm = params['email'];
    });

    this.periodo = this.formBuilder.group({
      status: this.selectedStatus,
      name: '',
      initialRangeTotal: 0,
      endRangeTotal: 100,
      initialRangePolitics: 0,
      endRangePolitics: 100,
      initialRangeMetadata: 0,
      endRangeMetadata: 100,
      initialRangeInteroperability: 0,
      endRangeInteroperability: 100,
      initialRangeLogs: 0,
      endRangeLogs: 100,
      initialRangeVisibility: 0,
      endRangeVisibility: 100
    });

  }

  ngOnInit() {
    this.userRepositoryService.getUsers().subscribe((data)=>{
      this.userList = data
      this.userListFiltered = data

      this.reFilterList()
    })
  }

  reFilterList() {
    this.userListFiltered = this.userList.filter(user =>
      (user.nombre + ' ' + user.apellidos).toLowerCase().includes(this.searchingTerm.toLowerCase())
    );
  }

  sortBy(status:String) {
    switch (status) {
      case "activo":
        this.userListFiltered.sort((a, b) => {
          // Assuming `active` is a boolean property
          const comparison = a.active === b.active ? 0 : a.active ? -1 : 1;
          return this.ascendingOrder ? comparison : -comparison;
        });
        // Cambia la dirección de la ordenación para la próxima vez
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "email":
        this.userListFiltered.sort((a, b) => {
          const comparison = a.email!!.localeCompare(b.email!!);
          return this.ascendingOrder ? comparison : -comparison;
        });
        // Cambia la dirección de la ordenación para la próxima vez
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "nombre":
        this.userListFiltered.sort((a, b) => {
          const comparison = (a.nombre || "").localeCompare(b.nombre || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "apellidos":
        this.userListFiltered.sort((a, b) => {
          const comparison = (a.apellidos || "").localeCompare(b.apellidos || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

      /*
      case "institucion":
        this.userListFiltered.sort((a, b) => {
          const comparison = (a.institucion || "").localeCompare(b.institucion || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;
        */

      case "roles":
        this.userListFiltered.sort((a, b) => {
          const rolesA = a.rol ? a.rol.join(', ') : '';
          const rolesB = b.rol ? b.rol.join(', ') : '';
          const comparison = rolesA.localeCompare(rolesB);
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;
    }
  }
}
