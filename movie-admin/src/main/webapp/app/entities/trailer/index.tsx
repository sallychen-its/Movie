import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Trailer from './trailer';
import TrailerDetail from './trailer-detail';
import TrailerUpdate from './trailer-update';
import TrailerDeleteDialog from './trailer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrailerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrailerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrailerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Trailer} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrailerDeleteDialog} />
  </>
);

export default Routes;
