/*
Created: 05.03.2016
Modified: 22.05.2016
Model: PostgreSQL 9.2
Database: PostgreSQL 9.2
*/


-- Create tables section -------------------------------------------------

-- Table poll

CREATE TABLE "poll"(
 "id" BigSerial NOT NULL,
 "name" Varchar NOT NULL,
 "description" Varchar,
 "istest" Boolean DEFAULT true NOT NULL,
 "ismultyoptional" Boolean DEFAULT false NOT NULL
)
;

-- Add keys for table poll

ALTER TABLE "poll" ADD CONSTRAINT "pk_poll_id" PRIMARY KEY ("id")
;

ALTER TABLE "poll" ADD CONSTRAINT "poll_id" UNIQUE ("id")
;

-- Table respondent

CREATE TABLE "respondent"(
 "id" BigSerial NOT NULL,
 "name" Varchar NOT NULL,
 "ipaddress" Varchar NOT NULL
)
;

-- Add keys for table respondent

ALTER TABLE "respondent" ADD CONSTRAINT "pk_respondent_id" PRIMARY KEY ("id")
;

ALTER TABLE "respondent" ADD CONSTRAINT "respondent_id" UNIQUE ("id")
;

-- Table poll_respondent

CREATE TABLE "poll_respondent"(
 "pollid" Bigint NOT NULL,
 "respondentid" Bigint NOT NULL
)
;

-- Add keys for table poll_respondent

ALTER TABLE "poll_respondent" ADD CONSTRAINT "pk_poll_respondent" PRIMARY KEY ("pollid","respondentid")
;

-- Table question

CREATE TABLE "question"(
 "id" BigSerial NOT NULL,
 "name" Varchar NOT NULL,
 "pollid" Bigint
)
;

-- Create indexes for table question

CREATE INDEX "IX_Relationship4" ON "question" ("pollid")
;

-- Add keys for table question

ALTER TABLE "question" ADD CONSTRAINT "pk_question_id" PRIMARY KEY ("id")
;

ALTER TABLE "question" ADD CONSTRAINT "id" UNIQUE ("id")
;

-- Table option

CREATE TABLE "option"(
 "id" BigSerial NOT NULL,
 "content" Varchar NOT NULL,
 "isright" Boolean DEFAULT false NOT NULL,
 "questionid" Bigint
)
;

-- Create indexes for table option

CREATE INDEX "IX_Relationship5" ON "option" ("questionid")
;

-- Add keys for table option

ALTER TABLE "option" ADD CONSTRAINT "pk_option_id" PRIMARY KEY ("id")
;

ALTER TABLE "option" ADD CONSTRAINT "option_id" UNIQUE ("id")
;

-- Table respondent_option

CREATE TABLE "respondent_option"(
 "respondentid" Bigint NOT NULL,
 "optionid" Bigint NOT NULL
)
;

-- Add keys for table respondent_option

ALTER TABLE "respondent_option" ADD CONSTRAINT "pk_respondent_option" PRIMARY KEY ("respondentid","optionid")
;

-- Create relationships section -------------------------------------------------

ALTER TABLE "poll_respondent" ADD CONSTRAINT "fk_poll_respondent" FOREIGN KEY ("pollid") REFERENCES "poll" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "poll_respondent" ADD CONSTRAINT "fk_respondent_poll" FOREIGN KEY ("respondentid") REFERENCES "respondent" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "question" ADD CONSTRAINT "fk_poll_question" FOREIGN KEY ("pollid") REFERENCES "poll" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "option" ADD CONSTRAINT "fk_question_option" FOREIGN KEY ("questionid") REFERENCES "question" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "respondent_option" ADD CONSTRAINT "fk_respondent_option" FOREIGN KEY ("respondentid") REFERENCES "respondent" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "respondent_option" ADD CONSTRAINT "fk_option_respondent" FOREIGN KEY ("optionid") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;