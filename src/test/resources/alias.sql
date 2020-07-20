SELECT s.a as s_a, s.b as s_b, m.c as m_c
FROM sample as s INNER JOIN master as m ON s.a = m.a;
