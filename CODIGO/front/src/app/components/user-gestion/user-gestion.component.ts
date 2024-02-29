import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User, UserPlusRoles} from "../../interfaces/user.interface";
import {ActivatedRoute} from "@angular/router";
import isEqual from 'lodash/isEqual';
import {ConfigBBDDService} from "../../services/configBBDD-service";
import {AuthService} from "../../services/authservice.service";
import {Message, MessageService} from "primeng/api";
import {roles} from "../../utils/Roles";
import {PasswordRecoverService} from "../../services/recoverPass-service";
import {CacheService} from "../../services/cache.service";
import {exportTranslations} from "../../global-constants";
import { UserRepositoryService } from 'src/app/services/user-repository.service';
import { UserRepository } from 'src/app/interfaces/user-repository.interface';

@Component({
  selector: 'app-user-gestion',
  templateUrl: './user-gestion.component.html',
  styleUrls: ['./user-gestion.component.scss']
})
export class UserGestionComponent {

  paramEmail = ""

  repositoryList: UserRepository[] = []
  repositoryListFiltered: UserRepository[] = []
  searchingTerm = "";
  ascendingOrder: boolean = false;

  selectedUserRepository: UserRepository | undefined = undefined
  userRepositoryBeingEdited: UserRepository | undefined = undefined

  toggleTimer: any;
  lastClickTime: number = 0;
  clickCount: number = 0;

  generalToggle:Boolean = false;
  registeringUser = false;

  registerPasswordsNotEqual = false
  registerEmailInvalid = false

  registerNombre = ""
  registerApellidos = ""
  registerInstitucion = ""
  registerEmail = ""
  registerPassword = ""
  registerPasswordRepeat = ""
  registerAcronimo = ""
  registerURL = ""
  registerAfiliacion = ""
  registerCountry = ""

  selectedRoles: any = [];

  constructor(private cacheService:CacheService,private recoverPasswordService:PasswordRecoverService,private userService:UserService,private userRepositoryService:UserRepositoryService, private messageService:MessageService ,private route:ActivatedRoute, private configBBDDService: ConfigBBDDService, private authService: AuthService) {

    this.route.queryParams.subscribe(params => {
      this.searchingTerm = params['email'];
    });

  }

  ngOnInit(){
    this.loadData();
  }

  loadData() {
    this.userRepositoryService.getUsers().subscribe((data) => {
      this.repositoryList = data;
      this.repositoryListFiltered = data;

      this.reFilterList();
    });

    this.configBBDDService.getAutomaticRegister().subscribe((data)=>{
      this.generalToggle = data
    })

  }

  updateGeneralToggle(){
    this.configBBDDService.setAutomaticRegister(this.generalToggle).subscribe((data)=>{})
  }

  reFilterList() {
    if(this.searchingTerm !== undefined) {
      this.repositoryListFiltered = this.repositoryList.filter(repository =>
        repository.user?.email && repository.user?.email.toLowerCase().includes(this.searchingTerm.toLowerCase()) ||
        (repository.user?.nombre + ' ' + repository.user?.apellidos).toLowerCase().includes(this.searchingTerm.toLowerCase())
      );
    }
  }

  onCountrySelected(event:any){
    if (event == null) return;
    this.registerCountry = event.alpha2Code
  }

  onCountrySelectedEdit(event:any){
    if (event == null) return;
    this.userRepositoryBeingEdited!!.user!.pais!! = event.alpha2Code
  }


  selectedUserRepositorySelected(userRepository: UserRepository){
    this.selectedUserRepository = userRepository;
    this.userRepositoryBeingEdited = JSON.parse(JSON.stringify(userRepository));;
    this.selectedRoles = this.selectedUserRepository?.user?.rol?.filter(
      userRole => roles.some(role => role.name === userRole)
    ).map(userRole => roles.find(role => role.name === userRole)) || [];

  }

