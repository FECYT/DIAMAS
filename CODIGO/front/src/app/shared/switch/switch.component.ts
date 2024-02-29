import { Component, Input, Output, EventEmitter } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: 'app-switch',
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss']
})
export class SwitchComponent {

  @Input() options = { position1: "", position2: "No", position3: "Sí" };
  @Input() styleScss: any = { position1: "#fffaaa", position2: "#ffffff", position3: "#aaaaaa" };
  @Output() positionChange = new EventEmitter<string>();

  @Input() currentPos = 'position1';
  @Input() disabled = false;
  @Input() frozen = false;

  changePosition(pos: string) {

    if (this.disabled) return;
    if (this.frozen) return;

    this.currentPos = pos;
    this.positionChange.emit(this.currentPos);
  }

}
