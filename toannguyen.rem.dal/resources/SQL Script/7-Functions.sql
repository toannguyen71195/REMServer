DELIMITER $$
CREATE FUNCTION levenshtein( s1 text, s2 text) 
	RETURNS int(11)
    DETERMINISTIC
BEGIN 
    DECLARE s1_len, s2_len, i, j, c, c_temp, cost INT; 
    DECLARE s1_char CHAR; 
    DECLARE cv0, cv1 text; 
    SET s1_len = CHAR_LENGTH(s1), s2_len = CHAR_LENGTH(s2), cv1 = 0x00, j = 1, i = 1, c = 0; 
    IF s1 = s2 THEN 
      RETURN 0; 
    ELSEIF s1_len = 0 THEN 
      RETURN s2_len; 
    ELSEIF s2_len = 0 THEN 
      RETURN s1_len; 
    ELSE 
      WHILE j <= s2_len DO 
        SET cv1 = CONCAT(cv1, UNHEX(HEX(j))), j = j + 1; 
      END WHILE; 
      WHILE i <= s1_len DO 
        SET s1_char = SUBSTRING(s1, i, 1), c = i, cv0 = UNHEX(HEX(i)), j = 1; 
        WHILE j <= s2_len DO 
          SET c = c + 1; 
          IF s1_char = SUBSTRING(s2, j, 1) THEN  
            SET cost = 0; ELSE SET cost = 1; 
          END IF; 
          SET c_temp = CONV(HEX(SUBSTRING(cv1, j, 1)), 16, 10) + cost; 
          IF c > c_temp THEN SET c = c_temp; END IF; 
            SET c_temp = CONV(HEX(SUBSTRING(cv1, j+1, 1)), 16, 10) + 1; 
            IF c > c_temp THEN  
              SET c = c_temp;  
            END IF; 
            SET cv0 = CONCAT(cv0, UNHEX(HEX(c))), j = j + 1; 
        END WHILE; 
        SET cv1 = cv0, i = i + 1; 
      END WHILE; 
    END IF; 
    RETURN c; 
END $$

CREATE FUNCTION levenshtein_ratio( s1 text, s2 text ) 
	RETURNS int(11)
    DETERMINISTIC
BEGIN 
    DECLARE s1_len, s2_len, max_len INT; 
    SET s1_len = LENGTH(s1), s2_len = LENGTH(s2); 
    IF s1_len > s2_len THEN  
      SET max_len = s1_len;  
    ELSE  
      SET max_len = s2_len;  
    END IF; 
    RETURN ROUND((1 - LEVENSHTEIN(s1, s2) / max_len) * 100); 
END $$

CREATE FUNCTION gpsDistance (lat1 DOUBLE, lng1 DOUBLE, lat2 DOUBLE, lng2 DOUBLE)
    RETURNS DOUBLE NO SQL DETERMINISTIC
BEGIN
    DECLARE dist DOUBLE;
    SET dist = 2 * 6371008.771415059 * ASIN(
        SQRT(
            POW(SIN((RADIANS(lat2) - RADIANS(lat1)) / 2), 2)
            + COS(RADIANS(lat1)) * COS(RADIANS(lat2)) * POW(SIN((RADIANS(lng2) - RADIANS(lng1)) / 2), 2)
        )
    );
    RETURN dist;
END$$

DELIMITER ;