  onSumbitEdit(){

    if (isEqual(this.userRepositoryBeingEdited, this.selectedUserRepository) && this.selectedRoles.size == this.userRepositoryBeingEdited!!.user!.rol!!.length) {
      this.selectedUserRepository = undefined
      return;
    }

    const foundUser = this.repositoryList.find(repository => repository.user?.id === this.selectedUserRepository!!.id);

    if (foundUser!!.user?.email !== this.userRepositoryBeingEdited!!.user!.email){

      this.userService.doesEmailExist(this.userRepositoryBeingEdited!!.user!.email!!).subscribe((data)=>{

        if (data){
          this.emailYaEnUso();
          return;
        }

        this.updateUser(foundUser);
      })

    } else {
      this.updateUser(foundUser);
    }

  }

  updateUser(foundUser:any){
    foundUser!!.nombre = this.userRepositoryBeingEdited!!.user!.nombre
    foundUser!!.ipsp = this.userRepositoryBeingEdited!!.user!.ipsp
    foundUser!!.apellidos = this.userRepositoryBeingEdited!!.user!.apellidos
    foundUser!!.email = this.userRepositoryBeingEdited!!.user!.email
    foundUser!!.pais = this.userRepositoryBeingEdited!!.user!.pais
    foundUser!!.url = this.userRepositoryBeingEdited!!.user!.url
    foundUser!!.acronimo = this.userRepositoryBeingEdited!!.user!.acronimo
    foundUser!!.afiliacion_institucional = this.userRepositoryBeingEdited!!.user!.afiliacion_institucional

    if(this.generalToggle) {
      foundUser!!.active = 1;
    } else {
      foundUser!!.active = 0;
    }

    const rolesUpdated = this.selectedRoles.map((role: { id: number; }) => role.id);

    const userPlusRoles: UserPlusRoles = {
      user: foundUser,
      rolesIds: rolesUpdated,
    };

    this.userService.updateUserPlusRoles(userPlusRoles).subscribe((data)=>{
      const selectedRolesNames: string[] = this.selectedRoles.map((role: any) => role.name);
      foundUser.rol = selectedRolesNames
      this.loadData();
    })

    this.selectedUserRepository = undefined;
  }

  updateUserActivity(userRepository: UserRepository) {
    const currentTime = new Date().getTime();

    // Si ha pasado más de 500 ms desde el último clic, reinicia el contador
    if (currentTime - this.lastClickTime > 500) {
      this.clickCount = 0;
    }

    // Incrementa el contador de clics
    this.clickCount++;

    // Actualiza el tiempo del último clic
    this.lastClickTime = currentTime;

    // Lógica para decidir si aplicar el retraso o no
    if (this.clickCount > 1) {
      // Si se detecta más de un clic reciente, aplica el retraso
      clearTimeout(this.toggleTimer);
      this.toggleTimer = setTimeout(() => {
        this.userService.toggleUserActivity(userRepository.user!.id!!, userRepository.user!.active!!).subscribe((data) => {
        });
      }, 500);
    } else {
      // Si es un solo clic, realiza la acción inmediatamente sin retraso
      this.userService.toggleUserActivity(userRepository.user!.id!!, userRepository.user!.active!!).subscribe((data) => {
      });
    }
  }


