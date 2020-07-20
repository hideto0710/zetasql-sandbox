SELECT s.a, MAX(m.c)
FROM sample as s INNER JOIN master as m ON s.a = m.a
GROUP BY s.a;
