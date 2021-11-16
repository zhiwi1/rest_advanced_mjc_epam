
CREATE  TABLE IF NOT EXISTS  tags
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ;


-- -----------------------------------------------------
-- Table `mydb`.`certificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificates`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(100) NOT NULL,
    `price`            DECIMAL(10)  NULL,
    `createDate`      TIMESTAMP    NULL,
    `lastUpdateDate` TIMESTAMP    NULL,
    `duration`         INT          NULL,
    `description`      VARCHAR(200) NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table `mydb`.`certificate_tags`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS   `certificate_tags`
(
    `tag_id`         BIGINT NOT NULL,
    `certificate_id` BIGINT NOT NULL,
    PRIMARY KEY (`tag_id`, `certificate_id`)
   )
;


CREATE  TABLE IF NOT EXISTS `users`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table `mydb`.`certificate_orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_orders`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `price`          DECIMAL(10) NULL,
    `createDate`    TIMESTAMP   NULL,
    `user_id`        BIGINT      NULL,
    `certificateId` BIGINT      NULL,
    PRIMARY KEY (`id`))

;