  sortBy(status:String){


    switch (status) {
      case "activo":
        this.repositoryListFiltered.sort((a, b) => {
          // Assuming `active` is a boolean property
          const comparison = a.user?.active === b.user?.active ? 0 : a.user?.active ? -1 : 1;
          return this.ascendingOrder ? comparison : -comparison;
        });
        // Cambia la dirección de la ordenación para la próxima vez
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "email":
        this.repositoryListFiltered.sort((a, b) => {
            const emailA = a.user?.email;
            const emailB = b.user?.email;

            // Handle the case where either email is undefined
            if (emailA === undefined || emailB === undefined) {
                return 0; // or handle it based on your requirements
            }

            const comparison = emailA.localeCompare(emailB);
            return this.ascendingOrder ? comparison : -comparison;
        });
        // Change the sorting order for the next time
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "nombre":
        this.repositoryListFiltered.sort((a, b) => {
          const comparison = (a.user?.nombre || "").localeCompare(b.user?.nombre || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "apellidos":
        this.repositoryListFiltered.sort((a, b) => {
          const comparison = (a.user?.apellidos || "").localeCompare(b.user?.apellidos || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "institucion":
        this.repositoryListFiltered.sort((a, b) => {
          const comparison = (a.repository.institucion?.nombre || "").localeCompare(b.repository.institucion?.nombre || "");
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

      case "roles":
        this.repositoryListFiltered.sort((a, b) => {
          const rolesA = a.user?.rol ? a.user?.rol.join(', ') : '';
          const rolesB = b.user?.rol ? b.user?.rol.join(', ') : '';
          const comparison = rolesA.localeCompare(rolesB);
          return this.ascendingOrder ? comparison : -comparison;
        });
        this.ascendingOrder = !this.ascendingOrder;
        break;

    }

  }

  onRegisterSubmit(){

    const emailPattern = /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/;

    if (this.registerCountry == "" || this.registerCountry == undefined) {
      alert('Por favor elija un país');
      return;
    }

    if (!emailPattern.test(this.registerEmail)) {
      this.registerEmailInvalid = true
      return;
    } else this.registerEmailInvalid = false

    if (this.registerPassword != this.registerPasswordRepeat){
      this.registerPasswordsNotEqual = true
      return;
    } else this.registerPasswordsNotEqual = false

    const user: User = {
      nombre: this.registerNombre,
      apellidos: this.registerApellidos,
      ipsp: this.registerInstitucion.toUpperCase(),
      email: this.registerEmail,
      password: this.registerPassword,
      acronimo: this.registerAcronimo.toUpperCase(),
      url: this.registerURL,
      afiliacion_institucional: this.registerAfiliacion.toUpperCase(),
      pais: this.registerCountry
    }

    this.authService.registerUser(user).subscribe((code)=>{

      this.registeringUser = false

      switch (code){

        case 0:
          //Correcto, LOGIN
          this.userService.getUserByEmail(this.registerEmail).subscribe((data)=>{
            // this.repositoryList.unshift(data)
          })
          this.success()
          this.registerNombre = ""
          this.registerApellidos = ""
          this.registerInstitucion = ""
          this.registerEmail = ""
          this.registerPassword = ""
          this.registerPasswordRepeat = ""
          this.registerAcronimo = ""
          this.registerURL = ""
          this.registerAfiliacion = ""
          this.registerCountry = ""
          break;

        case 1:
          //Correcto, contacta para activar
          this.userService.getUserByEmail(this.registerEmail).subscribe((data)=>{
            // this.repositoryList.unshift(data)
          })
          this.successAvisaAdmin()
          this.registerNombre = ""
          this.registerApellidos = ""
          this.registerInstitucion = ""
          this.registerEmail = ""
          this.registerPassword = ""
          this.registerPasswordRepeat = ""
          this.registerAcronimo = ""
          this.registerURL = ""
          this.registerAfiliacion = ""
          this.registerCountry = ""
          break;

        case 2:
          //Ya existe usuario con este mail
          this.errorYaExiste()
          break;

      }
      this.loadData();

    })

  }

  getExportTranslation(key:string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }


  successAvisaAdmin() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("EXITO"), detail:this.getExportTranslation("REGISTRADO_CON_EXITO_3")}]);
  }

  success() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("EXITO"), detail:this.getExportTranslation("REGISTRADO_CON_EXITO_2")}]);
  }

  correoEnviado() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("EXITO"), detail:this.getExportTranslation("CORREO_RECUPERACION_ENVIADO")}]);
  }

  errorYaExiste() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("YA_EXISTE_EMAIL")}]);
  }

  emailYaEnUso() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("EMAIL_EN_USO")}]);
  }

  sendPasswordRecover(event: Event, userEmail: string): void {
    event.preventDefault();

    this.recoverPasswordService.generateNewCode(userEmail).subscribe((data)=>{
      this.correoEnviado()
    })
  }





  protected readonly roles = roles;
  protected readonly undefined = undefined;
}
