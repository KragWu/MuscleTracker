beforeAll(() => {
    spyOn(localStorage, 'removeItem').and.callThrough();
    spyOn(localStorage, 'setItem').and.callThrough();
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'session') return 'session123';
      if (key === 'token') return 'token456';
      return null;
    });
});

afterEach(() => {
    if (localStorage.getItem && (localStorage.getItem as jasmine.Spy).calls) {
        (localStorage.getItem as jasmine.Spy).calls.reset();
    }
    if (localStorage.removeItem && (localStorage.removeItem as jasmine.Spy).calls) {
        (localStorage.removeItem as jasmine.Spy).calls.reset();
    }
    if (localStorage.setItem && (localStorage.setItem as jasmine.Spy).calls) {
        (localStorage.setItem as jasmine.Spy).calls.reset();
    }
});
