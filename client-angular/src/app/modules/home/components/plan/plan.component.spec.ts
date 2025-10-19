import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanComponent } from './plan.component';

describe('PlanComponent', () => {
  let component: PlanComponent;
  let fixture: ComponentFixture<PlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlanComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('title element', () => {
    it('should display the title "plan works!"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      const titleElement = compiled.querySelector('h2');
      expect(titleElement?.textContent).toBe('plan works!');
    });
  });
});
