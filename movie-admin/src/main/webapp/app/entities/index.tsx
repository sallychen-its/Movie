import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Category from './category';
import Movie from './movie';
import Trailer from './trailer';
import Actor from './actor';
import Episode from './episode';
import Session from './session';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}movie`} component={Movie} />
      <ErrorBoundaryRoute path={`${match.url}trailer`} component={Trailer} />
      <ErrorBoundaryRoute path={`${match.url}actor`} component={Actor} />
      <ErrorBoundaryRoute path={`${match.url}episode`} component={Episode} />
      <ErrorBoundaryRoute path={`${match.url}session`} component={Session} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
