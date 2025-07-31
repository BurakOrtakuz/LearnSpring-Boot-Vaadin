INSERT INTO role (role_id, name) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO role (role_id, name) VALUES (2, 'DOCTOR') ON CONFLICT DO NOTHING;
INSERT INTO role (role_id, name) VALUES (3, 'PATIENT') ON CONFLICT DO NOTHING;

-- Admin kullanıcısını ekle (eğer yoksa)
INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id)
VALUES (1, '12345678901', 'Admin', 'User', 'admin', 'admin@example.com', '$2a$10$2M2W4uv.8LEI4w9mSIxu9.mECfYCXCILpP5ihSqvTgs2vJzzyPht2', 1)
ON CONFLICT (person_id) DO NOTHING;
-- The password is 'admin123'


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
VALUES (12, '23456109876', 'Lale', 'Güneş', 'lalegune2s', 'lale12@example.com', '$2a$10$abcdef...', 3, '1992-07-12', 'Kadın', '5551110012', 'Adres 12')
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
VALUES (36, '67891234569', 'Sibel', 'Kaya', 'sibe2lkaya', 'sibel36@example.com', '$2a$10$abcdef...', 3, '1994-07-1', 'Kadın', '5551110036', 'Adres 36')
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

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (52, '11111111152', 'Selim', 'Yıldırım', 'selimyildirim', 'selim52@example.com', '$2a$10$abcdef...', 2, '1980-03-12', 'Erkek', '5551110052', 'Adres 52')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (52, 'Kardiyoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (53, '11111111153', 'Aylin', 'Demir', 'aylindemir', 'aylin53@example.com', '$2a$10$abcdef...', 2, '1985-07-24', 'Kadın', '5551110053', 'Adres 53')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (53, 'Dahiliye') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (54, '11111111154', 'Murat', 'Kaya', 'muratkaya', 'murat54@example.com', '$2a$10$abcdef...', 2, '1977-11-05', 'Erkek', '5551110054', 'Adres 54')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (54, 'Ortopedi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (55, '11111111155', 'Elif', 'Çelik', 'elifcelik', 'elif55@example.com', '$2a$10$abcdef...', 2, '1990-02-18', 'Kadın', '5551110055', 'Adres 55')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (55, 'Göz Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (56, '11111111156', 'Ahmet', 'Polat', 'ahmetpolat2', 'ahmet56@example.com', '$2a$10$abcdef...', 2, '1982-06-30', 'Erkek', '5551110056', 'Adres 56')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (56, 'Nöroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (57, '11111111157', 'Zeynep', 'Acar', 'zeynepacar', 'zeynep57@example.com', '$2a$10$abcdef...', 2, '1988-09-14', 'Kadın', '5551110057', 'Adres 57')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (57, 'Çocuk Sağlığı') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (58, '11111111158', 'Burak', 'Şahin', 'buraksahin', 'burak58@example.com', '$2a$10$abcdef...', 2, '1979-12-22', 'Erkek', '5551110058', 'Adres 58')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (58, 'Üroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (59, '11111111159', 'Derya', 'Kurt', 'deryakurt2', 'derya59@example.com', '$2a$10$abcdef...', 2, '1983-04-10', 'Kadın', '5551110059', 'Adres 59')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (59, 'Kadın Doğum') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (60, '11111111160', 'Emre', 'Yalçın', 'emreyalcin', 'emre60@example.com', '$2a$10$abcdef...', 2, '1986-08-19', 'Erkek', '5551110060', 'Adres 60')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (60, 'KBB') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (61, '11111111161', 'Gizem', 'Polat', 'gizempolat', 'gizem61@example.com', '$2a$10$abcdef...', 2, '1991-01-23', 'Kadın', '5551110061', 'Adres 61')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (61, 'Deri Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (62, '11111111162', 'Hakan', 'Demir', 'hakandemir', 'hakan62@example.com', '$2a$10$abcdef...', 2, '1984-05-15', 'Erkek', '5551110062', 'Adres 62')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (62, 'Psikiyatri') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (63, '11111111163', 'Melis', 'Kara', 'meliskara', 'melis63@example.com', '$2a$10$abcdef...', 2, '1993-03-28', 'Kadın', '5551110063', 'Adres 63')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (63, 'Fizik Tedavi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (64, '11111111164', 'Onur', 'Güneş', 'onurgunes', 'onur64@example.com', '$2a$10$abcdef...', 2, '1987-10-02', 'Erkek', '5551110064', 'Adres 64')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (64, 'Genel Cerrahi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (65, '11111111165', 'Pelin', 'Şimşek', 'pelinsimsek', 'pelin65@example.com', '$2a$10$abcdef...', 2, '1992-12-11', 'Kadın', '5551110065', 'Adres 65')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (65, 'Radyoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (66, '11111111166', 'Serkan', 'Aydın', 'serkanaydin', 'serkan66@example.com', '$2a$10$abcdef...', 2, '1981-09-09', 'Erkek', '5551110066', 'Adres 66')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (66, 'Göğüs Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (67, '11111111167', 'Dilan', 'Çetin', 'dilancetin', 'dilan67@example.com', '$2a$10$abcdef...', 2, '1989-04-17', 'Kadın', '5551110067', 'Adres 67')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (67, 'Enfeksiyon') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (68, '11111111168', 'Furkan', 'Kurt', 'furkankurt', 'furkan68@example.com', '$2a$10$abcdef...', 2, '1983-08-21', 'Erkek', '5551110068', 'Adres 68')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (68, 'Beyin Cerrahisi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (69, '11111111169', 'Gül', 'Polat', 'gulpolat', 'gul69@example.com', '$2a$10$abcdef...', 2, '1994-11-13', 'Kadın', '5551110069', 'Adres 69')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (69, 'Acil Tıp') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (70, '11111111170', 'Hüseyin', 'Yıldız', 'huseyinyildiz', 'huseyin70@example.com', '$2a$10$abcdef...', 2, '1986-05-06', 'Erkek', '5551110070', 'Adres 70')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (70, 'Anestezi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (71, '11111111171', 'İlknur', 'Çakır', 'ilknurcakir', 'ilknur71@example.com', '$2a$10$abcdef...', 2, '1990-07-29', 'Kadın', '5551110071', 'Adres 71')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (71, 'Aile Hekimliği') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (72, '11111111172', 'Barış', 'Erdoğan', 'bariserdogan', 'baris72@example.com', '$2a$10$abcdef...', 2, '1981-02-15', 'Erkek', '5551110072', 'Adres 72')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (72, 'Gastroenteroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (73, '11111111173', 'Cansu', 'Yılmaz', 'cansuyilmaz', 'cansu73@example.com', '$2a$10$abcdef...', 2, '1987-06-18', 'Kadın', '5551110073', 'Adres 73')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (73, 'Endokrinoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (74, '11111111174', 'Deniz', 'Kara', 'denizkara', 'deniz74@example.com', '$2a$10$abcdef...', 2, '1990-09-09', 'Erkek', '5551110074', 'Adres 74')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (74, 'Nefroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (75, '11111111175', 'Ece', 'Şahin', 'ecesahin', 'ece75@example.com', '$2a$10$abcdef...', 2, '1985-12-25', 'Kadın', '5551110075', 'Adres 75')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (75, 'Hematoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (76, '11111111176', 'Fatih', 'Demir', 'fatihdemir', 'fatih76@example.com', '$2a$10$abcdef...', 2, '1978-04-03', 'Erkek', '5551110076', 'Adres 76')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (76, 'Kardiyovasküler Cerrahi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (77, '11111111177', 'Gamze', 'Aydın', 'gamzeaydin', 'gamze77@example.com', '$2a$10$abcdef...', 2, '1992-08-14', 'Kadın', '5551110077', 'Adres 77')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (77, 'Göz Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (78, '11111111178', 'Hülya', 'Kurt', 'hulyakurt', 'hulya78@example.com', '$2a$10$abcdef...', 2, '1983-11-19', 'Kadın', '5551110078', 'Adres 78')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (78, 'Nöroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (79, '11111111179', 'İsmail', 'Çetin', 'ismailcetin', 'ismail79@example.com', '$2a$10$abcdef...', 2, '1989-03-27', 'Erkek', '5551110079', 'Adres 79')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (79, 'Ortopedi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (80, '11111111180', 'Jale', 'Polat', 'jalepolat', 'jale80@example.com', '$2a$10$abcdef...', 2, '1994-05-11', 'Kadın', '5551110080', 'Adres 80')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (80, 'Dahiliye') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (81, '11111111181', 'Kadir', 'Yıldız', 'kadiryildiz', 'kadir81@example.com', '$2a$10$abcdef...', 2, '1986-07-22', 'Erkek', '5551110081', 'Adres 81')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (81, 'KBB') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (82, '11111111182', 'Lale', 'Güneş', 'lalegunes', 'lale82@example.com', '$2a$10$abcdef...', 2, '1982-10-30', 'Kadın', '5551110082', 'Adres 82')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (82, 'Deri Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (83, '11111111183', 'Mert', 'Kaya', 'mertkaya', 'mert83@example.com', '$2a$10$abcdef...', 2, '1991-01-17', 'Erkek', '5551110083', 'Adres 83')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (83, 'Psikiyatri') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (84, '11111111184', 'Nazan', 'Şimşek', 'nazansimsek', 'nazan84@example.com', '$2a$10$abcdef...', 2, '1988-06-06', 'Kadın', '5551110084', 'Adres 84')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (84, 'Fizik Tedavi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (85, '11111111185', 'Okan', 'Acar', 'okanacar', 'okan85@example.com', '$2a$10$abcdef...', 2, '1980-08-28', 'Erkek', '5551110085', 'Adres 85')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (85, 'Genel Cerrahi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (86, '11111111186', 'Pelin', 'Demir', 'pelindemir', 'pelin86@example.com', '$2a$10$abcdef...', 2, '1993-11-13', 'Kadın', '5551110086', 'Adres 86')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (86, 'Radyoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (87, '11111111187', 'Ramazan', 'Kurt', 'ramazankurt', 'ramazan87@example.com', '$2a$10$abcdef...', 2, '1984-03-19', 'Erkek', '5551110087', 'Adres 87')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (87, 'Göğüs Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (88, '11111111188', 'Seda', 'Çakır', 'sedacakir', 'seda88@example.com', '$2a$10$abcdef...', 2, '1987-12-05', 'Kadın', '5551110088', 'Adres 88')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (88, 'Enfeksiyon') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (89, '11111111189', 'Tayfun', 'Polat', 'tayfunpolat', 'tayfun89@example.com', '$2a$10$abcdef...', 2, '1985-05-16', 'Erkek', '5551110089', 'Adres 89')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (89, 'Beyin Cerrahisi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (90, '11111111190', 'Ufuk', 'Yalçın', 'ufukyalcin', 'ufuk90@example.com', '$2a$10$abcdef...', 2, '1992-02-21', 'Erkek', '5551110090', 'Adres 90')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (90, 'Acil Tıp') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (91, '11111111191', 'Veli', 'Güneş', 'veligunes', 'veli91@example.com', '$2a$10$abcdef...', 2, '1986-09-09', 'Erkek', '5551110091', 'Adres 91')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (91, 'Anestezi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (92, '11111111192', 'Ayşe', 'Kara', 'aysekara', 'ayse92@example.com', '$2a$10$abcdef...', 2, '1984-01-12', 'Kadın', '5551110092', 'Adres 92')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (92, 'Kardiyoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (93, '11111111193', 'Berk', 'Demir', 'berkdemir', 'berk93@example.com', '$2a$10$abcdef...', 2, '1982-03-23', 'Erkek', '5551110093', 'Adres 93')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (93, 'Dahiliye') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (94, '11111111194', 'Cemre', 'Yıldız', 'cemreyildiz', 'cemre94@example.com', '$2a$10$abcdef...', 2, '1990-05-14', 'Kadın', '5551110094', 'Adres 94')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (94, 'Ortopedi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (95, '11111111195', 'Deniz', 'Polat', 'denizpolat', 'deniz95@example.com', '$2a$10$abcdef...', 2, '1987-07-19', 'Erkek', '5551110095', 'Adres 95')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (95, 'Göz Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (96, '11111111196', 'Ebru', 'Çetin', 'ebrucetin', 'ebru96@example.com', '$2a$10$abcdef...', 2, '1985-09-28', 'Kadın', '5551110096', 'Adres 96')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (96, 'Nöroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (97, '11111111197', 'Fikret', 'Acar', 'fikretacar', 'fikret97@example.com', '$2a$10$abcdef...', 2, '1983-11-11', 'Erkek', '5551110097', 'Adres 97')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (97, 'Çocuk Sağlığı') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (98, '11111111198', 'Gülşah', 'Kurt', 'gulsahkurt', 'gulsah98@example.com', '$2a$10$abcdef...', 2, '1992-02-02', 'Kadın', '5551110098', 'Adres 98')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (98, 'Üroloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (99, '11111111199', 'Hakan', 'Şahin', 'hakansahin', 'hakan99@example.com', '$2a$10$abcdef...', 2, '1986-04-18', 'Erkek', '5551110099', 'Adres 99')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (99, 'Kadın Doğum') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (100, '11111111200', 'İlker', 'Demir', 'ilkerdemir', 'ilker100@example.com', '$2a$10$abcdef...', 2, '1989-06-06', 'Erkek', '5551110100', 'Adres 100')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (100, 'KBB') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (101, '11111111201', 'Jale', 'Polat', 'jalepolat2', 'jale101@example.com', '$2a$10$abcdef...', 2, '1988-08-08', 'Kadın', '5551110101', 'Adres 101')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (101, 'Deri Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (102, '11111111202', 'Kaan', 'Yıldız', 'kaanyildiz', 'kaan102@example.com', '$2a$10$abcdef...', 2, '1981-10-10', 'Erkek', '5551110102', 'Adres 102')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (102, 'Psikiyatri') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (103, '11111111203', 'Leman', 'Kaya', 'lemankaya', 'leman103@example.com', '$2a$10$abcdef...', 2, '1993-12-12', 'Kadın', '5551110103', 'Adres 103')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (103, 'Fizik Tedavi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (104, '11111111204', 'Mete', 'Aydın', 'meteaydin', 'mete104@example.com', '$2a$10$abcdef...', 2, '1980-03-03', 'Erkek', '5551110104', 'Adres 104')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (104, 'Genel Cerrahi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (105, '11111111205', 'Nisa', 'Polat', 'nisapolat', 'nisa105@example.com', '$2a$10$abcdef...', 2, '1987-05-15', 'Kadın', '5551110105', 'Adres 105')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (105, 'Radyoloji') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (106, '11111111206', 'Oğuz', 'Kurt', 'oguzkurt', 'oguz106@example.com', '$2a$10$abcdef...', 2, '1982-07-07', 'Erkek', '5551110106', 'Adres 106')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (106, 'Göğüs Hastalıkları') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (107, '11111111207', 'Pelin', 'Çelik', 'pelincelik', 'pelin107@example.com', '$2a$10$abcdef...', 2, '1991-09-09', 'Kadın', '5551110107', 'Adres 107')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (107, 'Enfeksiyon') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (108, '11111111208', 'Rıza', 'Acar', 'rizaacar', 'riza108@example.com', '$2a$10$abcdef...', 2, '1984-11-11', 'Erkek', '5551110108', 'Adres 108')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (108, 'Beyin Cerrahisi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (109, '11111111209', 'Sibel', 'Kaya', 'sibelkaya', 'sibel109@example.com', '$2a$10$abcdef...', 2, '1994-01-01', 'Kadın', '5551110109', 'Adres 109')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (109, 'Acil Tıp') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (110, '11111111210', 'Tuna', 'Yıldız', 'tunayildiz', 'tuna110@example.com', '$2a$10$abcdef...', 2, '1986-02-02', 'Erkek', '5551110110', 'Adres 110')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (110, 'Anestezi') ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO person (person_id, tc_no, first_name, last_name, username, email, password, role_id, birth_date, gender, phone_number, address)
VALUES (111, '11111111211', 'Umut', 'Güneş', 'umutgunes', 'umut111@example.com', '$2a$10$abcdef...', 2, '1985-03-03', 'Erkek', '5551110111', 'Adres 111')
ON CONFLICT (person_id) DO NOTHING;
INSERT INTO doctor (doctor_id, branch) VALUES (111, 'Aile Hekimliği') ON CONFLICT (doctor_id) DO NOTHING;

SELECT setval('person_seq', COALESCE((SELECT MAX(person_id) FROM person), 1) + 1, false);
