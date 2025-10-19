import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowComponent } from './follow.component';

describe('FollowComponent', () => {
  let component: FollowComponent;
  let fixture: ComponentFixture<FollowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FollowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FollowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('title element', () => {
    it('should display the title "follow works!"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      const titleElement = compiled.querySelector('h2');
      expect(titleElement?.textContent).toBe('follow works!');
    });
  });
});
