import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './session.reducer';
import { ISession } from 'app/shared/model/session.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Session = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sessionList = useAppSelector(state => state.session.entities);
  const loading = useAppSelector(state => state.session.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="session-heading" data-cy="SessionHeading">
        <Translate contentKey="movieAdminApp.session.home.title">Sessions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="movieAdminApp.session.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="movieAdminApp.session.home.createLabel">Create new Session</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sessionList && sessionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="movieAdminApp.session.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.session.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.session.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.session.createdDate">Created Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sessionList.map((session, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${session.id}`} color="link" size="sm">
                      {session.id}
                    </Button>
                  </td>
                  <td>{session.name}</td>
                  <td>{session.description}</td>
                  <td>
                    {session.createdDate ? <TextFormat type="date" value={session.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${session.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${session.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${session.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="movieAdminApp.session.home.notFound">No Sessions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Session;
