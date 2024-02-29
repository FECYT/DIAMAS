import { Component, Injectable, OnInit } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { BehaviorSubject, Observable, of, tap } from "rxjs";
import { QuestionnaireType } from '../interfaces/questionnaire-type.interface';
import { QuestionnaireTypeService } from './questionnaire-type.service';

@Injectable({
  providedIn: 'root'
})
export class CacheService {

  // private module = new BehaviorSubject<string>(this.retrieveModuleFromStorage() || 'null');
  // public cachedVariable$ = this.module.asObservable();

  private languageSubject = new BehaviorSubject<string>(this.getLanguage());
  public language$: Observable<string> = this.languageSubject.asObservable();

  constructor(private questionnaireTypeService: QuestionnaireTypeService) {
    this.loadData();
  }

  private selectedType = new BehaviorSubject<QuestionnaireType | null>(this.retrieveModuleFromStorage() || null);
  public selectedType$: Observable<QuestionnaireType | null> = this.selectedType.asObservable();

  private questionnaireTypesSubject = new BehaviorSubject<QuestionnaireType[]>([]);
  public questionnaireTypes$: Observable<QuestionnaireType[]> = this.questionnaireTypesSubject.asObservable();

  //Tipo 1 - Journal
  //Tipo 2 - S
  get selectedTypeId(): number | null {
    const selectedType = this.selectedTypeValue;
    return selectedType?.id ?? null;
  }

  get selectedTypeValue(): QuestionnaireType | null {
    return this.selectedType.value;
  }

  set selectedTypeValue(value: QuestionnaireType | null) {
    this.selectedType.next(value);
    this.saveModuleToStorage(value);
  }

  get questionnaireTypesValue(): QuestionnaireType[] {
    return this.questionnaireTypesSubject.value;
  }

  set questionnaireTypesValue(value: QuestionnaireType[]) {
    this.questionnaireTypesSubject.next(value);
    this.saveQuestionnaireTypesToStorage(value);
  }

  retrieveModuleFromStorage(): QuestionnaireType | null {
    const storedModule = sessionStorage.getItem('module');
    return storedModule ? JSON.parse(storedModule) : null;
  }

  saveModuleToStorage(value: QuestionnaireType | null): void {
    sessionStorage.setItem('module', JSON.stringify(value));
  }

  getQuestionnairesType(): void {
    this.questionnaireTypeService.getQuestionnairesType().subscribe((data) => {
      this.questionnaireTypesValue = data;
      // Al obtener los datos, guárdalos en sessionStorage
      sessionStorage.setItem('questionnaireTypes', JSON.stringify(data));
    });
  }

  private loadData(): void {
    // Cargar selectedType desde sessionStorage
    const storedSelectedType = sessionStorage.getItem('module');
    if (storedSelectedType) {
      this.selectedType.next(JSON.parse(storedSelectedType));
    }

    // Cargar questionnaireTypes desde sessionStorage
    const storedQuestionnaireTypes = sessionStorage.getItem('questionnaireTypes');
    if (storedQuestionnaireTypes) {
      const parsedQuestionnaireTypes = JSON.parse(storedQuestionnaireTypes);
      this.questionnaireTypesSubject.next(parsedQuestionnaireTypes);
    }
  }

  loadQuestionnaireTypes(): Observable<QuestionnaireType[]> {
    const storedQuestionnaireTypes = sessionStorage.getItem('questionnaireTypes');

    if (storedQuestionnaireTypes) {
      const parsedQuestionnaireTypes = JSON.parse(storedQuestionnaireTypes);
      this.questionnaireTypesSubject.next(parsedQuestionnaireTypes);
      return of(parsedQuestionnaireTypes);
    } else {
      return this.questionnaireTypeService.getQuestionnairesType().pipe(
        tap(data => {
          this.questionnaireTypesValue = data;
          sessionStorage.setItem('questionnaireTypes', JSON.stringify(data));
        })
      );
    }
  }

  private saveQuestionnaireTypesToStorage(value: QuestionnaireType[]): void {
    sessionStorage.setItem('questionnaireTypes', JSON.stringify(value));
  }

  clearData(): void {
    this.selectedType.next(null);
    this.questionnaireTypesSubject.next([]);
    sessionStorage.removeItem('questionnaireTypes');
    sessionStorage.removeItem('module');
  }
  /*
  retrieveModuleFromStorage(): string | null {
    return sessionStorage.getItem('module');
  }

  saveModuleToStorage(value: string): void {
    sessionStorage.setItem('module', value);
  }

  updateModule(newValue: string) {
    this.saveModuleToStorage(newValue);
    this.module.next(newValue);
  }
  */

  getEvaluatorList(): User[] | null {
    const evaluatorsString = sessionStorage.getItem('evaluatorList');
    if (evaluatorsString) {
      return JSON.parse(evaluatorsString) as User[];
    }
    return null;
  }

  setLanguage(lang: string) {
    sessionStorage.setItem('languageValue', lang);
    this.languageSubject.next(lang); // Emite el nuevo idioma
  }

  getLanguage(): string {
    return sessionStorage.getItem('languageValue') || 'en'; // Ajusta como prefieras si el idioma no está establecido
  }

  removeEvaluatorsList() {
    sessionStorage.removeItem('evaluatorList');
  }

}
