DO $$
DECLARE
dup_count integer;
BEGIN
SELECT count(*) INTO dup_count
FROM (SELECT nida FROM customers
      WHERE nida IS NOT NULL
      GROUP BY nida HAVING count(*) > 1) d;

IF dup_count > 0 THEN
        RAISE EXCEPTION 'Found % duplicated NIDA value(s) in customers. Resolve them manually before applying V2.', dup_count;
END IF;
END $$;

ALTER TABLE customers ADD CONSTRAINT uk_customers_nida UNIQUE (nida);