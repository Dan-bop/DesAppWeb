import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampoDetalle } from './campo-detalle';

describe('CampoDetalle', () => {
  let component: CampoDetalle;
  let fixture: ComponentFixture<CampoDetalle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CampoDetalle]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampoDetalle);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
