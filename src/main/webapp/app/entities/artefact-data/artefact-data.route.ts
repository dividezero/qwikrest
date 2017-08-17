import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ArtefactDataComponent } from './artefact-data.component';
import { ArtefactDataDetailComponent } from './artefact-data-detail.component';
import { ArtefactDataPopupComponent } from './artefact-data-dialog.component';
import { ArtefactDataDeletePopupComponent } from './artefact-data-delete-dialog.component';

import { Principal } from '../../shared';

export const artefactDataRoute: Routes = [
    {
        path: 'artefact-data',
        component: ArtefactDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactData'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artefact-data/:id',
        component: ArtefactDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactData'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artefactDataPopupRoute: Routes = [
    {
        path: 'artefact-data-new',
        component: ArtefactDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact-data/:id/edit',
        component: ArtefactDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact-data/:id/delete',
        component: ArtefactDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
