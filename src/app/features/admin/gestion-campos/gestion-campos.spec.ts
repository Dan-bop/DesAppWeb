import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionCampos } from './gestion-campos';

describe('GestionCampos', () => {
  let component: GestionCampos;
  let fixture: ComponentFixture<GestionCampos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionCampos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionCampos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
