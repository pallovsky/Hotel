import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarGameComponent } from './navbar-game.component';

describe('NavbarGameComponent', () => {
  let component: NavbarGameComponent;
  let fixture: ComponentFixture<NavbarGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarGameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
