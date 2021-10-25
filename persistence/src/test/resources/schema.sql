
CREATE TABLE tags
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ;


-- -----------------------------------------------------
-- Table `mydb`.`certificates`
-- -----------------------------------------------------
CREATE TABLE  `certificates`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(100) NOT NULL,
    `price`            DECIMAL(10)  NULL,
    `create_date`      TIMESTAMP    NULL,
    `last_update_date` TIMESTAMP    NULL,
    `duration`         INT          NULL,
    `description`      VARCHAR(200) NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table `mydb`.`certificate_tags`
-- -----------------------------------------------------
CREATE TABLE  `certificate_tags`
(
    `tag_id`         BIGINT NOT NULL,
    `certificate_id` BIGINT NOT NULL,
    PRIMARY KEY (`tag_id`, `certificate_id`)

)
;


CREATE TABLE `users`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table `mydb`.`certificate_orders`
-- -----------------------------------------------------
CREATE TABLE `certificate_orders`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `price`          DECIMAL(10) NULL,
    `create_date`    TIMESTAMP   NULL,
    `user_id`        BIGINT      NULL,
    `certificate_id` BIGINT      NULL,
    PRIMARY KEY (`id`))

;