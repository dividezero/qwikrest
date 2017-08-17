import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { QwikrestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtefactDataDetailComponent } from '../../../../../../main/webapp/app/entities/artefact-data/artefact-data-detail.component';
import { ArtefactDataService } from '../../../../../../main/webapp/app/entities/artefact-data/artefact-data.service';
import { ArtefactData } from '../../../../../../main/webapp/app/entities/artefact-data/artefact-data.model';

describe('Component Tests', () => {

    describe('ArtefactData Management Detail Component', () => {
        let comp: ArtefactDataDetailComponent;
        let fixture: ComponentFixture<ArtefactDataDetailComponent>;
        let service: ArtefactDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QwikrestTestModule],
                declarations: [ArtefactDataDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtefactDataService,
                    EventManager
                ]
            }).overrideComponent(ArtefactDataDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtefactDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtefactDataService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArtefactData(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artefactData).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
