import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categoryEntity = useAppSelector(state => state.category.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="movieAdminApp.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieAdminApp.category.name">Name</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="movieAdminApp.category.description">Description</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="movieAdminApp.category.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.category.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {categoryEntity.createdDate ? (
              <TextFormat value={categoryEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;
