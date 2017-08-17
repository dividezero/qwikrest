import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { QwikrestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtefactDetailComponent } from '../../../../../../main/webapp/app/entities/artefact/artefact-detail.component';
import { ArtefactService } from '../../../../../../main/webapp/app/entities/artefact/artefact.service';
import { Artefact } from '../../../../../../main/webapp/app/entities/artefact/artefact.model';

describe('Component Tests', () => {

    describe('Artefact Management Detail Component', () => {
        let comp: ArtefactDetailComponent;
        let fixture: ComponentFixture<ArtefactDetailComponent>;
        let service: ArtefactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QwikrestTestModule],
                declarations: [ArtefactDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtefactService,
                    EventManager
                ]
            }).overrideComponent(ArtefactDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtefactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtefactService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Artefact(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artefact).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
