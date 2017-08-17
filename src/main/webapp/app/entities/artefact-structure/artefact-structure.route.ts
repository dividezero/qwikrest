import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ArtefactStructureComponent } from './artefact-structure.component';
import { ArtefactStructureDetailComponent } from './artefact-structure-detail.component';
import { ArtefactStructurePopupComponent } from './artefact-structure-dialog.component';
import { ArtefactStructureDeletePopupComponent } from './artefact-structure-delete-dialog.component';

import { Principal } from '../../shared';

export const artefactStructureRoute: Routes = [
    {
        path: 'artefact-structure',
        component: ArtefactStructureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactStructures'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artefact-structure/:id',
        component: ArtefactStructureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactStructures'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artefactStructurePopupRoute: Routes = [
    {
        path: 'artefact-structure-new',
        component: ArtefactStructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactStructures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact-structure/:id/edit',
        component: ArtefactStructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactStructures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artefact-structure/:id/delete',
        component: ArtefactStructureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArtefactStructures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
