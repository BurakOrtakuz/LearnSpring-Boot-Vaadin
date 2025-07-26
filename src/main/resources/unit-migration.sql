-- Unit enum migration script
-- Bu script mevcut unit tablosundaki verileri medicine tablosuna enum olarak aktarır

-- 1. Önce yeni unit_type kolunu ekle
ALTER TABLE medicine ADD COLUMN unit_type VARCHAR(50);

-- 2. Mevcut unit verilerini enum değerlerine map et
UPDATE medicine
SET unit_type = CASE
    WHEN u.name = 'mg' THEN 'MG'
    WHEN u.name = 'ml' THEN 'ML'
    WHEN u.name = 'tablet' THEN 'TABLET'
    WHEN u.name = 'capsule' THEN 'CAPSULE'
    WHEN u.name = 'drop' THEN 'DROP'
    WHEN u.name = 'tube' THEN 'TUBE'
    WHEN u.name = 'gram' THEN 'GRAM'
    WHEN u.name = 'liter' THEN 'LITER'
    ELSE 'MG' -- default value
END
FROM unit u
WHERE medicine.unit_id = u.unit_id;

-- 3. unit_id kolonunu kaldır
ALTER TABLE medicine DROP COLUMN unit_id;

-- 4. unit tablosunu kaldır (isteğe bağlı - veri kaybına dikkat!)
-- DROP TABLE unit;
