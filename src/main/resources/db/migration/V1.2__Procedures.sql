DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `place_order`(IN in_email VARCHAR(256))
BEGIN

DECLARE v_quantity INT;
DECLARE v_id INT;
DECLARE v_item_fk VARCHAR(512);
DECLARE v_user_account_fk VARCHAR(512);
DECLARE done INT DEFAULT FALSE;
DECLARE item_cursor CURSOR FOR
	SELECT id, quantity, item_fk, user_account_fk
    FROM cart
    WHERE user_account_fk = (SELECT id FROM user_account WHERE email = in_email);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;

	SET done = 0;
	OPEN item_cursor;
	get_item_loop: LOOP
	FETCH item_cursor INTO v_id, v_quantity, v_item_fk, v_user_account_fk;
	IF done = 1 THEN LEAVE get_item_loop; END IF;

	INSERT INTO order_details (order_date, order_status, quantity, item_fk, user_account_fk)
	VALUES(now(), 'NEW', v_quantity, v_item_fk, v_user_account_fk);

    DELETE FROM cart WHERE id = v_id;

END LOOP get_item_loop;
CLOSE item_cursor;
END$$
DELIMITER ;
