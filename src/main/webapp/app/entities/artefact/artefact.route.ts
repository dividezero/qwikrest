import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ArtefactComponent } from './artefact.component';
import { ArtefactDetailComponent } from './artefact-detail.component';
import { ArtefactPopupComponent } from './artefact-dialog.component';
import { ArtefactDeletePopupComponent } from './artefact-delete-dialog.component';

import { Principal } from '../../shared';

export const artefactRoute: Routes = [
    {
        path: 'artefact',
        component: ArtefactComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Artefacts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artefact/:id',
        component: ArtefactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Artefacts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artefactPopupRoute: Routes = [
    {
        path: 'artefact-new',
        component: ArtefactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Artefacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact/:id/edit',
        component: ArtefactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Artefacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact/:id/delete',
        component: ArtefactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Artefacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
