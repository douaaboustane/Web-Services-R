-- 02-seed.sql

-- 1) Catégories (2000)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM category) THEN
    FOR i IN 1..2000 LOOP
      INSERT INTO category (code, name)
      VALUES ('CAT' || LPAD(i::text, 4, '0'), 'Category ' || i);
END LOOP;
END IF;
END $$;

-- 2) Items
-- Variante RAPIDE (~10 000)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM item) THEN
    FOR i IN 1..10000 LOOP
      INSERT INTO item (sku, name, price, stock, category_id)
      VALUES (
        'SKU' || LPAD(i::text, 6, '0'),
        'Item ' || i,
        round((random()*100)::numeric, 2),
        (random()*100)::int,
        ((i-1) % 2000) + 1
      );
END LOOP;
END IF;
END $$;

-- Variante PLEINE (~100 000) — à activer plus tard si ta machine suit
-- DO $$
-- BEGIN
--   IF NOT EXISTS (SELECT 1 FROM item) THEN
--     FOR i IN 1..100000 LOOP
--       INSERT INTO item (sku, name, price, stock, category_id)
--       VALUES (
--         'SKU' || LPAD(i::text, 6, '0'),
--         'Item ' || i,
--         round((random()*100)::numeric, 2),
--         (random()*100)::int,
--         ((i-1) % 2000) + 1
--       );
--     END LOOP;
--   END IF;
-- END $$;

-- (Optionnel) Analyser les stats après gros insert
ANALYZE;
