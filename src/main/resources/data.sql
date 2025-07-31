INSERT INTO role (role_id, name) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO role (role_id, name) VALUES (2, 'DOCTOR') ON CONFLICT DO NOTHING;
INSERT INTO role (role_id, name) VALUES (3, 'PATIENT') ON CONFLICT DO NOTHING;

-- Admin kullanıcısını ekle (eğer yoksa)
INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id)
VALUES (1, '12345678901', 'Admin', 'User', 'admin', 'admin@example.com', '$2a$10$2M2W4uv.8LEI4w9mSIxu9.mECfYCXCILpP5ihSqvTgs2vJzzyPht2', 1)
ON CONFLICT (person_id) DO NOTHING;
-- The password is 'admin123'

SELECT setval('person_seq', COALESCE((SELECT MAX(person_id) FROM person), 1) + 1, false);

-- 50 örnek person ve patient eklemesi
INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (2, '23456789012', 'Ali', 'Veli', 'aliveli', 'ali2@example.com', '$2a$10$abcdef...', 3, '1990-01-01', 'Erkek', '5551110002', 'Adres 2')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (2, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (3, '34567890123', 'Ayşe', 'Demir', 'aysedemir', 'ayse3@example.com', '$2a$10$abcdef...', 3, '1985-02-02', 'Kadın', '5551110003', 'Adres 3')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (3, 'B_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (4, '45678901234', 'Mehmet', 'Kaya', 'mehmetkaya', 'mehmet4@example.com', '$2a$10$abcdef...', 3, '1978-03-03', 'Erkek', '5551110004', 'Adres 4')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (4, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (5, '56789012345', 'Fatma', 'Yılmaz', 'fatmayilmaz', 'fatma5@example.com', '$2a$10$abcdef...', 3, '1992-04-04', 'Kadın', '5551110005', 'Adres 5')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (5, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (6, '67890123456', 'Deniz', 'Arslan', 'denizarslan', 'deniz6@example.com', '$2a$10$abcdef...', 3, '1991-01-06', 'Kadın', '5551110006', 'Adres 6')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (6, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (7, '78901234567', 'Emre', 'Çetin', 'emrecetin', 'emre7@example.com', '$2a$10$abcdef...', 3, '1987-02-07', 'Erkek', '5551110007', 'Adres 7')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (7, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (8, '89012345678', 'Gizem', 'Yıldız', 'gizemyildiz', 'gizem8@example.com', '$2a$10$abcdef...', 3, '1994-03-08', 'Kadın', '5551110008', 'Adres 8')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (8, 'B_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (9, '90123456789', 'Hakan', 'Koç', 'hakankoc', 'hakan9@example.com', '$2a$10$abcdef...', 3, '1983-04-09', 'Erkek', '5551110009', 'Adres 9')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (9, 'AB_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (10, '01234567890', 'İrem', 'Acar', 'iremacar', 'irem10@example.com', '$2a$10$abcdef...', 3, '1996-05-10', 'Kadın', '5551110010', 'Adres 10')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (10, 'A_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (11, '12345098765', 'Kaan', 'Er', 'kaaner', 'kaan11@example.com', '$2a$10$abcdef...', 3, '1989-06-11', 'Erkek', '5551110011', 'Adres 11')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (11, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (12, '23456109876', 'Lale', 'Güneş', 'lalegunes', 'lale12@example.com', '$2a$10$abcdef...', 3, '1992-07-12', 'Kadın', '5551110012', 'Adres 12')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (12, 'B_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (13, '34567210987', 'Murat', 'Şimşek', 'muratsimsek', 'murat13@example.com', '$2a$10$abcdef...', 3, '1984-08-13', 'Erkek', '5551110013', 'Adres 13')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (13, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (14, '45678321098', 'Naz', 'Kara', 'nazkara', 'naz14@example.com', '$2a$10$abcdef...', 3, '1997-09-14', 'Kadın', '5551110014', 'Adres 14')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (14, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (15, '56789432109', 'Onur', 'Polat', 'onurpolat', 'onur15@example.com', '$2a$10$abcdef...', 3, '1986-10-15', 'Erkek', '5551110015', 'Adres 15')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (15, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (16, '67891234567', 'Pelin', 'Yalçın', 'pelinyalcin', 'pelin16@example.com', '$2a$10$abcdef...', 3, '1990-11-16', 'Kadın', '5551110016', 'Adres 16')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (16, 'B_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (17, '78912345678', 'Serkan', 'Uzun', 'serkanuzun', 'serkan17@example.com', '$2a$10$abcdef...', 3, '1988-12-17', 'Erkek', '5551110017', 'Adres 17')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (17, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (18, '89013456789', 'Derya', 'Kurt', 'deryakurt', 'derya18@example.com', '$2a$10$abcdef...', 3, '1993-01-18', 'Kadın', '5551110018', 'Adres 18')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (18, 'A_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (19, '90124567890', 'Barış', 'Çakır', 'bariscakir', 'baris19@example.com', '$2a$10$abcdef...', 3, '1985-02-19', 'Erkek', '5551110019', 'Adres 19')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (19, 'AB_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (20, '01235678901', 'Ece', 'Aydın', 'eceaydin', 'ece20@example.com', '$2a$10$abcdef...', 3, '1997-03-20', 'Kadın', '5551110020', 'Adres 20')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (20, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (21, '12346789012', 'Tolga', 'Demir', 'tolgademir', 'tolga21@example.com', '$2a$10$abcdef...', 3, '1982-04-21', 'Erkek', '5551110021', 'Adres 21')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (21, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (22, '23457890123', 'Seda', 'Kaya', 'sedakaya', 'seda22@example.com', '$2a$10$abcdef...', 3, '1994-05-22', 'Kadın', '5551110022', 'Adres 22')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (22, 'B_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (23, '34568901234', 'Mete', 'Şahin', 'metesahin', 'mete23@example.com', '$2a$10$abcdef...', 3, '1986-06-23', 'Erkek', '5551110023', 'Adres 23')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (23, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (24, '45679012345', 'Buse', 'Yıldırım', 'buseyildirim', 'buse24@example.com', '$2a$10$abcdef...', 3, '1991-07-24', 'Kadın', '5551110024', 'Adres 24')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (24, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (25, '56780123456', 'Can', 'Polat', 'canpolat', 'can25@example.com', '$2a$10$abcdef...', 3, '1989-08-25', 'Erkek', '5551110025', 'Adres 25')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (25, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (26, '67891234568', 'Dilan', 'Kurtuluş', 'dilankurtulus', 'dilan26@example.com', '$2a$10$abcdef...', 3, '1993-09-26', 'Kadın', '5551110026', 'Adres 26')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (26, 'B_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (27, '78912345679', 'Furkan', 'Acar', 'furkanacar', 'furkan27@example.com', '$2a$10$abcdef...', 3, '1987-10-27', 'Erkek', '5551110027', 'Adres 27')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (27, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (28, '89013456780', 'Gül', 'Kara', 'gulkara', 'gul28@example.com', '$2a$10$abcdef...', 3, '1995-11-28', 'Kadın', '5551110028', 'Adres 28')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (28, 'A_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (29, '90124567891', 'Hüseyin', 'Çelik', 'huseyincelik', 'huseyin29@example.com', '$2a$10$abcdef...', 3, '1984-12-29', 'Erkek', '5551110029', 'Adres 29')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (29, 'AB_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (30, '01235678902', 'İlknur', 'Güneş', 'ilknurgunes', 'ilknur30@example.com', '$2a$10$abcdef...', 3, '1996-01-30', 'Kadın', '5551110030', 'Adres 30')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (30, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (31, '12346789013', 'Kerem', 'Yılmaz', 'keremyilmaz', 'kerem31@example.com', '$2a$10$abcdef...', 3, '1993-02-15', 'Erkek', '5551110031', 'Adres 31')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (31, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (32, '23457890124', 'Melis', 'Demir', 'melisdemir', 'melis32@example.com', '$2a$10$abcdef...', 3, '1992-03-30', 'Kadın', '5551110032', 'Adres 32')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (32, 'B_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (33, '34568901235', 'Okan', 'Şimşek', 'okansimsek', 'okan33@example.com', '$2a$10$abcdef...', 3, '1985-04-30', 'Erkek', '5551110033', 'Adres 33')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (33, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (34, '45679012346', 'Pınar', 'Kurt', 'pinarkurt', 'pinar34@example.com', '$2a$10$abcdef...', 3, '1991-05-30', 'Kadın', '5551110034', 'Adres 34')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (34, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (35, '56780123457', 'Rıza', 'Polat', 'rizapolat', 'riza35@example.com', '$2a$10$abcdef...', 3, '1987-06-20', 'Erkek', '5551110035', 'Adres 35')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (35, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (36, '67891234569', 'Sibel', 'Kaya', 'sibelkaya', 'sibel36@example.com', '$2a$10$abcdef...', 3, '1994-07-1', 'Kadın', '5551110036', 'Adres 36')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (36, 'B_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (37, '78912345680', 'Tuna', 'Acar', 'tunaacar', 'tuna37@example.com', '$2a$10$abcdef...', 3, '1986-08-3', 'Erkek', '5551110037', 'Adres 37')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (37, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (38, '89013456781', 'Tuğba', 'Kara', 'tugbakara', 'tugba38@example.com', '$2a$10$abcdef...', 3, '1995-09-3', 'Kadın', '5551110038', 'Adres 38')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (38, 'A_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (39, '90124567892', 'Umut', 'Çelik', 'umutcelik', 'umut39@example.com', '$2a$10$abcdef...', 3, '1983-10-3', 'Erkek', '5551110039', 'Adres 39')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (39, 'AB_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (40, '01235678903', 'Vildan', 'Güneş', 'vildangunes', 'vildan40@example.com', '$2a$10$abcdef...', 3, '1996-11-4', 'Kadın', '5551110040', 'Adres 40')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (40, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (41, '12346789014', 'Yakup', 'Yılmaz', 'yakupyilmaz', 'yakup41@example.com', '$2a$10$abcdef...', 3, '1982-12-4', 'Erkek', '5551110041', 'Adres 41')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (41, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (42, '23457890125', 'Zehra', 'Demir', 'zehrademir', 'zehra42@example.com', '$2a$10$abcdef...', 3, '1992-01-4', 'Kadın', '5551110042', 'Adres 42')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (42, 'B_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (43, '34568901236', 'Yusuf', 'Şimşek', 'yusufsimsek', 'yusuf43@example.com', '$2a$10$abcdef...', 3, '1985-02-3', 'Erkek', '5551110043', 'Adres 43')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (43, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (44, '45679012347', 'Zeynep', 'Kurt', 'zeynepkurt', 'zeynep44@example.com', '$2a$10$abcdef...', 3, '1991-03-4', 'Kadın', '5551110044', 'Adres 44')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (44, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (45, '56780123458', 'Ahmet', 'Polat', 'ahmetpolat', 'ahmet45@example.com', '$2a$10$abcdef...', 3, '1987-04-5', 'Erkek', '5551110045', 'Adres 45')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (45, 'A_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (46, '24681357946', 'Yasemin', 'Öztürk', 'yaseminozturk', 'yasemin46@example.com', '$2a$10$abcdef...', 3, '1991-10-31', 'Kadın', '5551110046', 'Adres 46')
ON CONFLICT (person_id) DO NOTHING;

INSERT INTO patient (patient_id, blood_type) VALUES (46, 'AB_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;
INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (47, '14725836901', 'Cem', 'Aydın', 'cemaydin', 'cem47@example.com', '$2a$10$abcdef...', 3, '1988-11-11', 'Erkek', '5551110047', 'Adres 47')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (47, 'A_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (48, '25836914702', 'Elif', 'Kurt', 'elifkurt', 'elif48@example.com', '$2a$10$abcdef...', 3, '1995-12-12', 'Kadın', '5551110048', 'Adres 48')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (48, 'O_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (49, '36914725803', 'Burak', 'Ortakuz', 'burakortakuz', 'burak49@example.com', '$2a$10$abcdef...', 3, '1980-05-05', 'Erkek', '5551110049', 'Adres 49')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (49, 'B_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (50, '47125836904', 'Zeynep', 'Şahin', 'zeynepsahin', 'zeynep50@example.com', '$2a$10$abcdef...', 3, '1993-06-06', 'Kadın', '5551110050', 'Adres 50')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (50, 'AB_NEGATIVE') ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (51, '58269314705', 'Mert', 'Çelik', 'mertcelik', 'mert51@example.com', '$2a$10$abcdef...', 3, '1982-07-07', 'Erkek', '5551110051', 'Adres 51')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO patient (patient_id, blood_type) VALUES (51, 'O_POSITIVE') ON CONFLICT (patient_id) DO NOTHING;