INSERT INTO role ( name) VALUES ( 'ADMIN') ON CONFLICT  DO NOTHING;
INSERT INTO role ( name) VALUES ( 'DOCTOR') ON CONFLICT DO NOTHING;
INSERT INTO role ( name) VALUES ('PATIENT') ON CONFLICT DO NOTHING;

-- Admin kullanıcısını ekle (eğer yoksa)
INSERT INTO person (id, first_name, last_name, username, email, password, role_id)
VALUES (1, 'Admin', 'User', 'admin', 'admin@example.com',
        '$2a$10$2M2W4uv.8LEI4w9mSIxu9.mECfYCXCILpP5ihSqvTgs2vJzzyPht2', 1)
ON CONFLICT (id) DO NOTHING;
-- The password is 'admin123'

ALTER SEQUENCE person_seq INCREMENT BY 1 MINVALUE 1 RESTART WITH 2;