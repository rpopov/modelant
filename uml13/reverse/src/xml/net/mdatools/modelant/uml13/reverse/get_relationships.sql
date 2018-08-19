-- implementation of java.sql.DatabaseMetaData.getImportedKeys(String, String, String)
-- called with null, schema, table

SELECT NULL AS pktable_cat,
       p.owner as pktable_schem,
       p.table_name as pktable_name,
       pc.column_name as pkcolumn_name,
       NULL as fktable_cat,
       f.owner as fktable_schem,
       f.table_name as fktable_name,
       fc.column_name as fkcolumn_name,
       fc.position as key_seq,
       NULL as update_rule,
       decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) as delete_rule,
       f.constraint_name as fk_name,
       p.constraint_name as pk_name,
       decode(f.deferrable,       'DEFERRABLE',5      ,'NOT DEFERRABLE',7      , 'DEFERRED', 6      ) deferrability 
  FROM all_cons_columns pc, 
       all_constraints p,
       all_cons_columns fc, 
       all_constraints f
WHERE 1 = 1
  AND p.owner           = f.r_owner
  AND p.constraint_name = f.r_constraint_name
  AND                     f.owner =           fc.owner
  AND                     f.table_name =      fc.table_name
  AND                     f.constraint_name = fc.constraint_name
  AND                                         fc.position = pc.position
  AND                                                       pc.owner=            p.owner
  AND                                                       pc.constraint_name = p.constraint_name
  AND                                                       pc.table_name =      p.table_name
  AND f.constraint_type = 'R'
  AND p.constraint_type = 'P'
  AND                     f.table_name = :S3 -- table
  AND                     f.owner = :s2      -- schema
ORDER BY pktable_schem, pktable_name, key_seq
