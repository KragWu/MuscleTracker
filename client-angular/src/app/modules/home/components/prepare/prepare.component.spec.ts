import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrepareComponent } from './prepare.component';

describe('PrepareComponent', () => {
  let component: PrepareComponent;
  let fixture: ComponentFixture<PrepareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrepareComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrepareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('title element', () => {
    it('should display the title "prepare works!"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      const titleElement = compiled.querySelector('h2');
      expect(titleElement?.textContent).toBe('prepare works!');
    });
  });
});
