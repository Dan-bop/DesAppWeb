import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Campos } from './campos';

describe('Campos', () => {
  let component: Campos;
  let fixture: ComponentFixture<Campos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Campos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Campos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
