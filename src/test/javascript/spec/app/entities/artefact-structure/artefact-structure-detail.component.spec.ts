import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { QwikrestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtefactStructureDetailComponent } from '../../../../../../main/webapp/app/entities/artefact-structure/artefact-structure-detail.component';
import { ArtefactStructureService } from '../../../../../../main/webapp/app/entities/artefact-structure/artefact-structure.service';
import { ArtefactStructure } from '../../../../../../main/webapp/app/entities/artefact-structure/artefact-structure.model';

describe('Component Tests', () => {

    describe('ArtefactStructure Management Detail Component', () => {
        let comp: ArtefactStructureDetailComponent;
        let fixture: ComponentFixture<ArtefactStructureDetailComponent>;
        let service: ArtefactStructureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QwikrestTestModule],
                declarations: [ArtefactStructureDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtefactStructureService,
                    EventManager
                ]
            }).overrideComponent(ArtefactStructureDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtefactStructureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtefactStructureService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArtefactStructure(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artefactStructure).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
