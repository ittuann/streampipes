/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import { RestApi } from './rest-api.service';
import { Injectable } from '@angular/core';
import { Observable, timer } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { filter, map, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { LoginService } from '../login/services/login.service';
import {
    CurrentUserService,
    JwtTokenStorageService,
} from '@streampipes/shared-ui';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(
        private restApi: RestApi,
        private tokenStorage: JwtTokenStorageService,
        private currentUserService: CurrentUserService,
        private router: Router,
        private loginService: LoginService,
    ) {
        if (this.authenticated()) {
            this.currentUserService.authToken$.next(tokenStorage.getToken());
            this.currentUserService.user$.next(tokenStorage.getUser());
            this.currentUserService.isLoggedIn$.next(true);
        } else {
            this.logout();
        }
        this.scheduleTokenRenew();
        this.watchTokenExpiration();
    }

    public login(data) {
        const jwtHelper: JwtHelperService = new JwtHelperService({});
        const decodedToken = jwtHelper.decodeToken(data.accessToken);
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(decodedToken.user);
        this.currentUserService.authToken$.next(data.accessToken);
        this.currentUserService.user$.next(decodedToken.user);
    }

    public oauthLogin(token: string) {
        const jwtHelper: JwtHelperService = new JwtHelperService({});
        const decodedToken = jwtHelper.decodeToken(token);
        this.tokenStorage.saveToken(token);
        this.tokenStorage.saveUser(decodedToken.user);
        this.currentUserService.authToken$.next(token);
        this.currentUserService.user$.next(decodedToken.user);
    }

    public logout() {
        this.tokenStorage.clearTokens();
        this.currentUserService.authToken$.next(undefined);
    }

    public authenticated(): boolean {
        const token =
            this.currentUserService.authToken$.getValue() ||
            this.tokenStorage.getToken();
        const jwtHelper: JwtHelperService = new JwtHelperService({});

        return (
            token !== null &&
            token !== undefined &&
            !jwtHelper.isTokenExpired(token)
        );
    }

    public decodeJwtToken(token: string): any {
        const jwtHelper: JwtHelperService = new JwtHelperService({});
        return jwtHelper.decodeToken(token);
    }

    checkConfiguration(): Observable<boolean> {
        return Observable.create(observer =>
            this.restApi.configured().subscribe(
                response => {
                    if (response.configured) {
                        observer.next(true);
                    } else {
                        observer.next(false);
                    }
                },
                error => {
                    observer.error();
                },
            ),
        );
    }

    scheduleTokenRenew() {
        this.currentUserService.authToken$
            .pipe(
                filter((token: any) => !!token),
                map((token: any) =>
                    new JwtHelperService({}).getTokenExpirationDate(token),
                ),
                switchMap((expiresIn: Date) =>
                    timer(expiresIn.getTime() - Date.now() - 60000),
                ),
            )
            .subscribe(() => {
                if (this.authenticated()) {
                    this.updateTokenAndUserInfo();
                }
            });
    }

    updateTokenAndUserInfo() {
        this.loginService.renewToken().subscribe(data => {
            this.login(data);
        });
    }

    watchTokenExpiration() {
        this.currentUserService.authToken$
            .pipe(
                filter((token: any) => !!token),
                map((token: any) =>
                    new JwtHelperService({}).getTokenExpirationDate(token),
                ),
                switchMap((expiresIn: Date) =>
                    timer(expiresIn.getTime() - Date.now() + 1),
                ),
            )
            .subscribe(() => {
                this.logout();
                this.router.navigate(['login']);
            });
    }

    public hasRole(role: string): boolean {
        return this.currentUserService.hasRole(role);
    }

    public hasAnyRole(roles: string[]): boolean {
        return this.currentUserService.hasAnyRole(roles);
    }

    isAnyAccessGranted(privileges: string[], redirect?: boolean): boolean {
        if (!privileges || privileges.length === 0) {
            return true;
        }

        const result = this.hasAnyRole(privileges);
        if (!result && redirect) {
            this.router.navigate(['']);
        }
        return result;
    }
}
