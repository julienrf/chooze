# --- Generated

# --- !Ups
CREATE TABLE "alternatives" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"name" VARCHAR NOT NULL,"poll_id" BIGINT NOT NULL);
CREATE TABLE "notes" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"vote_id" BIGINT NOT NULL,"value" INTEGER DEFAULT 0 NOT NULL,"alternative_id" BIGINT NOT NULL);
CREATE TABLE "votes" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"user" VARCHAR NOT NULL,"poll_id" BIGINT NOT NULL);
CREATE TABLE "polls" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"name" VARCHAR NOT NULL,"description" VARCHAR NOT NULL)

# --- !Downs
CREATE TABLE "alternatives" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"name" VARCHAR NOT NULL,"poll_id" BIGINT NOT NULL);
CREATE TABLE "notes" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"vote_id" BIGINT NOT NULL,"value" INTEGER DEFAULT 0 NOT NULL,"alternative_id" BIGINT NOT NULL);
CREATE TABLE "votes" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"user" VARCHAR NOT NULL,"poll_id" BIGINT NOT NULL);
CREATE TABLE "polls" ("id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"name" VARCHAR NOT NULL,"description" VARCHAR NOT NULL)

