--ltree extension
  CREATE EXTENSION IF NOT EXISTS ltree;

-- create organization_structure_type table with data
CREATE TABLE public.organization_structure_type
(
  id    BIGSERIAL PRIMARY KEY NOT NULL,
  name  VARCHAR(80)  NOT NULL
);

INSERT INTO public.organization_structure_type (id, name)
VALUES (1, 'COUNTRY'), (2, 'REGION'), (3, 'RIVIERA'), (4, 'CITY');

-- create organization_structure table
CREATE TABLE public.organization_structure
(
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  name              VARCHAR(80)  NOT NULL,
  parent_id         BIGINT,
  tree_path         ltree,
  entity_type_id    BIGINT  NOT NULL,
  description       TEXT,
  short_description TEXT,

  FOREIGN KEY (parent_id) REFERENCES organization_structure(id),
  FOREIGN KEY (entity_type_id) REFERENCES organization_structure_type(id)
);

CREATE INDEX organisation_structure_tree_path_gist_idx ON organization_structure USING GIST (tree_path);
CREATE INDEX organisation_structure_tree_path_btree_idx ON organization_structure USING btree(tree_path);

-- create functions
  CREATE FUNCTION organisation_structure_tree_path_update_after_insert() RETURNS TRIGGER AS $$
    BEGIN
      IF EXISTS (SELECT 1 FROM organization_structure WHERE id = NEW.id AND parent_id IS NULL) THEN
        UPDATE organization_structure SET tree_path = NEW.id::text::ltree WHERE id = NEW.id;
      ELSE
        IF EXISTS (SELECT 1 FROM organization_structure WHERE id = NEW.id AND parent_id IS NOT NULL) THEN
          UPDATE organization_structure SET tree_path = temp_table.calculated_tree_path::ltree FROM
          (
            SELECT CONCAT(org2.tree_path, '.', org1.id) AS calculated_tree_path
            FROM organization_structure AS org1
            JOIN organization_structure AS org2 ON org1.parent_id = org2.id
            WHERE org1.id = NEW.id
          ) AS temp_table
          WHERE organization_structure.id = NEW.id ;
        END IF;
      END IF;

      RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

  CREATE FUNCTION organization_structure_tree_path_update_after_update() RETURNS TRIGGER AS $$
    DECLARE
      org_node_row record;
    BEGIN
      UPDATE organization_structure SET tree_path = temp_table.calculated_tree_path::ltree FROM
      (
        SELECT CONCAT(org2.tree_path, '.', org1.id) AS calculated_tree_path
        FROM organization_structure AS org1
        JOIN organization_structure AS org2 ON org1.parent_id = org2.id
        WHERE org1.id = NEW.id
      ) AS temp_table
      WHERE organization_structure.id = NEW.id ;

      FOR org_node_row IN SELECT id FROM organization_structure WHERE tree_path <@ OLD.tree_path ORDER BY id ASC
        LOOP
          UPDATE organization_structure SET tree_path = temp_table.calculated_tree_path::ltree FROM
          (
            SELECT CONCAT(org2.tree_path, '.', org1.id) AS calculated_tree_path
            FROM organization_structure AS org1
            JOIN organization_structure AS org2 ON org1.parent_id = org2.id
            WHERE org1.id = org_node_row.id
          ) AS temp_table
          WHERE organization_structure.id = org_node_row.id ;
        END LOOP;

      RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

  -- create triggers
  CREATE TRIGGER organization_structure_tree_path_after_insert_trigger AFTER INSERT ON organization_structure FOR EACH ROW EXECUTE PROCEDURE organisation_structure_tree_path_update_after_insert();
  CREATE TRIGGER organization_structure_tree_path_after_update_trigger AFTER UPDATE OF parent_id ON organization_structure FOR EACH ROW EXECUTE PROCEDURE organization_structure_tree_path_update_after_update();

