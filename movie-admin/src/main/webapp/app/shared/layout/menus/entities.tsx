import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/category">
      <Translate contentKey="global.menu.entities.category" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie">
      <Translate contentKey="global.menu.entities.movie" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/trailer">
      <Translate contentKey="global.menu.entities.trailer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/actor">
      <Translate contentKey="global.menu.entities.actor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/episode">
      <Translate contentKey="global.menu.entities.episode" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/session">
      <Translate contentKey="global.menu.entities.session" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
