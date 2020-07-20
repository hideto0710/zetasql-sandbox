WITH with_m as (
  SELECT * FROM master
)
SELECT s.a, s.b, m.c
FROM sample as s INNER JOIN with_m as m ON s.a = m.a;
