import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagoSimulacion } from './pago-simulacion';

describe('PagoSimulacion', () => {
  let component: PagoSimulacion;
  let fixture: ComponentFixture<PagoSimulacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagoSimulacion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PagoSimulacion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
