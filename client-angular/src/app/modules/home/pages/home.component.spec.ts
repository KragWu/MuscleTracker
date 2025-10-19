import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { By } from '@angular/platform-browser';
import { provideRouter, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  const expectedRoutes = {
    prepare: '/home/prepare',
    follow: '/home/follow',
    plan: '/home/plan'
  };

  const navigationItems = [
    'Prepare Session',
    'Follow-up Session',
    'Plan Session'
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterOutlet, RouterLink, RouterLinkActive],
      providers: [
        provideRouter([
          { path: 'home/prepare', component: {} as any },
          { path: 'home/follow', component: {} as any },
          { path: 'home/plan', component: {} as any }
        ])
      ],
      declarations: [HomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).withContext('Component should be created').toBeTruthy();
  });

  describe('Header elements', () => {
    it('should display the title "Muscle Tracker"', () => {
      const titleElement = fixture.debugElement.query(By.css('.title-home'));
      expect(titleElement.nativeElement.textContent).toBe('Muscle Tracker');
    });

    it('should have Profile and Logout links in submenu', () => {
      const submenuLinks = fixture.debugElement.queryAll(By.css('.submenu-item'));
      expect(submenuLinks.length)
        .withContext('Should have exactly 2 submenu items')
        .toBe(2);
      
      const submenuTexts = submenuLinks.map(link => link.nativeElement.textContent.trim());
      expect(submenuTexts)
        .withContext('Submenu items should be Profile and Logout')
        .toEqual(['Profile', 'Logout']);
    });
  });

  describe('Navigation tabs (Desktop view)', () => {
    it('should display navigation tabs for desktop view', () => {
      const navTabs = fixture.debugElement.query(By.css('.nav-tabs'));
      expect(navTabs.classes)
        .withContext('Should have correct responsive classes')
        .toEqual(jasmine.objectContaining({
          'd-none': true,
          'd-md-flex': true
        }));
    });

    it('should have three navigation items', () => {
      const navItems = fixture.debugElement.queryAll(By.css('.nav-item'));
      expect(navItems.length)
        .withContext('Should have exactly 3 navigation items')
        .toBe(3);
      
      const navTexts = navItems.map(item => item.nativeElement.textContent.trim());
      expect(navTexts)
        .withContext('Navigation items should match expected texts')
        .toEqual(navigationItems);
    });

    it('should have correct router links', () => {
      const navLinks = fixture.debugElement.queryAll(By.css('.nav-item .nav-link'));
      
      const routerLinks = navLinks.map(link => link.attributes['routerLink']);
      expect(routerLinks)
        .withContext('Router links should match expected routes')
        .toEqual([expectedRoutes.prepare, expectedRoutes.follow, expectedRoutes.plan]);
    });
  });

  describe('Navigation buttons (Mobile view)', () => {
    it('should display navigation buttons for mobile view', () => {
      const mobileMenu = fixture.debugElement.query(By.css('.d-md-none'));
      expect(mobileMenu).toBeTruthy();
    });

    it('should have three navigation buttons', () => {
      const mobileButtons = fixture.debugElement.queryAll(By.css('.btn-subheader'));
      expect(mobileButtons.length)
        .withContext('Should have exactly 3 mobile buttons')
        .toBe(3);
      
      const buttonTexts = mobileButtons.map(btn => btn.nativeElement.textContent.trim());
      expect(buttonTexts)
        .withContext('Mobile buttons should match expected texts')
        .toEqual(navigationItems);
    });

    it('should have correct router links', () => {
      const mobileButtons = fixture.debugElement.queryAll(By.css('.btn-subheader'));
      mobileButtons.forEach((button, index) => {
        expect(button.attributes['routerLink'])
          .withContext(`Mobile button ${index + 1} should have correct router link`)
          .toBe(Object.values(expectedRoutes)[index]);
      });
    });
  });
});
